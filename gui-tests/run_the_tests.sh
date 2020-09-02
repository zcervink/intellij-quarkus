#! /bin/bash

ORIGINAL_WORKING_DIR=`pwd`
SCRIPT_WORKING_DIR=`dirname "$0"`

cd "$SCRIPT_WORKING_DIR/.."

./gradlew clean
./gradlew prepareUiTestingSandbox
cp -R "./build/idea-sandbox/plugins-uiTest/Quarkus Tools" "./gui-tests/build/idea-sandbox/plugins-uiTest"
./gradlew gui-tests:runIdeForUiTests & ./gradlew gui-tests:test

cd "$ORIGINAL_WORKING_DIR"



