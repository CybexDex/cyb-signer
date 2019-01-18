#!/usr/bin/env bash

WHOAMI=`whoami`

echo -e "Stopping existing cyb-signer"
latest_pid=`ps auxwww | egrep "cybex.signer" | grep ${WHOAMI} | grep -v grep | awk '{print $2}'`

if [[ -n "${latest_pid}" ]]; then
   echo -e "Terminating pid: ${latest_pid}"
   kill -9 ${latest_pid}
   echo -e "Done"
   exit
fi

echo -e "Cannot find cyb-signer process."

