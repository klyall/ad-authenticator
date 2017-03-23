#!/bin/bash

set -e -x

pushd ad-authenticator
	cat /etc/hosts
	ifconfig
    env
	mvn sonar:sonar -Dsonar.host.url=$SONAR_HOST
popd

