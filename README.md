
# bourbon-platform-react-native-core

https://wiki.corp.adobe.com/display/adms/React+Native


## Setup

`npm install`

#### Andriod

Open `android/build.gradle` in Android studio

#### iOS:

Open `ios/RCTACPCore.xcodeproj`

Note: This the Xcode project does not include all dependencies to build. If you want to build the project you should run `pod install` then open the workspace.


## Installation

You need to install the SDK with [npm](https://www.npmjs.com/) and configure the native Android/iOS project in your react native project.

### 1. Create React Native project

First create a React Native project:

```bash
react-native init MyReactApp
```

### 2. Install JavaScript packages

Install and link the `react-native-acpcore` package:

```bash
npm install react-native-acpcore
react-native link react-native-acpcore
```

### 3. Configure native projects

#### 3.1 Android project

Navigate to `MainApplication.java` under `app/src/main/java/com/<project name>/`  and add a call to `RCTACPCoreModule.setApplication()` inside of `onCreate()`

```java
import com.adobe.marketing.mobile.reactnative.RCTACPCorePackage; // import the package
//...

@Override
public void onCreate() {
	super.onCreate();
	//...
	RCTACPCoreModule.setApplication(this); // add this line
}
```

## Usage

### [Core](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core)

##### Initalizing the SDK:

```javascript
import {ACPCore, ACPLifecycle, ACPIdentity, ACPSignal, ACPMobileLogLevel} from 'react-native-acpcore';

initSDK() {
    ACPCore.setLogLevel("ACP_LOG_LEVEL_VERBOSE");
    ACPCore.configureWithAppId("yourAppId");
    ACPLifecycle.registerExtension();
    ACPIdentity.registerExtension();
    ACPSignal.registerExtension();
    ACPCore.start();
}
```

##### Configuring the SDK with a local file:

```javascript
ACPCore.configureWithFileInPath("pathToConfigFile");
```

##### Updating the SDK configuration:

```javascript
ACPCore.updateConfiguration({"yourConfigKey": "yourConfigValue"});
```

##### Getting the SDK version:

```javascript
ACPCore.extensionVersion().then(version => console.log("AMSDK: ACPCore version: " + version));
```

##### Controlling the log level of the SDK:

```javascript
ACPCore.setLogLevel("ACP_LOG_LEVEL_VERBOSE");
```

Note: `ACPMobileLogLevel` is defined as:

```javascript
type ACPMobileLogLevel =
  | "ACP_LOG_LEVEL_ERROR"
  | "ACP_LOG_LEVEL_WARNING"
  | "ACP_LOG_LEVEL_DEBUG"
  | "ACP_LOG_LEVEL_VERBOSE";
```

##### Setting the privacy status:

```javascript
ACPCore.setPrivacyStatus("ACP_PRIVACY_STATUS_OPT_IN");
```

Note: `ACPMobilePrivacyStatus` is defined as:

```javascript
type ACPMobilePrivacyStatus =
  | "ACP_PRIVACY_STATUS_OPT_IN"
  | "ACP_PRIVACY_STATUS_OPT_OUT"
  | "ACP_PRIVACY_STATUS_UNKNOWN";
```

##### Getting the current privacy status:

```javascript
ACPCore.getPrivacyStatus().then(status => console.log("AMSDK: Privacy Status = " + status));
```

##### Getting the SDK identities:

```javascript
ACPCore.getSdkIdentities().then(identities => console.log("AMSDK: Identities = " + identities));
```

##### Dispatching an Event Hub event:

```javascript
import {ACPExtensionEvent} from 'react-native-acpcore';

var event = new ACPExtensionEvent("eventName", "eventType", "eventSource", {"testDataKey": "testDataValue"});
ACPCore.dispatchEvent(event);
```

##### Dispatching an Event Hub event with callback:

```javascript
import {ACPExtensionEvent} from 'react-native-acpcore';

var event = new ACPExtensionEvent("eventName", "eventType", "eventSource", {"testDataKey": "testDataValue"});
ACPCore.dispatchEventWithResponseCallback(event).then(responseEvent => console.log("AMSDK: responseEvent = " + responseEvent));
```

##### Dispatching an Event Hub response event:

```javascript
import {ACPExtensionEvent} from 'react-native-acpcore';

var responseEvent = new ACPExtensionEvent("responseEvent", "eventType", "eventSource", {"testDataKey": "testDataValue"});
var requestEvent = new ACPExtensionEvent("requestEvent", "eventType", "eventSource", {"testDataKey": "testDataValue"});
ACPCore.dispatchResponseEvent(responseEvent, requestEvent);
```

### [Identity](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core/identity)

##### Getting the extension version:

```javascript
ACPIdentity.extensionVersion().then(version => console.log("AMSDK: ACPIdentity version: " + version));
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
ACPIdentity.syncIdentifiersWithAuthState({"id1": "identifier1"}, "ACP_VISITOR_AUTH_STATE_AUTHENTICATED");
```

##### Setting the advertising identifier:

```javascript
ACPCore.setAdvertisingIdentifier("adID");
```

Note: `ACPMobileVisitorAuthenticationState` is defined as:

```javascript
type ACPMobileVisitorAuthenticationState =
  | "ACP_VISITOR_AUTH_STATE_AUTHENTICATED"
  | "ACP_VISITOR_AUTH_STATE_LOGGED_OUT"
  | "ACP_VISITOR_AUTH_STATE_UNKNOWN";
```

##### Append visitor data to a URL:

```javascript
ACPIdentity.appendVisitorInfoForURL("test.com").then(urlWithVisitorData => console.log("AMSDK: VisitorData = " + urlWithVisitorData));
```

##### Get Identifiers:

```javascript
ACPIdentity.getIdentifiers().then(identifiers => console.log("AMSDK: Identifiers = " + identifiers));
```

##### Get Experience Cloud IDs:

```javascript
ACPIdentity.getExperienceCloudId().then(cloudId => console.log("AMSDK: CloudID = " + cloudId));
```

##### Setting the push identifier:

```javascript
ACPCore.setPushIdentifier("pushIdentifier");
```

##### VisitorID Class:

```javascript
import {ACPVisitorID} from 'react-native-acpcore';

var visitorId = new ACPVisitorID(idOrigin?: string, idType: string, id?: string, authenticationState?: ACPMobileVisitorAuthenticationState)
```

### [Lifecycle](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core/lifecycle)

##### Getting the extension version:

```javascript
ACPLifecycle.extensionVersion().then(version => console.log("AMSDK: ACPLifecycle version: " + version));
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

### [Signal](https://aep-sdks.gitbook.io/docs/using-mobile-extensions/mobile-core/signal)

##### Getting the extension version:

```javascript
ACPSignal.extensionVersion().then(version => console.log("AMSDK: ACPSignal version: " + version));
```

##### Registering the extension with Core:

```javascript
ACPSignal.registerExtension();
```

##### Collecting PII:

```javascript
ACPCore.collectPii({"myPii": "data"});
```
