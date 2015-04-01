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
package org.adauthenticator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchResult;

/**
 * Active Directory User object.
 * 
 * @author Jiji Sasidharan
 */
public class ADUserParser {

    public static final String ATTRIBUTE_DISPLAY_NAME           = "displayName";
    public static final String ATTRIBUTE_EMAIL                  = "mail";
    public static final String ATTRIBUTE_FIRST_NAME             = "givenName";
    public static final String ATTRIBUTE_LAST_NAME              = "sn";
    public static final String ATTRIBUTE_MEMBER_OF              = "memberOf";
    public static final String ATTRIBUTE_SAM_ACCOUNT_NAME       = "sAMAccountName";
    public static final String ATTRIBUTE_TITLE                  = "title";
    public static final String ATTRIBUTE_USER_PRINCIPAL_NAME    = "userPrincipalName";

    private static final Logger LOG = LoggerFactory.getLogger(ADUserParser.class);

    private ADUserParser() {
    }

	/**
	 * Populate the user attributes from AD SearchResult
	 * @param userAttributeEnumeration
	 * @throws javax.naming.NamingException
	 */
	public static ADUser parse(NamingEnumeration<SearchResult> userAttributeEnumeration) throws NamingException {

        ADUserImpl adUser = new ADUserImpl();

    	while (userAttributeEnumeration.hasMoreElements()) {
    		SearchResult result = userAttributeEnumeration.nextElement();

			NamingEnumeration attrs = result.getAttributes().getAll();

    		while (attrs.hasMore()) {
    			Attribute attr = (Attribute) attrs.next();
                parseAttribute(attr, adUser);
            }
    	}

        return adUser;
	}

    private static void parseAttribute(Attribute attr, ADUserImpl adUser) throws NamingException {
        if (ATTRIBUTE_DISPLAY_NAME.equals(attr.getID())) {
            LOG.debug("{}", attr);
            adUser.setDisplayName((String) attr.get());
        } else if (ATTRIBUTE_FIRST_NAME.equals(attr.getID())) {
            LOG.debug("{}", attr);
            adUser.setFirstName((String) attr.get());
        } else if (ATTRIBUTE_LAST_NAME.equals(attr.getID())) {
            LOG.debug("{}", attr);
            adUser.setLastName((String) attr.get());
        } else if (ATTRIBUTE_EMAIL.equals(attr.getID())) {
            LOG.debug("{}", attr);
            adUser.setEmail((String) attr.get());
        } else if (ATTRIBUTE_MEMBER_OF.equals(attr.getID())) {
            LOG.debug("{}", attr);
            parseGroups(attr.getAll(), adUser);
        } else if (ATTRIBUTE_SAM_ACCOUNT_NAME.equals(attr.getID())) {
            LOG.debug("{}", attr);
            adUser.setSamAccountName((String) attr.get());
        } else if (ATTRIBUTE_TITLE.equals(attr.getID())) {
            LOG.debug("{}", attr);
            adUser.setTitle((String) attr.get());
        } else if (ATTRIBUTE_USER_PRINCIPAL_NAME.equals(attr.getID())) {
            LOG.debug("{}", attr);
            adUser.setUserPrincipleName((String) attr.get());
        }
    }

    private static void parseGroups(NamingEnumeration<?> attributeValues, ADUserImpl adUser) throws NamingException {
        while (attributeValues.hasMoreElements()) {
            String attrValue = (String) attributeValues.next();
            adUser.addGroup(ADGroupParser.parse(attrValue));
        }
    }
}
