@echo off

set CURDIR=%cd%
set SIGNER_HOME="%CURDIR%\.."

mkdir %SIGNER_HOME%\logs

cd ..

echo Build cyb-signer with maven
mvn package -DskipTests

cd scripts
echo Done
