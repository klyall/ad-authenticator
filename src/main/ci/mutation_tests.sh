#!/bin/bash

set -e -x

pushd ad-authenticator
  mvn test-compile org.pitest:pitest-maven:mutationCoverage
  ls target/pit-reports/
popd

