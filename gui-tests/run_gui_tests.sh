#! /bin/bash

ORIGINAL_WORKING_DIR=`pwd`
SCRIPT_WORKING_DIR=`dirname "$0"`

echo "Building the plugin."
cd "$SCRIPT_WORKING_DIR/.."
rm -fr "./build"
./gradlew buildPlugin


echo "Preparing sandbox."
cd "$SCRIPT_WORKING_DIR/gui-tests"
rm -fr "../build"
./gradlew :gui-tests:test --tests "com.redhat.devtools.intellij.quarkus.PrepareSandboxTestSuite"


echo "Installing the plugin."
mkdir -p "../build/gui-tests/idea-sandbox/plugins/"
unzip "../../build/distributions/*.zip" -d "../build/gui-tests/idea-sandbox/plugins/"
rm -fr "../build/gui-tests/idea-sandbox/system-test"


echo "Running the tests."
./gradlew :gui-tests:test --tests "com.redhat.devtools.intellij.quarkus.QuarkusPluginGuiTestSuite"


cd "$ORIGINAL_WORKING_DIR"
