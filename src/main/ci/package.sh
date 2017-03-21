#!/bin/bash

set -e -x

pushd ad-authenticator
  mvn -Dmaven.test.skip=true package
  ls target/
popd

