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
package org.adauthenticator.test.model;

import org.adauthenticator.model.ADServerEntry;
import org.adauthenticator.model.ADServerEntryParser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ADServerEntryTest extends EqualsAndHashCodeTests<ADServerEntry> {

    public static final String TEST_SERVER_RECORD = "1 100 389 ldaphost1";
    private ADServerEntry referenceADServerEntry1;
    private ADServerEntry referenceADServerEntry2;
    private ADServerEntry referenceADServerEntry3;
    private Map<String, ADServerEntry> differentADServerEntries;

    @Before
    public void setUp() {
        referenceADServerEntry1 = ADServerEntryParser.parse(TEST_SERVER_RECORD);
        referenceADServerEntry2 = ADServerEntryParser.parse(TEST_SERVER_RECORD);
        referenceADServerEntry3 = ADServerEntryParser.parse(TEST_SERVER_RECORD);

        differentADServerEntries = new HashMap<String, ADServerEntry>();
        differentADServerEntries.put("priority", ADServerEntryParser.parse("2 100 389 ldaphost1"));
        differentADServerEntries.put("weight", ADServerEntryParser.parse("1 200 389 ldaphost1"));
        differentADServerEntries.put("host", ADServerEntryParser.parse("1 100 389 ldaphost2"));
        differentADServerEntries.put("port", ADServerEntryParser.parse("1 100 390 ldaphost1"));
    }

    @Override
    protected ADServerEntry getReferenceObject1() {
        return referenceADServerEntry1;
    }

    @Override
    protected ADServerEntry getReferenceObject2() {
        return referenceADServerEntry2;
    }

    @Override
    protected ADServerEntry getReferenceObject3() {
        return referenceADServerEntry3;
    }

    @Override
    protected Map<String, ADServerEntry> getDifferentObjects() {
        return differentADServerEntries;
    }

    @Test
	public void shouldParseValidServiceRecordAndCreateADServerEntry() {
        assertNotNull("ADServerEntry has not been successfully parsed", referenceADServerEntry1);
	}

    @Test
    public void shouldParseValidServiceRecordAndCreateADServerEntryAndExtractPriority() {
        assertEquals("Priority is not set properly", referenceADServerEntry1.getPriority(), 1);
    }

    @Test
    public void shouldParseValidServiceRecordAndCreateADServerEntryAndExtractWeight() {
        assertEquals("Weight is not set properly", referenceADServerEntry1.getWeight(), 100);
    }

    @Test
    public void shouldParseValidServiceRecordAndCreateADServerEntryAndExtractHost() {
        assertEquals("Host is not set properly", referenceADServerEntry1.getHost(), "ldaphost1");
    }

    @Test
    public void shouldParseValidServiceRecordAndCreateADServerEntryAndExtractPort() {
        assertEquals("Port is not set properly", referenceADServerEntry1.getPort(), 389);
    }

	@Test
	public void shouldGenerateUrlFromHostAndPort() {
		assertEquals("getURL is not returning properly", referenceADServerEntry1.getUrl(), "ldap://ldaphost1:389");
	}

	@Test
	public void shouldOrderADServerEntryByPriorityFirst() {
		TreeSet<ADServerEntry> serverList = new TreeSet<ADServerEntry>();

        serverList.add(ADServerEntryParser.parse("2 100 389 ldaphost2"));
		serverList.add(ADServerEntryParser.parse("1 100 389 ldaphost1"));

        assertThat(serverList.first().getHost(), is(equalTo("ldaphost1")));
	}

	@Test
	public void shouldOrderADServerEntryByWeightSecond() {
        TreeSet<ADServerEntry> serverList = new TreeSet<ADServerEntry>();

        serverList.add(ADServerEntryParser.parse("1 100 389 ldaphost2"));
        serverList.add(ADServerEntryParser.parse("1 200 389 ldaphost1"));

        assertThat(serverList.first().getHost(), is(equalTo("ldaphost1")));
	}

	@Test
	public void shouldOrderADServerEntryByUrlThird() {
        TreeSet<ADServerEntry> serverList = new TreeSet<ADServerEntry>();

        serverList.add(ADServerEntryParser.parse("1 100 389 ldaphost2"));
        serverList.add(ADServerEntryParser.parse("1 100 389 ldaphost1"));

        assertThat(serverList.first().getHost(), is(equalTo("ldaphost1")));
	}
}
