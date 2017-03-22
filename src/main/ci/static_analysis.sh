#!/bin/bash

set -e -x

pushd ad-authenticator
	mvn sonar:sonar -Dsonar.host.url=http://dev:9000
popd

