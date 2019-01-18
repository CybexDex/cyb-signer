#!/usr/bin/env bash

CURDIR=`pwd`

SIGNER_HOME="${CURDIR}/.."

mkdir -vp ${SIGNER_HOME}/logs

WHOAMI=`whoami`

cd ..

echo -e "Build cyb-signer with maven"
mvn package -DskipTests

cd scripts
echo -e "Done"
