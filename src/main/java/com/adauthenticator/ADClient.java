/*
 * Copyright (C) Jiji Sasidharan (http://programmingforliving.com/)
 * Copyright (C) Keith Lyall (https://github.com/klyall/ad-authenticator)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.adauthenticator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;

import com.adauthenticator.model.ADServerEntry;
import com.adauthenticator.model.ADServerEntryParser;
import com.adauthenticator.model.ADUser;
import com.adauthenticator.model.ADUserParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

/**
 * This class provides utility methods to open and close LDAP Context.
 * 
 * @author Jiji Sasidharan
 */
public class ADClient implements ADAuthenticator {

    public static final String FACTORY_INITIAL                  = "java.naming.factory.initial";
    public static final String SECURITY_AUTHENTICATION          = "java.naming.security.authentication";
    public static final String SECURITY_PRINCIPAL               = "java.naming.security.principal";
    public static final String SECURITY_CREDENTIALS             = "java.naming.security.credentials";
    public static final String REFERRAL                         = "java.naming.referral";
    public static final String REFERRAL_FOLLOW                  = "follow";
    public static final String PROVIDER_URL                     = "java.naming.provider.url";
    public static final String LDAP_CTX_FACTORY                 = "com.sun.jndi.ldap.LdapCtxFactory";
    public static final String SECURITY_AUTHENTICATION_SIMPLE   = "simple";

    public static final String DEFAULT_SEARCH_FILTER           = "(|(sAMAccountName=%1$s)(userPrincipalName=%2$s))";

    private static final Logger LOG = LoggerFactory.getLogger(ADClient.class);
    // A ThreadLocal instance to hold the logged in user details local to the thread.
    private static final ThreadLocal<ADUser> LOGGED_IN_USER_HOLDER = new ThreadLocal<ADUser>();
    private String dnsDomain;
    private String searchFilter;
    private List<String> searchObjects = new ArrayList<String>();
    private Set<ADServerEntry> providerList;

    protected ADClient() {
        doAutoDiscovery();
    }

    protected ADClient(String adDomain) {
        this.dnsDomain      = adDomain;
        this.providerList   = fetchProviderList(adDomain);
    }

    public String getSearchFilter() {
        if (searchFilter == null || searchFilter.trim().equals("")) {
            return DEFAULT_SEARCH_FILTER;
        } else {
            return searchFilter;
        }
    }

    @Override
    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    @Override
    public void addSearchObject(String searchObject) {
        this.searchObjects.add(searchObject);
    }

    @Override
    public boolean authenticate(String username, String password) {
        LOG.info("Authenticating '{}'", username);

        boolean isAuthenticated = false;

        DirContext dirCtx = open(username, password);

        if (dirCtx != null) {
            isAuthenticated = true;
            LOG.info("'{}' successfully authenticated", username);
        } else {
            LOG.info("'{}' failed authentication", username);
        }

        closeQuietly(dirCtx);
        return isAuthenticated;
    }

    /**
     * Retrieve the logged in user object from ThreadLocal and returns it.
     * @return
     */
    public ADUser retrieveLoggedInUser() {
        return LOGGED_IN_USER_HOLDER.get();
    }

    /**
     * This method automatically identifies the Active Directory settings.
     * If domain is given in sonar.properties, then the authentication server details
     * are fetched based on that. If domain is not available in sonar properties, then
     * this method take the FQN of the host and derive domain name and authentication server
     * list from it.
     */
    private void doAutoDiscovery() {
        try {
            String adDomain = "";
            Set<ADServerEntry> providerList = null;
            String hostName = InetAddress.getLocalHost().getCanonicalHostName();

            LOG.debug("Hostname: {}", hostName);

            int index = -2;

            while ((index = hostName.indexOf(".", index + 2)) > -1) {
                adDomain = hostName.substring(index + 1);
                try {
                    providerList = fetchProviderList(adDomain);
                    if (providerList != null && providerList.size() > 0) {
                        break;
                    }
                } catch (Exception e) {
                    LOG.warn("Couldn't find any providers for domain '{}", adDomain);
                }
            };

            if (providerList == null || providerList.isEmpty()) {
                LOG.error("Auto-discovery couldn't find any srv records for {}", hostName);
                throw new ADAuthenticatorException("Failed to retrieveUser srv records for " + hostName);
            }

            this.dnsDomain   = adDomain;
            this.providerList = providerList;
        } catch (UnknownHostException e) {
            LOG.error("Failed to detect domain. Error: " + e.getMessage());
            throw new ADAuthenticatorException("Failed to detect the domain ", e);
        }

        LOG.info("AutoDiscovery completed successfully. Domain found '{}'", dnsDomain);
    }

