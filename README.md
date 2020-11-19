# React Native AEP Core Extension

[![npm version](https://badge.fury.io/js/%40adobe%2Freact-native-acpcore.svg)](https://badge.fury.io/js/%40adobe%2Freact-native-acpcore) ![npm](https://img.shields.io/npm/dm/@adobe/react-native-acpcore) [![CircleCI](https://img.shields.io/circleci/project/github/adobe/react-native-acpcore/master.svg?logo=circleci)](https://circleci.com/gh/adobe/workflows/react-native-acpcore) ![NPM](https://img.shields.io/npm/l/@adobe/react-native-acpcore.svg)

`@adobe/react-native-acpcore` is a wrapper around the iOS, tvOS and Android [AEP Core SDK](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core) to allow for integration with React Native applications. Functionality to enable the Core extension is provided entirely through JavaScript documented below.

## Contents
- [Installation](#installation)
  - [iOS](#31-ios-project)
  - [Next steps](#4-next-steps)
- [Tests](#tests)
- [Usage](#usage)
  - [Initializing](#initializing)
  - [Core](#core)
  - [Identity](#identity)
  - [Lifecycle](#lifecycle)
  - [Signal](#signal)
- [Troubleshooting](#troubleshooting)	

## Installation

You need to install the SDK with [npm](https://www.npmjs.com/) and configure the native Android/iOS project in your react native project.

> Note: If you are new to React Native we suggest you follow the [React Native Getting Started](<https://facebook.github.io/react-native/docs/getting-started.html>) page before continuing.

### 1. Create React Native project

First create a React Native project:

```bash
react-native init MyReactApp
```
> Note: Follow [React Native tvos support](https://reactnative.dev/blog/2020/03/26/version-0.62#moving-apple-tv-to-react-native-tvos) to create app with tvos target.

### 2. Install JavaScript packages

Install and link the `@adobe/react-native-acpcore` package:

```bash
cd MyReactApp
npm install @adobe/react-native-acpcore
```

#### 2.1 Link
- **React Native 0.60+**


[CLI autolink feature](https://github.com/react-native-community/cli/blob/master/docs/autolinking.md) links the module while building the app.


- **React Native <= 0.59**


```bash
react-native link @adobe/react-native-acpcore
```

> Note: if using Cocoapods, run:

```bash
cd ios/ && pod install
```

### 3. Configure native projects

#### 3.1 iOS project

> (Only required for **React Native <= 0.59**)

In the Link Binary With Libraries section, click the + link and add the following frameworks and libraries:
* `UIKit.framework`
* `SystemConfiguration.framework`
* `WebKit.framework`
* `UserNotifications.framework`
* `libsqlite3.0.tbd`
* `libc++.tbd`
* `libz.tbd`

#### 4. Next steps

After you have installed Core, you can install additional AEP React Native extensions.

| Extension    | npm package                                                  |
| ------------ | ------------------------------------------------------------ |
| Analytics    | [![npm version](https://img.shields.io/npm/v/@adobe/react-native-acpanalytics.svg?color=green&label=%40adobe%2Freact-native-acpanalytics&logo=npm&style=flat-square)](https://badge.fury.io/js/%40adobe%2Freact-native-acpanalytics) |
| Audience     | [![npm version](https://img.shields.io/npm/v/@adobe/react-native-acpaudience.svg?color=green&label=%40adobe%2Freact-native-acpaudience&logo=npm&style=flat-square)](https://badge.fury.io/js/%40adobe%2Freact-native-acpaudience) |
| Campaign     | [![npm version](https://img.shields.io/npm/v/@adobe/react-native-acpcampaign.svg?color=green&label=%40adobe%2Freact-native-acpcampaign&logo=npm&style=flat-square)](https://badge.fury.io/js/%40adobe%2Freact-native-acpcampaign) |
| Media     | [![npm version](https://img.shields.io/npm/v/@adobe/react-native-acpmedia.svg?color=green&label=%40adobe%2Freact-native-acpmedia&logo=npm&style=flat-square)](https://badge.fury.io/js/%40adobe%2Freact-native-acpmedia) |
| Target       | [![npm version](https://img.shields.io/npm/v/@adobe/react-native-acptarget.svg?color=green&label=%40adobe%2Freact-native-acptarget&logo=npm&style=flat-square)](https://badge.fury.io/js/%40adobe%2Freact-native-acptarget) |
| User Profile | [![npm version](https://img.shields.io/npm/v/@adobe/react-native-acpuserprofile.svg?color=green&label=%40adobe%2Freact-native-acpuserprofile&logo=npm&style=flat-square)](https://badge.fury.io/js/%40adobe%2Freact-native-acpuserprofile) |

## Tests
This project contains jest unit tests which are contained in the `__tests__` directory, to run the tests locally:
```
make run-tests-locally
```

## Usage

### Initializing:

Initializing the SDK should be done in native code, documentation on how to initalize the SDK can be found [here](https://aep-sdks.gitbook.io/docs/getting-started/get-the-sdk#2-add-initialization-code). The linked documentation initalizes the User Profile extension which is not required, however, if you are interested in the User Profile extension for React Native you can find it [here](https://www.npmjs.com/package/@adobe/react-native-acpuserprofile).

Once you have added the initialization code to your app, be sure to set the SDK wrapper type to React Native before you start the SDK.

###### iOS:
```objective-c
[ACPCore setWrapperType:ACPMobileWrapperTypeReactNative];
```

###### Android:
```java
MobileCore.setWrapperType(WrapperType.REACT_NATIVE);
```

### [Core](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core)

##### Updating the SDK configuration:

```javascript
ACPCore.updateConfiguration({"yourConfigKey": "yourConfigValue"});
```

##### Getting the SDK version:
```javascript
ACPCore.extensionVersion().then(version => console.log("AdobeExperienceSDK: ACPCore version: " + version));
```

##### Getting the log level:
```javascript
ACPCore.getLogLevel().then(level => console.log("AdobeExperienceSDK: Log Level = " + level));
```

##### Controlling the log level of the SDK:
```javascript
import {ACPMobileLogLevel} from '@adobe/react-native-acpcore';

ACPCore.setLogLevel(ACPMobileLogLevel.VERBOSE);
```

##### Using the AEP Logging API:
```javascript
import {ACPMobileLogLevel} from '@adobe/react-native-acpcore';

ACPCore.log(ACPMobileLogLevel.ERROR, "React Native Tag", "React Native Message");
```

Note: `ACPMobileLogLevel` contains the following getters:

```javascript
const ERROR = "ACP_LOG_LEVEL_ERROR";
const WARNING = "ACP_LOG_LEVEL_WARNING";
const DEBUG = "ACP_LOG_LEVEL_DEBUG";
const VERBOSE = "ACP_LOG_LEVEL_VERBOSE";
```

##### Getting the current privacy status:
```javascript
ACPCore.getPrivacyStatus().then(status => console.log("AdobeExperienceSDK: Privacy Status = " + status));
```

##### Setting the privacy status:
```javascript
import {ACPMobilePrivacyStatus} from '@adobe/react-native-acpcore';

ACPCore.setPrivacyStatus(ACPMobilePrivacyStatus.OPT_IN);
```

Note: `ACPMobilePrivacyStatus` contains the following getters:

```javascript
const OPT_IN = "ACP_PRIVACY_STATUS_OPT_IN";
const OPT_OUT = "ACP_PRIVACY_STATUS_OPT_OUT";
const UNKNOWN = "ACP_PRIVACY_STATUS_UNKNOWN";
```

##### Getting the SDK identities:
```javascript
ACPCore.getSdkIdentities().then(identities => console.log("AdobeExperienceSDK: Identities = " + identities));
```

##### Dispatching an Event Hub event:
```javascript
import {ACPExtensionEvent} from '@adobe/react-native-acpcore';

var event = new ACPExtensionEvent("eventName", "eventType", "eventSource", {"testDataKey": "testDataValue"});
ACPCore.dispatchEvent(event);
```

##### Dispatching an Event Hub event with callback:
```javascript
import {ACPExtensionEvent} from '@adobe/react-native-acpcore';

var event = new ACPExtensionEvent("eventName", "eventType", "eventSource", {"testDataKey": "testDataValue"});
ACPCore.dispatchEventWithResponseCallback(event).then(responseEvent => console.log("AdobeExperienceSDK: responseEvent = " + responseEvent));
```

##### Dispatching an Event Hub response event:
```javascript
import {ACPExtensionEvent} from '@adobe/react-native-acpcore';

var responseEvent = new ACPExtensionEvent("responseEvent", "eventType", "eventSource", {"testDataKey": "testDataValue"});
var requestEvent = new ACPExtensionEvent("requestEvent", "eventType", "eventSource", {"testDataKey": "testDataValue"});
ACPCore.dispatchResponseEvent(responseEvent, requestEvent);
```

### [Identity](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core/identity)

##### Getting the extension version:
```javascript
ACPIdentity.extensionVersion().then(version => console.log("AdobeExperienceSDK: ACPIdentity version: " + version));
```

##### Registering the extension with Core:
```javascript
ACPIdentity.registerExtension();
```

##### Sync Identifier:
```javascript
ACPIdentity.syncIdentifier("identifierType", "identifier", ACPMobileVisitorAuthenticationState.AUTHENTICATED);
```


##### Sync Identifiers:
```javascript
ACPIdentity.syncIdentifiers({"id1": "identifier1"});
```

##### Sync Identifiers with Authentication State:
```javascript
import {ACPMobileVisitorAuthenticationState} from '@adobe/react-native-acpcore';

ACPIdentity.syncIdentifiersWithAuthState({"id1": "identifier1"}, ACPMobileVisitorAuthenticationState.UNKNOWN);
```

Note: `ACPMobileVisitorAuthenticationState` contains the following getters:

```javascript
const AUTHENTICATED = "ACP_VISITOR_AUTH_STATE_AUTHENTICATED";
const LOGGED_OUT = "ACP_VISITOR_AUTH_STATE_LOGGED_OUT";
const UNKNOWN = "ACP_VISITOR_AUTH_STATE_UNKNOWN";
```

##### Setting the advertising identifier:

```javascript
ACPCore.setAdvertisingIdentifier("adID");
```

##### Append visitor data to a URL:

```javascript
ACPIdentity.appendVisitorInfoForURL("test.com").then(urlWithVisitorData => console.log("AdobeExperienceSDK: VisitorData = " + urlWithVisitorData));
```

##### Get visitor data as URL query parameter string:

```javascript
ACPIdentity.getUrlVariables().then(urlVariables => console.log("AdobeExperienceSDK: UrlVariables = " + urlVariables));
```

##### Get Identifiers:

```javascript
ACPIdentity.getIdentifiers().then(identifiers => console.log("AdobeExperienceSDK: Identifiers = " + identifiers));
```

##### Get Experience Cloud IDs:
```javascript
ACPIdentity.getExperienceCloudId().then(cloudId => console.log("AdobeExperienceSDK: CloudID = " + cloudId));
```

##### Setting the push identifier:
```javascript
ACPCore.setPushIdentifier("pushIdentifier");
```

##### VisitorID Class:
```javascript
import {ACPVisitorID} from '@adobe/react-native-acpcore';

var visitorId = new ACPVisitorID(idOrigin?: string, idType: string, id?: string, authenticationState?: ACPMobileVisitorAuthenticationState)
```

### [Lifecycle](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core/lifecycle)

> Note: Implementing Lifecycle via Javascript may lead to inaccurate Lifecycle metrics, therefore we recommend implementing Lifecycle in native [Android and iOS code](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core/lifecycle). However, if implementing Lifecycle in Javascript is required you can use the [`AppState`](https://facebook.github.io/react-native/docs/appstate) to receive notifications about when your app enters foreground/background. Based on the `AppState` you can make the corresponding calls to `lifecycleStart` and `lifecyclePause`.

##### Getting the extension version:
```javascript
ACPLifecycle.extensionVersion().then(version => console.log("AdobeExperienceSDK: ACPLifecycle version: " + version));
```

##### Registering the extension with Core:
```javascript
ACPLifecycle.registerExtension();
```

##### Starting a lifecycle event:
```javascript
ACPCore.lifecycleStart({"lifecycleStart": "myData"});
```

##### Pausing a lifecycle event:
```javascript
ACPCore.lifecyclePause();
```

### [Signal](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core/signals)
##### Getting the extension version:
```javascript
ACPSignal.extensionVersion().then(version => console.log("AdobeExperienceSDK: ACPSignal version: " + version));
```

##### Registering the extension with Core:
```javascript
ACPSignal.registerExtension();
```

##### Collecting PII:
```javascript
ACPCore.collectPii({"myPii": "data"});
```

## Troubleshooting
1. A few different errors can result from not running `react-native link @adobe/react-native-acpcore` or when the autolinker in React Native 0.60.x does not properly link the SDK when building.

- `TypeError: null is not an object (evaluating RCTACPCore...)`

- `ACPCore.h` not found when importing `<RCTACPCore/ACPCore.h>`

- No SDK logs or errors after implementing in Javascript

```bash
react-native link @adobe/react-native-acpcore
cd ios/ && pod install # only if using pods
```
Another possible issue is that your application is built using [`Expo`](https://expo.io/). Unfortunately `Expo` does not support native modules out of the box. Please see [Ejecting to ExpoKit](https://docs.expo.io/versions/latest/expokit/eject/).

2. `Specs satisfying the RCTACPCore (from ../node_modules/@adobe/react-native-acpcore) dependency were found, but they required a higher minimum deployment target.`

- The AEP SDK's require at least iOS 10.0 to function properly. If you see this error, ensure your `Podfile` specfies at least iOS 10.0.

## Contributing
See [CONTRIBUTING](CONTRIBUTING.md)

## License
See [LICENSE](LICENSE)
