#!/bin/bash

set -e -x

pushd ad-authenticator
  mvn verify
popd

