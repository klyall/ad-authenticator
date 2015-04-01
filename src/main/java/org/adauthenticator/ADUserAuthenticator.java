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

public interface ADUserAuthenticator {

    /**
     * Authenticates that a user exists in Active Directory for the provided credentials.
     * @param username The user's sAMAccountName or usrPrincipleName.
     * @param password The user's password.
     * @return Returns true if the user is authenticated otherwise false.
     */
    boolean authenticate(String username, String password);
}
