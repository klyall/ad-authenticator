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
package com.adauthenticator.test.model;

import com.adauthenticator.model.ADGroup;
import com.adauthenticator.model.ADGroupParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ADGroupTest extends EqualsAndHashCodeTests {

    private static final String TEST_STRING = "CN=Group Name,OU=Distribution Groups,OU=Exchange,DC=company,DC=com";
    private ADGroup referenceGroup1;
    private ADGroup referenceGroup2;
    private ADGroup referenceGroup3;
    private Map<String, ADGroup> differentADGroups;

    @Before
    public void setUp() {
        referenceGroup1 = ADGroupParser.parse(TEST_STRING);
        referenceGroup2 = ADGroupParser.parse(TEST_STRING);
        referenceGroup3 = ADGroupParser.parse(TEST_STRING);

        differentADGroups = new HashMap<String, ADGroup>();
        differentADGroups.put("cn different", ADGroupParser.parse("CN=Group Name2,OU=Distribution Groups,OU=Exchange,DC=company,DC=com"));
        differentADGroups.put("ou", ADGroupParser.parse("CN=Group Name,OU=Distribution Groups2,OU=Exchange,DC=company,DC=com"));
        differentADGroups.put("dc", ADGroupParser.parse("CN=Group Name,OU=Distribution Groups,OU=Exchange,DC=company2,DC=com"));
        differentADGroups.put("cn null", ADGroupParser.parse("OU=Distribution Groups,OU=Exchange,DC=company,DC=com"));
    }

    @Override
    protected ADGroup getReferenceObject1() {
        return referenceGroup1;
    }

    @Override
    protected Object getReferenceObject2() {
        return referenceGroup2;
    }

    @Override
    protected Object getReferenceObject3() {
        return referenceGroup3;
    }

    @Override
    protected Map getDifferentObjects() {
        return differentADGroups;
    }

    @Test
    public void shouldCreateGroupFromValidFormattedString() {
        assertThat(referenceGroup1, is(notNullValue()));
    }

    @Test
    public void shouldCreateGroupAndExtractCommonNameFromValidFormattedString() {
        assertThat(referenceGroup1.getCommonName(), is(equalTo("Group Name")));
    }

    @Test
    public void shouldCreateGroupAndExtractOrganizationalUnitNamesFromValidFormattedString() {
        List<String> expectedOrgUnits = new ArrayList<String>();
        expectedOrgUnits.add("Distribution Groups");
        expectedOrgUnits.add("Exchange");

        assertThat(referenceGroup1.getOrganizationalUnitNames(), is(equalTo(expectedOrgUnits)));
    }

    @Test
    public void shouldCreateGroupAndExtractOrganizationalUnitsFromValidFormattedString() {
        List<String> expectedDomainComponents = new ArrayList<String>();
        expectedDomainComponents.add("company");
        expectedDomainComponents.add("com");

        assertThat(referenceGroup1.getDomainComponents(), is(equalTo(expectedDomainComponents)));
    }

    @Test
    public void shouldIgnoreInvalidAttributesWhenTryingToCreateGroupFromInvalidFormattedString() {
        String str = "abcdefg";

        ADGroup group = ADGroupParser.parse(str);

        assertThat(referenceGroup1, is(notNullValue()));
    }

    @Test
    public void shouldIgnoreUnknownAttributesWhenTryingToCreateGroupFromInvalidFormattedString() {
        String str = "abc=def";

        ADGroup group = ADGroupParser.parse(str);

        assertThat(group, is(notNullValue()));
    }
}
