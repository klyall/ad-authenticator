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
package com.adauthenticator.model;

public class ADServerEntryParser {

    private ADServerEntryParser() {
    }

    public static ADServerEntry parse(String serverRecord) {
        // srv record format: 0 100 389 xyzdc.yourcompany.com.
        String[] srvRecordTokens = serverRecord.split(" ");

        int priority    = Integer.valueOf(srvRecordTokens[0]);
        int weight      = Integer.valueOf(srvRecordTokens[1]);
        int port        = Integer.valueOf(srvRecordTokens[2]);
        String host        = srvRecordTokens[3];

        if (host.endsWith(".")) {
            host = host.substring(0, host.length() - 1);
        }

        return new ADServerEntryImpl(priority, weight, host, port);
    }
}
