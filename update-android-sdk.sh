#!/bin/bash
# Update the SDK version in the gradle file
# sh update-android-sdk.sh

ANDROID_LIB_NAME="core"

latestSdkVersion=$(find ./acp-sdks/android -name "$ANDROID_LIB_NAME-*.aar" | cut -d'-' -f 3 | cut -d '.' -f 1,2,3)
latestSdkVersion="$latestSdkVersion\""

sed -E -i '' "s#(com\.adobe\.marketing\.mobile:*.*:)([^:'])+#\1$latestSdkVersion#g" android/build.gradle
echo "React Native Android updated to $latestSdkVersion"
