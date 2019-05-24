#!/bin/bash
set -e

EXTENSION_NAME=react-native-acpcore

echo 'Installing npm packages'
npm install

echo 'Unlinking React Native Module'
react-native unlink @adobe/$EXTENSION_NAME

echo 'Copying React Native Module into Sample App'
rm -r node_modules/@adobe/$EXTENSION_NAME
mkdir node_modules/@adobe/$EXTENSION_NAME
cd ../.. && npm pack
cd sample/ACP*Sample/
cp ../../*.tgz node_modules/@adobe/
tar -xvzf node_modules/@adobe/*.tgz --directory=node_modules/@adobe/
rm node_modules/@adobe/*.tgz
rm -r node_modules/@adobe/$EXTENSION_NAME
mv node_modules/@adobe/package node_modules/@adobe/$EXTENSION_NAME

echo 'Linking'
react-native link
