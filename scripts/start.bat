@echo off

set CURDIR=%cd%
set SIGNER_HOME="%CURDIR%\.."

mkdir %SIGNER_HOME%\logs

echo Launching cyb-signer
java -DenvFile="%SIGNER_HOME%\scripts\env.properties" -cp "%SIGNER_HOME%\target\cyb-signer-1.0-SNAPSHOT.jar" io.cybex.signer.server.api.SignerApiVerticle >> %SIGNER_HOME%\logs\cyb-signer.log

echo Done

