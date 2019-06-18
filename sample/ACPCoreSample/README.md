# ACPCoreSample

> Note: Commands are assuming you're in the root directory of the repository.

> Note: If you are new to React Native we suggest you follow the [React Native Getting Started](<https://facebook.github.io/react-native/docs/getting-started.html>) page before continuing.

How to run the sample app:

### Add your App Id:
In `App.js`, find the call to `configureWithAppId` and add your app id.

#### Run instructions for iOS:

```
cd sample/ACPCoreSample/ && npm install && react-native run-ios
```

> Note: If you see an error "Can't find simulator for "iPhoneX"", this is a known [issue](https://github.com/facebook/react-native/issues/23256) with React Native and you should try running the sample app inside of Xcode.

or
```
cd sample/ACPCoreSample/ && npm install
```
Then, open the Xcode project under the `ios` directory and hit run.

#### Run instructions for Android:

Have an Android emulator running (quickest way to get started), or a device connected. https://developer.android.com/studio/run/emulator-commandline

```
cd sample/ACPCoreSample/ && npm install && react-native run-android
```

### Troubleshooting

If you're having issues running the sample app, ensure you can at least run a default react native app.

```
react-native init MyReactApp
cd MyReactApp
react-native run-ios or react-native run-android
```
