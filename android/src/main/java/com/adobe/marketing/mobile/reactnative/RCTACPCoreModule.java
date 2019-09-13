/*
Copyright 2019 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
*/
package com.adobe.marketing.mobile.reactnative;

import com.adobe.marketing.mobile.AdobeCallback;
import com.adobe.marketing.mobile.Event;
import com.adobe.marketing.mobile.ExtensionError;
import com.adobe.marketing.mobile.ExtensionErrorCallback;
import com.adobe.marketing.mobile.LoggingMode;
import com.adobe.marketing.mobile.MobileCore;
import com.adobe.marketing.mobile.MobilePrivacyStatus;
import com.adobe.marketing.mobile.WrapperType;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import android.app.Application;

import java.util.concurrent.atomic.AtomicBoolean;

public class RCTACPCoreModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private static String FAILED_TO_CONVERT_EVENT_MESSAGE = "Failed to convert map to Event";
    private static AtomicBoolean hasStarted = new AtomicBoolean(false);

    public RCTACPCoreModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        MobileCore.setWrapperType(WrapperType.REACT_NATIVE);
    }

    // Required for RN modules
    @Override
    public String getName() {
        return "ACPCore";
    }

    public static void setApplication(final Application application) {
      MobileCore.setApplication(application);
    }

    @ReactMethod
    public void extensionVersion(final Promise promise) {
        promise.resolve(MobileCore.extensionVersion());
    }

    @ReactMethod
    public void start(final Promise promise) {
        if (hasStarted.get()) {
            promise.resolve(true);
            return;
        }

        MobileCore.start(new AdobeCallback() {
            @Override
            public void call(Object obj) {
            hasStarted.set(true);
            promise.resolve(true);
            }
        });
    }

    @ReactMethod
    public void configureWithAppId(final String appId) {
        MobileCore.configureWithAppID(appId);
    }

    @ReactMethod
    public void updateConfiguration(final ReadableMap configMap) {
        MobileCore.updateConfiguration(RCTACPMapUtil.toMap(configMap));
    }

    @ReactMethod
    public void setLogLevel(final String mode) {
        LoggingMode logMode = RCTACPCoreDataBridge.loggingModeFromString(mode);
        MobileCore.setLogLevel(logMode);
    }

    @ReactMethod
    public void getLogLevel(final Promise promise) {
        promise.resolve(RCTACPCoreDataBridge.stringFromLoggingMode(MobileCore.getLogLevel()));
    }

    @ReactMethod
    public void log(final String mode, final String tag, final String message) {
        LoggingMode logMode = RCTACPCoreDataBridge.loggingModeFromString(mode);
        MobileCore.log(logMode, tag, message);
    }

    @ReactMethod
    public void setPrivacyStatus(final String privacyStatus) {
        MobileCore.setPrivacyStatus(RCTACPCoreDataBridge.privacyStatusFromString(privacyStatus));
    }

    @ReactMethod
    public void getPrivacyStatus(final Promise promise) {
        MobileCore.getPrivacyStatus(new AdobeCallback<MobilePrivacyStatus>() {
            @Override
            public void call(MobilePrivacyStatus mobilePrivacyStatus) {
                promise.resolve(RCTACPCoreDataBridge.stringFromPrivacyStatus(mobilePrivacyStatus));
            }
        });
    }

    @ReactMethod
    public void getSdkIdentities(final Promise promise) {
        MobileCore.getSdkIdentities(new AdobeCallback<String>() {
            @Override
            public void call(String value) {
                promise.resolve(value);
            }
        });
    }

    @ReactMethod
    public void dispatchEvent(final ReadableMap eventMap, final Promise promise) {
        Event event = RCTACPCoreDataBridge.eventFromReadableMap(eventMap);
        if (event == null) {
            promise.reject(getName(), FAILED_TO_CONVERT_EVENT_MESSAGE, new Error(FAILED_TO_CONVERT_EVENT_MESSAGE));
            return;
        }

        MobileCore.dispatchEvent(event, new ExtensionErrorCallback<ExtensionError>() {
            @Override
            public void error(ExtensionError extensionError) {
                if (extensionError != null) {
                    promise.resolve(true);
                } else {
                    handleError(promise, extensionError);
                }
            }
        });
    }

    @ReactMethod
    public void dispatchEventWithResponseCallback(final ReadableMap eventMap,
                                                  final Promise promise) {
        Event event = RCTACPCoreDataBridge.eventFromReadableMap(eventMap);
        if (event == null) {
            promise.reject(getName(), FAILED_TO_CONVERT_EVENT_MESSAGE, new Error(FAILED_TO_CONVERT_EVENT_MESSAGE));
            return;
        }

        AdobeCallback<Event> eventAdobeCallback = new AdobeCallback<Event>() {
            @Override
            public void call(Event event) {
                promise.resolve(RCTACPCoreDataBridge.readableMapFromEvent(event));
            }
        };

        ExtensionErrorCallback<ExtensionError> extensionErrorExtensionErrorCallback = new ExtensionErrorCallback<ExtensionError>() {
            @Override
            public void error(ExtensionError extensionError) {
                handleError(promise, extensionError);
            }
        };

        MobileCore.dispatchEventWithResponseCallback(event, eventAdobeCallback, extensionErrorExtensionErrorCallback);
    }

    @ReactMethod
    public void dispatchResponseEvent(final ReadableMap responseEvent, final ReadableMap requestEvent,
                                      final Promise promise) {
        MobileCore.dispatchResponseEvent(RCTACPCoreDataBridge.eventFromReadableMap(responseEvent), RCTACPCoreDataBridge.eventFromReadableMap(requestEvent), new ExtensionErrorCallback<ExtensionError>() {
            @Override
            public void error(ExtensionError extensionError) {
                if (extensionError != null) {
                    promise.resolve(true);
                } else {
                    handleError(promise, extensionError);
                }
            }
        });
    }

    @ReactMethod
    public void trackAction(final String action, final ReadableMap contextData) {
        MobileCore.trackAction(action, RCTACPMapUtil.toStringMap(contextData));
    }

    @ReactMethod
    public void trackState(final String state, final ReadableMap contextData) {
        MobileCore.trackState(state, RCTACPMapUtil.toStringMap(contextData));
    }

    @ReactMethod
    public void setAdvertisingIdentifier(final String advertisingIdentifier) {
        MobileCore.setAdvertisingIdentifier(advertisingIdentifier);
    }

    @ReactMethod
    public void setPushIdentifier(final String pushIdentifier) {
        MobileCore.setPushIdentifier(pushIdentifier);
    }

    @ReactMethod
    public void lifecycleStart(final ReadableMap additionalContextData) {
        MobileCore.lifecycleStart(RCTACPMapUtil.toStringMap(additionalContextData));
    }

    @ReactMethod
    public void lifecyclePause() {
        MobileCore.lifecyclePause();
    }

    @ReactMethod
    public void collectPii(final ReadableMap data) {
        MobileCore.collectPii(RCTACPMapUtil.toStringMap(data));
    }

    @ReactMethod
    public static void setSmallIconResourceID(final int resourceID) {
      MobileCore.setSmallIconResourceID(resourceID);
    }

    @ReactMethod
    public static void setLargeIconResourceID(final int resourceID) {
      MobileCore.setLargeIconResourceID(resourceID);
    }

    // MobileCore.collectLaunchInfo is private
    @ReactMethod
    public void collectLaunchInfo() {
        MobileCore.log(LoggingMode.DEBUG, getName(), "collectLaunchInfo() cannot be invoked on Android");
    }

    @ReactMethod
    public void setAppGroup(final String appGroup) {
        MobileCore.log(LoggingMode.DEBUG, getName(), "setAppGroup() cannot be invoked on Android");
    }

    @ReactMethod
    public void downloadRules() {
        MobileCore.log(LoggingMode.DEBUG, getName(), "downloadRules() cannot be invoked on Android");
    }

    // Helper method/s
    private void handleError(Promise promise, ExtensionError error) {
        if (error == null || promise == null) {
            return;
        }

        promise.reject(String.valueOf(error.getErrorCode()), error.getErrorName(), new RuntimeException(error.getErrorName()));
    }

}
