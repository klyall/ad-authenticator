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

import java.util.ArrayList;
import java.util.Collection;

public class ADUserImpl implements ADUser {

	private static final Logger LOG = LoggerFactory.getLogger(ADUserImpl.class);

    private String displayName;
    private String email;
    private String firstName;
    private Collection<ADGroup> groups = new ArrayList<ADGroup>();
    private String lastName;
    private String samAccountName;
    private String title;
    private String userPrincipleName;

    protected ADUserImpl() {
	}

    protected void addGroup(ADGroup group) {
        groups.add(group);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserPrincipleName() {
        return userPrincipleName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public Collection<ADGroup> getGroups() {
        return groups;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSamAccountName() {
        return samAccountName;
    }

    public String getTitle() {
        return title;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    protected void setSamAccountName(String samAccountName) {
        this.samAccountName = samAccountName;
    }

    protected void setUserPrincipleName(String userPrincipleName) {
        this.userPrincipleName = userPrincipleName;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(samAccountName);
        sb.append(" (").append(displayName).append(")");
        sb.append(", groups: ");
        sb.append(groups);

        return sb.toString();
    }
}
