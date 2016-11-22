#!/bin/bash

if [ ! -f linux-amd64-github-release.tar.bz2 ]; then
    wget https://github.com/aktau/github-release/releases/download/v0.6.2/linux-amd64-github-release.tar.bz2 -O linux-amd64-github-release.tar.bz2
fi
tar xf linux-amd64-github-release.tar.bz2

VERSION="0.1.0"
GHRELEASE_BIN="./bin/linux/amd64/github-release"

$GHRELEASE_BIN release \
  --user ai-traders \
  --repo swagger-composer \
  --tag $VERSION \
  --name $VERSION \
  --pre-release

$GHRELEASE_BIN upload \
  --user ai-traders \
  --repo swagger-composer \
  --tag $VERSION \
  --name "swagger-composer-$VERSION.jar" \
  --file build/swagger-composer-$VERSION.jar
