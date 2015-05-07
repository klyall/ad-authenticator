/*
 * Copyright (C) 2015 Keith Lyall (https://github.com/klyall/ad-authenticator)
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
package com.adauthenticator;

public class ADAuthenticatorFactory {

    private ADAuthenticatorFactory() {
    }

    /**
     * Creates and instance of ADAuthenticator that will auto-discover the Active Directory domain from the current
     * computers Fully qualified hostname.
     * @return Returns an ADAuthenticator instance.
     */
    public static ADAuthenticator newInstance() {
        return new ADClient();
    }

    /**
     * Creates and instance of ADAuthenticator with the specified domain name.
     * @param domainName The domain name to use when searching Active Directory.
     * @return Returns an ADAuthenticator instance.
     */
    public static ADAuthenticator newInstance(String domainName) {
        return new ADClient(domainName);
    }
}
