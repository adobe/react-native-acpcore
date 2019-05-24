#!/bin/bash
# Update the SDK version in the gradle file
# sh update-android-sdk.sh --latestSdkVersion=1.1.1

set -e

while [ "$1" != "" ]; do
    PARAM=`echo $1 | awk -F= '{print $1}'`
    VALUE=`echo $1 | awk -F= '{print $2}'`
    case $PARAM in
        --latestSdkVersion)
            latestSdkVersion="$VALUE\"" ;;
        *)
    esac
    shift
done

if [ -z $latestSdkVersion ]; then
    echo "--latestSdkVersion is empty.\nExample usage:\nsh update-android-sdk.sh --latestSdkVersion=1.1.1"
    exit 1
fi

sed -E -i '' "s#(com\.adobe\.marketing\.mobile:*.*:)([^:'])+#\1$latestSdkVersion#g" android/build.gradle

echo "React Native Android updated to $latestSdkVersion"
