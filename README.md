AD-Authenticator
================

ad-authenticator is a Java library for authenticating a user against Active Directory and then retrieving their user details.

The library is intended to be used as an easy to use component of other systems such as server authentication plug-ins.  

Usage
=====
The simplest way to use the library is to let it auto-discover the DNS domain and fetch a list of active directory servers.
 
    ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance(); 

This will use the domain name of the host computer to lookup DNS for Active Directory servers.

Alternatively the domain name can be provided:

    ADAuthenticator authenticator = ADAuthenticatorFactory.newInstance(DOMAIN_NAME);

To authenticate a user call the authenticate method with their credentials:

    boolean authenticated = authenticator.authenticate(username, password);

The username can be either the sAMAccountName or the email name (i.e. "firstname.lastname" if email address is "firstname.lastname@company.com") 

TODO: How to set go faster stripe

To retrieve the user's details call the retrieveLoggedInUser method: 

    ADUser adUser = authenticator.retrieveLoggedInUser();

Testing
=======
A suite of unit tests has been used written to test the easily mocked parts of the library. This can be tested by running:
 
    mvn test
    
An integration test covers the remainder of the logic by directly accessing an Active Directory instance. 
By default these tests are ignored as the credentials and expected user details needs to be provided in the test. 
Once these have been specified in the constants are the top of the test, comment out the @Ignore annotations and run:
  
    mvn integration-test  

Bugs & Issues
=============
Please use GitHub issues to report bugs, features, or other problems.

License & Authors
=================
The project was adapted from sonar-ad-plugin developed by Jiji Sasidharan (https://github.com/programmingforliving/sonar-ad-plugin) to extract the 
core AD authentication logic. Thanks Jiji!

- Copyright (C) 2015 Keith Lyall
- Copyright (C) 2012-2014 Jiji Sasidharan (http://programmingforliving.com/)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
