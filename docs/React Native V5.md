# Intro:

React Native offers something called "native modules" which allow us the capability of reusing our Java/Objective-C/C++ in JavaScript without having to reimplement it. This lets us wrap our native SDK's into a Native Module for use in React Native apps.

Recommend intro on native modules:
https://facebook.github.io/react-native/docs/native-modules-ios
https://facebook.github.io/react-native/docs/native-modules-android

TLDR:
1. You can think of a Native Module as a library/framework. I anticipate we will have a Core Module, Target Module, Analytics Module, etc.
2. Methods exposed to RN **CANNOT** return a value. Instead, we must use a `Callback`, or a more preferred way is using a `Promise`.
3. You cannot pass any object/type between RN and native. Supported types include standard JSON types. If we want to pass an object such as an `ACPExtensionEvent` we need to define an `ACPExtensionEvent` type in JS and do some extra leg work to convert them over.
4. We need to consider threading carefully

# How do we create a Native Module?

Lucky for us, there is a [tool](https://facebook.github.io/react-native/docs/native-modules-setup) that is endorsed by Facebook to create a template Native Module.

From my experimentation I've been creating native modules with the following command:

`react-native-create-library --prefix RCTACP --module-prefix adobe-mobile-marketing  --package-identifier com.adobe.marketing.mobile --platforms ios,android --author-name Adobe Core`

I found that I needed to update some parts of the gradle file in the Android project, see the POC for more details.

#### React Versions

`react-native@0.55.4`

`react@16.3.1`



# Project Structure:
After creating a Native Module you'll see a package.json which defines the name, version, dependencies, and more.

In the `ios` directory, you'll find an Xcode project which holds our iOS native modules. The source files are either wrapping our API's or helping bridge data between RN and iOS.

In the `android` directory, you'll find an Android project which does the same. Defines modules, wraps API's, bridges data.

# Example API Wrap:

Say we want to wrap our `getPrivacyStatus` method in Core. In Java we would do the following:

```java
// Must be in a class that extends ReactContextBaseJavaModule
@ReactMethod
public void getPrivacyStatus(final Promise promise) {
    MobileCore.getPrivacyStatus(new AdobeCallback<MobilePrivacyStatus>() {
        @Override
        public void call(MobilePrivacyStatus mobilePrivacyStatus) {
            promise.resolve(mobilePrivacyStatus.getValue());
        }
    });
}
```



In Objective-C we would wrap the `getPrivacyStatus` like:

```objective-c
// Must be in a class that invokes RCT_EXPORT_MODULE()
RCT_EXPORT_METHOD(getPrivacyStatus: (RCTPromiseResolveBlock) resolve) {
    [ACPCore getPrivacyStatus:^(ACPMobilePrivacyStatus status) {
        resolve([RCTACPCoreDataBridge stringFromPrivacyStatus:status]);
    }];
}
```



In our JavaScript bridging class (`ACPCore.js`), we can define the method which calls our Native Module:

```javascript
static getPrivacyStatus(): Promise<ACPMobilePrivacyStatus> {
	return RCTACPCore.getPrivacyStatus();
}
```



Then in a React Native app, we can invoke `getPrivacyStatus` in Javascript with:

**TODO**


# What if there are API differences between Android and iOS?
Let's say I have an API called `doiOSMagic` in `ACPAnalytics.h` and I do not want this method to ever be called on Android (It doesn’t even exist in `Analytics.java`).

- Option 1: Do not include this API in our React Native module `.js` file, and force native calls in either Java or Objective-C.
- Option 2: Create a no-op method in our React Native Module.

Example of option 2:

Java:

```java
@ReactMethod
public void doiOSMagic() {
    Log.d(getName(), "doiOSMagic cannot be invoked from an Android device.");
}
```

Objective-C:

```objective-c
RCT_EXPORT_METHOD(doiOSMagic) {
    [ACPiOSWizard doiOSMagic];
}
```

Javascript:

```javascript
static doiOSMagic() {
	ACPiOSWizard.doiOSMagic();
}
```

It seems option 2 is the safer approach, since even if you don't define the function in your `.js` file, you can still invoke from it Javascript, leading to undefined behavior when called from the unsupported platform (probably a crash). Need more investigation.




# Naming Conventions

It seems that there isn’t a strict naming guideline in React Native. Some native module’s have prefixes and some don’t. We can either use the `ACP` prefix or not, in the POC we have the `ACP` prefix for our exported types and exported modules.

In our internal source files (could be public if we open source 😁) the POC has the prefix `RCTACP`, this is because:
1. We cannot use the name `ACPAnalytics.h` since that would conflict with the native iOS header file.
2. We cannot use the name `Analytics.java` since that would conflict with the native Android file.

We could do `ACPReactAnalytics.h`, but meh...

# Threading:
Our native module should not have any assumptions about what thread it is being called on.

**We need to determine if we want to force our SDK to run on the main thread, background, or on its own serial queue.**

https://facebook.github.io/react-native/docs/native-modules-ios#threading
https://facebook.github.io/react-native/docs/native-modules-android#threading

# Organizing Repos:
From all examples I've seen online, these wrappers tend to live in their own repos, the current POC lives in its own repo.

# Testing
From looking over many other React native modules, testing is quite limited (most of the time they doesn’t exist), mainly UI tests. We should at the minimum write tests for our data bridging classes.

More investigation required.

# CI/CD:

More investigation required.



# 3rd Party Considerations

There does not appear to be anyway to develop a 3rd party extension in Javascript/React Native. The best option for a 3rd party would be to develop their extension nativley and wrap it in a react native module. If Javascript extensions become a thing, then this should be possible in the future.

More investigation should be done by the extensions squad

# Shipping a Module/Extension:

To distribute these native modules we will use `npm` , we anticipate the steps to look something like:
1. Create an account on the `npm` [website](https://www.npmjs.com/)
2. From the command line, use `npm login` to sign into your account
3. `cd into nativeModuleDirectory`
4. Update the version number in `package.json`
5. Publish to `npm` using `npm publish`



Are we interested in doing a React Native beta?

# Example Install/Usage:

TODO 

# Shipping Core:
Since Core encapsulates Core, Identity, Lifecycle, and Signal, we believe it is best that the repo containing the Core native module should also include the Identity, Lifecycle, and Signal native modules. This makes releasing much easier, as we can release our Core npm module which will contain the Core, Identity,  Lifecycle, Signal. (Not seeing how this would work if they lived in their own repos.)
# Open Source:
These native modules _may_ be good candidates to test the waters with open source.

# POC

An initial POC of the native module and been implemented and can be found here. **TODO** INSERT LINK

# Helpful links: