#!/usr/bin/env bash

CURDIR=`pwd`

SIGNER_HOME="${CURDIR}/.."

mkdir -vp ${SIGNER_HOME}/logs

WHOAMI=`whoami`

echo -e "Stopping existing cyb-signer"
latest_pid=`ps auxwww | egrep "cybex.signer" | grep ${WHOAMI} | grep -v grep | awk '{print $2}'`

if [[ -n "${latest_pid}" ]]; then
   echo -e "Terminating pid: ${latest_pid}"
   kill -9 ${latest_pid}
fi

sleep 1
latest_pid=`ps auxwww | egrep "cybex.signer" | grep ${WHOAMI} | grep -v grep | awk '{print $2}'`

if [[ -n "${latest_pid}" ]]; then
   echo -e "Cyb-signer process cannot be terminated. Its pid: ${latest_pid}. Please check."
   echo -e "Exit"
   exit
fi

cd ..

echo -e "Launch cyb-signer"
nohup java -DenvFile="${SIGNER_HOME}/scripts/env.properties" -cp "${SIGNER_HOME}/target/cyb-signer-1.0-SNAPSHOT.jar" io.cybex.signer.server.api.SignerApiVerticle >> ${SIGNER_HOME}/logs/cyb-signer.log &

cd scripts

echo -e "Done"