    /**
     * Fetch list of LDAP server configurations for a given domain
     *
     * @param dnsDomain
     * @return
     */
    private Set<ADServerEntry> fetchProviderList(String dnsDomain) {
        LOG.debug("Searching provider list for domain '{}'", dnsDomain);
        Set<ADServerEntry> providerList = new TreeSet<ADServerEntry>();
        try {
            DirContext dirContext = new InitialDirContext();
            Attributes serverAttributes = dirContext.getAttributes("dns:/_ldap._tcp." + dnsDomain, new String[] { "srv" });
            Attribute serverAttribute = serverAttributes.get("srv");

            for (NamingEnumeration<?> e = serverAttribute.getAll(); e.hasMoreElements();) {
                String serverRecord = (String) e.nextElement();
                LOG.debug("Processing srv record - {}", serverRecord);
                providerList.add(ADServerEntryParser.parse(serverRecord));
            }
            LOG.debug("Provider List: {}", providerList);
        } catch (NamingException e) {
            LOG.error("Failed to retrieveUser srv records for domain: '{}'. Error: {}", dnsDomain, e.getMessage());
            throw new ADAuthenticatorException("Failed to retrieveUser src records for domain: " + dnsDomain, e);
        }
        return providerList;
    }

    private DirContext open(String userName, String password) {
        String usernameAtDomain = userName + "@" + dnsDomain;

        Properties env = new Properties();
        env.put(FACTORY_INITIAL, LDAP_CTX_FACTORY);
        env.put(SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION_SIMPLE);
        env.put(SECURITY_PRINCIPAL, usernameAtDomain);
        env.put(SECURITY_CREDENTIALS, password);
        env.put(REFERRAL, REFERRAL_FOLLOW);

        DirContext ldapCtx = null;

        for(ADServerEntry provider : providerList) {
            env.put(PROVIDER_URL, provider.getUrl());
            try {
            	LOGGED_IN_USER_HOLDER.remove();
                ldapCtx = new InitialLdapContext(env, null);
                LOG.debug("User successfully bound to AD {}", provider.getUrl());
                retrieveUserFromContext(ldapCtx, userName, usernameAtDomain);
                break;
            } catch (AuthenticationException e) {
                LOG.debug("Authentication exception for {}. Error: {}", provider.getUrl(), e.getMessage());
                break;
            } catch (NamingException e) {
                LOG.warn("AD bind failed for {}. Error: {}", provider, e.getMessage());
                LOG.debug("AD bind failed", e);
            }
        }
        return ldapCtx;
    }

    private void retrieveUserFromContext(DirContext ldapCtx, String userName, String usernameAtDomain) {
        try {
            SearchControls searchControl = new SearchControls();
            searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String name = createSearchName();
            String filter = format(getSearchFilter(), userName, usernameAtDomain);

            LOG.debug("Searching AD for {} in {}", filter, dnsDomain);

            NamingEnumeration<SearchResult> answer = ldapCtx.search(name, filter, searchControl);

            ADUser user = ADUserParser.parse(answer);
            LOG.debug("User found: {}", user);
            LOGGED_IN_USER_HOLDER.set(user);
        } catch (NamingException e) {
            LOG.warn("Failed to retrieve user the attributes for '{}'. Error: {}", userName, e.getMessage());
            LOG.debug("User search failed", e);
        }
    }

    private String createSearchName() {
        StringBuilder sb = new StringBuilder();
        for (String searchObject: searchObjects) {
            sb.append(searchObject);
            sb.append(",");
        }
        sb.append(toDomainDN(dnsDomain));
        return sb.toString();
    }

    private String toDomainDN(String dnsDomain) {
        return "DC=" + dnsDomain.replace(".", ",DC=");
    }

    private void closeQuietly(DirContext dirCtx) {
        try {
            if (dirCtx != null)
                dirCtx.close();
        } catch (Exception e) {
            // ignore the exception
            LOG.warn("Error while closing the context", e);
        }
    }
}

