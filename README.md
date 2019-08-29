# React Native AEP Core Extension

[![npm version](https://badge.fury.io/js/%40adobe%2Freact-native-acpcore.svg)](https://badge.fury.io/js/%40adobe%2Freact-native-acpcore) [![CircleCI](https://img.shields.io/circleci/project/github/adobe/react-native-acpcore/master.svg?logo=circleci)](https://circleci.com/gh/adobe/workflows/react-native-acpcore) ![NPM](https://img.shields.io/npm/l/@adobe/react-native-acpcore.svg)

`@adobe/react-native-acpcore` is a wrapper around the iOS and Android [AEP Core SDK](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core) to allow for integration with React Native applications. Functionality to enable the Core extension is provided entirely through JavaScript documented below.

## Contents
- [Installation](#installation)
  - [Android](#31-android-project)
  - [iOS](#32-ios-project)
- [Tests](#tests)
- [Usage](#usage)
	- [Core](#core)
	- [Identity](#identity)
	- [Lifecycle](#lifecycle)
	- [Signal](#signal)

## Installation

You need to install the SDK with [npm](https://www.npmjs.com/) and configure the native Android/iOS project in your react native project.

> Note: If you are new to React Native we suggest you follow the [React Native Getting Started](<https://facebook.github.io/react-native/docs/getting-started.html>) page before continuing.

### 1. Create React Native project

First create a React Native project:

```bash
react-native init MyReactApp
```

### 2. Install JavaScript packages

Install and link the `@adobe/react-native-acpcore` package:

```bash
cd MyReactApp
npm install @adobe/react-native-acpcore
react-native link @adobe/react-native-acpcore
```

### 3. Configure native projects

#### 3.1 Android project

Navigate to `MainApplication.java` under `android/app/src/main/java/com/<project-name>/MainApplication.java` and add a call to `MobileCore.setApplication(this)` inside of `onCreate()`.

```java
import com.adobe.marketing.mobile.MobileCore; // import MobileCore

@Override
public void onCreate() {
	super.onCreate();
	//...
	MobileCore.setApplication(this); // add this line
}
```

#### 3.2 iOS project
In the Link Binary With Libraries section, click the + link and add the following frameworks and libraries:
* `UIKit.framework`
* `SystemConfiguration.framework`
* `WebKit.framework`
* `UserNotifications.framework`
* `libsqlite3.0.tbd`
* `libc++.tbd`
* `libz.tbd`

Note: If you plan to use the AEP SDK in your native iOS code you will need to import the appropriate headers with the following format: `#import <RCTACPCore/ACPCore.h>`

## Tests
This project contains jest unit tests which are contained in the `__tests__` directory, to run the tests locally:
```
make run-tests-locally
```

## Usage
### [Core](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core)
##### Initializing the SDK:

It is recommended to initialize the SDK via native code inside your AppDelegate and MainApplication in iOS and Android respectively. However, you can still initialize the SDK in Javascript.

**iOS:**

```objective-c
// Import the SDK
#import <RCTACPCore/ACPCore.h>
#import <RCTACPCore/ACPLifecycle.h>
#import <RCTACPCore/ACPIdentity.h>
#import <RCTACPCore/ACPSignal.h>

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
  //...
  [ACPCore configureWithAppId:@"yourAppId"];
  [ACPCore setWrapperType:ACPMobileWrapperTypeReactNative];
  [ACPIdentity registerExtension];
  [ACPLifecycle registerExtension];
  [ACPSignal registerExtension];

  [ACPCore start:nil];
}
```

**Android:**

```java
// Import the SDK
import com.adobe.marketing.mobile.MobileCore;
import com.adobe.marketing.mobile.Identity;
import com.adobe.marketing.mobile.Lifecycle;
import com.adobe.marketing.mobile.Signal;
import com.adobe.marketing.mobile.WrapperType;

@Override
public void onCreate() {
  //...
  MobileCore.setApplication(this);
  MobileCore.configureWithAppID("yourAppId");
  MobileCore.setWrapperType(WrapperType.REACT_NATIVE);
  try {
    Identity.registerExtension();
    Lifecycle.registerExtension();
    Signal.registerExtension();
  } catch (Exception e) {
    // handle exception
  }

  MobileCore.start(null);
}
```

**Javascript:**

```javascript
import {ACPCore, ACPLifecycle, ACPIdentity, ACPSignal, ACPMobileLogLevel} from '@adobe/react-native-acpcore';

initSDK() {
    ACPCore.setLogLevel(ACPMobileLogLevel.VERBOSE);
    ACPCore.configureWithAppId("yourAppId");
    ACPLifecycle.registerExtension();
    ACPIdentity.registerExtension();
    ACPSignal.registerExtension();
    ACPCore.start();
}
```

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

> Note: Implementing Lifecycle via Javascript may lead to inaccurate Lifecycle metrics, therefore we recommend implementing Lifecycle in native Android and iOS code.

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

## Contributing
See [CONTRIBUTING](CONTRIBUTING.md)

## License
See [LICENSE](LICENSE)
