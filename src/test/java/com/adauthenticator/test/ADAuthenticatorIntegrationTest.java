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
package com.adauthenticator.test;

import com.adauthenticator.ADAuthenticator;
import com.adauthenticator.ADAuthenticatorFactory;
import com.adauthenticator.model.ADUser;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ADAuthenticatorIntegrationTest {

    private static final String DOMAIN_NAME          = "?";
    private static final String TEST_SAM_ACCOUNT     = "?";
    private static final String TEST_EMAIL_ID        = "?";
    private static final String TEST_PASSWORD        = "?";

    private static final String EXPECTED_EMAIL                  = "?";
    private static final String EXPECTED_FIRST_NAME             = "?";
    private static final String EXPECTED_LAST_NAME              = "?";
    private static final String EXPECTED_USER_PRINCIPLE_NAME    = "?";
    private static final String EXPECTED_TITLE                  = "?";

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAValidUserUsingSamAccount() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, TEST_PASSWORD);

        assertThat("User not authenticated", authenticated, is(equalTo(true)));
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAValidUserUsingSamAccountWhenSpecifyingDnsDomain() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance(DOMAIN_NAME);
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, TEST_PASSWORD);

        assertThat("User not authenticated", authenticated, is(equalTo(true)));
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAValidUserUsingEmailAccount() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(TEST_EMAIL_ID, TEST_PASSWORD);

        assertThat("User not authenticated", authenticated, is(equalTo(true)));
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldNotAuthenticateAnInvalidPassword() {
        String password = "invalid";

        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, password);

        assertThat("User not authenticated", authenticated, is(equalTo(false)));
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldNotAuthenticateAnInvalidUser() {
        String username = "testUser";
        String password = "invalid";

        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(username, password);

        assertThat("User not authenticated", authenticated, is(equalTo(false)));
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAndRetrieveUser() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, TEST_PASSWORD);
        ADUser adUser = authenticator.retrieveLoggedInUser();

        assertNotNull("User not retrieved", adUser);
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAndRetrieveUserUsingAdditionalSearchObject() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        authenticator.addSearchObject("OU=User Accounts");
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, TEST_PASSWORD);
        ADUser adUser = authenticator.retrieveLoggedInUser();

        assertNotNull("User not retrieved", adUser);
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAndRetrieveUserEmail() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, TEST_PASSWORD);
        ADUser adUser = authenticator.retrieveLoggedInUser();

        assertThat("User email not retrieved", adUser.getEmail(), is(equalTo(EXPECTED_EMAIL)));
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAndRetrieveUserFirstName() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, TEST_PASSWORD);
        ADUser adUser = authenticator.retrieveLoggedInUser();

        assertThat("User email not retrieved", adUser.getFirstName(), is(equalTo(EXPECTED_FIRST_NAME)));
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAndRetrieveUserLastName() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, TEST_PASSWORD);
        ADUser adUser = authenticator.retrieveLoggedInUser();

        assertThat("User email not retrieved", adUser.getLastName(), is(equalTo(EXPECTED_LAST_NAME)));
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAndRetrieveUserTitle() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, TEST_PASSWORD);
        ADUser adUser = authenticator.retrieveLoggedInUser();

        assertThat("User email not retrieved", adUser.getTitle(), is(equalTo(EXPECTED_TITLE)));
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAndRetrieveUserSamAccountName() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, TEST_PASSWORD);
        ADUser adUser = authenticator.retrieveLoggedInUser();

        assertThat("User email not retrieved", adUser.getSamAccountName(), is(equalTo(TEST_SAM_ACCOUNT)));
    }

    @Test
    @Ignore("Only run manually as you need to use real AD credentials")
    public void shouldAuthenticateAndRetrieveUserPrincipleName() {
        ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance();
        boolean authenticated = authenticator.authenticate(TEST_SAM_ACCOUNT, TEST_PASSWORD);
        ADUser adUser = authenticator.retrieveLoggedInUser();

        assertThat("User email not retrieved", adUser.getUserPrincipleName(), is(equalTo(EXPECTED_USER_PRINCIPLE_NAME)));
    }
}
