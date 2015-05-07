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

import java.util.ArrayList;
import java.util.List;

public class ADGroupImpl implements ADGroup {

    private static final Logger LOG = LoggerFactory.getLogger(ADGroupImpl.class);

    private String commonName;
    private List<String> organizationalUnitNames = new ArrayList<String>();
    private List<String> domainComponents = new ArrayList<String>();

    protected ADGroupImpl() {
    }

    protected void addDomainComponents(String domainComponent) {
        domainComponents.add(domainComponent);
    }

    protected void addOrganizationalUnitNames(String organizationalUnitName) {
        organizationalUnitNames.add(organizationalUnitName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ADGroupImpl adGroup = (ADGroupImpl) o;

        if (commonName != null ? !commonName.equals(adGroup.commonName) : adGroup.commonName != null) return false;
        if (domainComponents != null ? !domainComponents.equals(adGroup.domainComponents) : adGroup.domainComponents != null)
            return false;
        if (organizationalUnitNames != null ? !organizationalUnitNames.equals(adGroup.organizationalUnitNames) : adGroup.organizationalUnitNames != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commonName != null ? commonName.hashCode() : 0;
        result = 31 * result + (organizationalUnitNames != null ? organizationalUnitNames.hashCode() : 0);
        result = 31 * result + (domainComponents != null ? domainComponents.hashCode() : 0);
        return result;
    }

    public String getCommonName() {
        return commonName;
    }

    public List<String> getOrganizationalUnitNames() {
        return organizationalUnitNames;
    }

    public List<String> getDomainComponents() {
        return domainComponents;
    }

    protected void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @Override
    public String toString() {
        return commonName;
    }
}
