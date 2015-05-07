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
package com.adauthenticator.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ADGroupParser {

    public static final String ATTRIBUTE_COMMON_NAME                = "CN";
    public static final String ATTRIBUTE_DOMAIN_COMPONENT           = "DC";
    public static final String ATTRIBUTE_ORGANIZATIONAL_UNIT_NAME   = "OU";

    private static final Logger LOG = LoggerFactory.getLogger(ADGroupParser.class);

    private ADGroupParser() {
    }

    public static ADGroup parse(String groupDN) {
        ADGroupImpl adGroup = new ADGroupImpl();

        String[] attrs = groupDN.split(",");

        for (String attr : attrs) {
            if (attr.contains("=")) {
                String[] pair = attr.split("=");

                String key      = pair[0];
                String value    = pair[1];

                if (key.equals(ATTRIBUTE_COMMON_NAME)) {
                    adGroup.setCommonName(value);
                } else if (key.equals(ATTRIBUTE_DOMAIN_COMPONENT)) {
                    adGroup.addDomainComponents(value);
                } else if (key.equals(ATTRIBUTE_ORGANIZATIONAL_UNIT_NAME)) {
                    adGroup.addOrganizationalUnitNames(value);
                } else {
                    LOG.debug("Unhandled group attribute: {}={}", key, value);
                }
            } else {
                LOG.debug("Unknown group attribute: {}", attr);
            }
        }
        return adGroup;
    }
}
