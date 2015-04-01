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

import java.util.Collection;

public interface ADUser {
    String getDisplayName();
    String getEmail();
    String getFirstName();
    Collection<ADGroup> getGroups();
    String getLastName();
    String getSamAccountName();
    String getTitle();
    String getUserPrincipleName();
}
