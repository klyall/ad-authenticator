/*
 * Copyright (C) 2015  Keith Lyall (https://github.com/klyall/ad-authenticator)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.adauthenticator;

import org.adauthenticator.model.ADUser;

public interface ADUserRetriever {

    /**
     * Retrieves the logged in user from Active Directory using the default search filter unless otherwise configured
     * using <code>setSearchFilter</code>.
     *
     * @return returns a populated ADUser.
     */
    ADUser retrieveLoggedInUser();

    /**
     * Optionally sets the filter used to search Active Directory for a user.
     * If not set, the default value used is '(|(sAMAccountName=%1$s)(userPrincipalName=%2$s))' where <code>%1$s</code>
     * is the supplied username and <code>%2$s</code> is the username@domainName.
     * @param searchFilter Search filter to be used.
     */
    void setSearchFilter(String searchFilter);
}
