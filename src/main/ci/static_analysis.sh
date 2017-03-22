#!/bin/bash

set -e -x

pushd ad-authenticator
	ifconfig
    env
	mvn sonar:sonar -Dsonar.host.url=http://sonar:9000
popd

