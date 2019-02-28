/* ***********************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2019 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
package com.adobe.marketing.mobile.reactnative;

import com.adobe.marketing.mobile.AdobeCallback;
import com.adobe.marketing.mobile.Event;
import com.adobe.marketing.mobile.ExtensionError;
import com.adobe.marketing.mobile.ExtensionErrorCallback;
import com.adobe.marketing.mobile.LoggingMode;
import com.adobe.marketing.mobile.MobileCore;
import com.adobe.marketing.mobile.MobilePrivacyStatus;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

public class RCTACPCoreModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private static String FAILED_TO_CONVERT_EVENT_MESSAGE = "Failed to convert map to Event";

    public RCTACPCoreModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
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

    /**
     * Start the Core processing. This should be called after the initial set of extensions have been registered.
     * <p>
     * This call will wait for any outstanding registrations to complete and then start event processing.
     * You can use the callback to kickoff additional operations immediately after any operations kicked off during registration.
     *
     * @param promise An optional {@link Promise} invoked after registrations are completed
     */
    @ReactMethod
    public void start(final Promise promise) {
        MobileCore.start(new AdobeCallback() {
            @Override
            public void call(Object obj) {
                promise.resolve(true);
            }
        });
    }

    /**
     * Update specific configuration parameters.
     * <p>
     * Update the current SDK configuration with specific key/value pairs. Keys not found in the current
     * configuration are added. Configuration updates are preserved and applied over existing or new
     * configurations set by calling {@link #configureWithAppId(String)} or {@link #configureWithFileInPath(String)},
     * even across application restarts.
     * <p>
     * Using {@code null} values is allowed and effectively removes the configuration parameter from the current configuration.
     *
     * @param appId configuration key/value pairs to be updated or added. A value of {@code null} has no effect.
     */
    @ReactMethod
    public void configureWithAppId(final String appId) {
        MobileCore.configureWithAppID(appId);
    }


    /**
     * Load configuration from local file.
     * <p>
     * Configure the SDK by reading a local file containing the JSON configuration.  On application relaunch,
     * the configuration from the file at {@code filepath} is not preserved and this method must be called again if desired.
     * <p>
     * On failure to read the file or parse the JSON contents, the existing configuration remains unchanged.
     * <p>
     * Calls to this API will replace any existing SDK configuration except those set using
     * {@link #updateConfiguration(ReadableMap)} or {@link #setPrivacyStatus(String)}.
     * Configuration updates made using {@link #updateConfiguration(ReadableMap)} and {@link #setPrivacyStatus(String)}
     * are always applied on top of configuration changes made using this API.
     *
     * @param filepath absolute path to a local configuration file. A value of {@code null} has no effect.
     */
    @ReactMethod
    public void configureWithFileInPath(final String filepath) {
        MobileCore.configureWithFileInPath(filepath);
    }

    /**
     * Update specific configuration parameters.
     * <p>
     * Update the current SDK configuration with specific key/value pairs. Keys not found in the current
     * configuration are added. Configuration updates are preserved and applied over existing or new
     * configurations set by calling {@link #configureWithAppId(String)} or {@link #configureWithFileInPath(String)},
     * even across application restarts.
     * <p>
     * Using {@code null} values is allowed and effectively removes the configuration parameter from the current configuration.
     *
     * @param configMap configuration key/value pairs to be updated or added. A value of {@code null} has no effect.
     */
    @ReactMethod
    public void updateConfiguration(final ReadableMap configMap) {
        MobileCore.updateConfiguration(RCTACPMapUtil.toMap(configMap));
    }

    /**
     * Set the log level
     *
     * @param mode the logging mode
     */
    @ReactMethod
    public void setLogLevel(final String mode) {
        LoggingMode logMode = RCTACPCoreDataBridge.loggingModeFromString(mode);
        MobileCore.setLogLevel(logMode);
    }

    /**
     * Set the Adobe Mobile Privacy status.
     * <p>
     * Sets the {@link MobilePrivacyStatus} for this SDK. The set privacy status is preserved and applied over any new
     * configuration changes from calls to {@link #configureWithAppId(String)} or {@link #configureWithFileInPath(String)},
     * even across application restarts.
     *
     * @param privacyStatus {@link String} to be set to the SDK
     * @see MobilePrivacyStatus
     */
    @ReactMethod
    public void setPrivacyStatus(final String privacyStatus) {
        MobileCore.setPrivacyStatus(RCTACPCoreDataBridge.privacyStatusFromString(privacyStatus));
    }

    /**
     * Get the current Adobe Mobile Privacy Status.
     * <p>
     * Gets the currently configured {@link MobilePrivacyStatus} and passes it as a parameter to the given
     * {@link AdobeCallback#call(Object)} function.
     *
     * @param promise {@link Promise} instance which is invoked with the configured privacy status as a parameter
     * @see AdobeCallback
     * @see MobilePrivacyStatus
     */
    @ReactMethod
    public void getPrivacyStatus(final Promise promise) {
        MobileCore.getPrivacyStatus(new AdobeCallback<MobilePrivacyStatus>() {
            @Override
            public void call(MobilePrivacyStatus mobilePrivacyStatus) {
                promise.resolve(RCTACPCoreDataBridge.stringFromPrivacyStatus(mobilePrivacyStatus));
            }
        });
    }

    /**
     * Retrieve all identities stored by/known to the SDK in a JSON {@code String} format.
     *
     * @param promise {@link Promise} instance which is invoked with all the known identifier in JSON {@link String} format
     * @see AdobeCallback
     */
    @ReactMethod
    public void getSdkIdentities(final Promise promise) {
        MobileCore.getSdkIdentities(new AdobeCallback<String>() {
            @Override
            public void call(String value) {
                promise.resolve(value);
            }
        });
    }

    /**
     * Called by the extension public API to dispatch an event for other extensions or the internal SDK to consume.
     *
     * @param eventMap required parameter, {@link Event} instance to be dispatched, should not be null
     * @param promise  {@link Promise} which will be called as (success, error)
     */
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


    /**
     * This method will be used when the provided {@code Event} is used as a trigger and a response event
     * is expected in return. The returned event needs to be sent using
     * {@link #dispatchResponseEvent(ReadableMap, ReadableMap, Promise)}.
     * <p>
     * Passes an {@link ExtensionError} to {@code errorCallback} if {@code event} or
     * {@code responseCallback} are null.
     *
     * @param eventMap required parameter, {@link ReadableMap} instance to be dispatched, used as a trigger
     * @param promise  required parameters, {@link Promise} to be called with the response event received
     * @see MobileCore#dispatchResponseEvent(Event, Event, ExtensionErrorCallback)
     */
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

    /**
     * Dispatches a response event for a paired event that was sent to {@code dispatchEventWithResponseCallback}
     * and received by an extension listener {@code hear} method.
     * <p>
     * Passes an {@link ExtensionError} to {@code errorCallback} if {@code responseEvent} or {@code requestEvent} are null.
     * <p>
     * Note: The {@code responseEvent} will not be sent to any listeners, it is sent only to the response callback registered
     * using {@link MobileCore#dispatchEventWithResponseCallback(Event, AdobeCallback, ExtensionErrorCallback)}.
     *
     * @param responseEvent required parameter, {@link ReadableMap} instance to be dispatched as a response for the
     *                      event sent using {@link RCTACPCoreModule#dispatchEventWithResponseCallback(ReadableMap, Promise)}
     * @param requestEvent  required parameter, the event sent using
     *                      {@link RCTACPCoreModule#dispatchEventWithResponseCallback(ReadableMap, Promise)}
     * @param promise optional {@link Promise} which will be called if an error occurred during dispatching, otherwise resolved with "true".
     * @see MobileCore#dispatchEventWithResponseCallback(Event, AdobeCallback, ExtensionErrorCallback)
     */
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

    // ========================================================
    // Generic methods
    // ========================================================

    /**
     * This method dispatches an Analytics track {@code action} event
     * <p>
     * Actions represent events that occur in your application that you want to measure; the corresponding metrics will
     * be incremented each time the event occurs. For example, you may want to track when an user click on the login
     * button or a certain article was viewed.
     * <p>
     *
     * @param action      {@code String} containing the name of the action to track
     * @param contextData {@code ReadableMap} containing context data to attach on this hit
     */
    @ReactMethod
    public void trackAction(final String action, final ReadableMap contextData) {
        MobileCore.trackAction(action, RCTACPMapUtil.toStringMap(contextData));
    }

    /**
     * This method dispatches an Analytics track {@code state} event
     * <p>
     * States represent different screens or views of your application. When the user navigates between application pages,
     * a new track call should be sent with current state name. Tracking state name is typically called from an
     * Activity in the onResume method.
     * <p>
     *
     * @param state       {@code String} containing the name of the state to track
     * @param contextData contextData {@code ReadableMap} containing context data to attach on this hit
     */
    @ReactMethod
    public void trackState(final String state, final ReadableMap contextData) {
        MobileCore.trackState(state, RCTACPMapUtil.toStringMap(contextData));
    }

    /**
     * This method dispatches an event to notify the SDK of a new {@code advertisingIdentifier}
     *
     * @param advertisingIdentifier {@code String} representing Android advertising identifier
     */
    @ReactMethod
    public void setAdvertisingIdentifier(final String advertisingIdentifier) {
        MobileCore.setAdvertisingIdentifier(advertisingIdentifier);
    }

    /**
     * This method dispatches an event to notify the SDK of a new {@code pushIdentifier}
     *
     * @param pushIdentifier {@code String} representing the new push identifier
     */
    @ReactMethod
    public void setPushIdentifier(final String pushIdentifier) {
        MobileCore.setPushIdentifier(pushIdentifier);
    }

    /**
     * Start/resume lifecycle session.
     * <p>
     * Start a new lifecycle session or resume a previously paused lifecycle session. If a previously paused session
     * timed out, then a new session is created. If a current session is running, then calling this method does nothing.
     * <p>
     * Additional context data may be passed when calling this method. Lifecycle data and any additional data are
     * sent as context data parameters to Analytics, to Target as mbox parameters, and for Audience Manager they are
     * sent as customer variables. Any additional data is also used by the Rules Engine when processing rules.
     * <p>
     * This method should be called from the Activity onResume method.
     *
     * @param additionalContextData optional additional context for this session.
     */
    @ReactMethod
    public void lifecycleStart(final ReadableMap additionalContextData) {
        MobileCore.lifecycleStart(RCTACPMapUtil.toStringMap(additionalContextData));
    }

    /**
     * Pause/stop lifecycle session.
     * <p>
     * Pauses the current lifecycle session. Calling pause on an already paused session updates the paused timestamp,
     * having the effect of resetting the session timeout timer. If no lifecycle session is running, then calling
     * this method does nothing.
     * <p>
     * A paused session is resumed if {@link #lifecycleStart(ReadableMap)} is called before the session timeout. After
     * the session timeout, a paused session is closed and calling {@link #lifecycleStart(ReadableMap)} will create
     * a new session. The session timeout is defined by the {@code lifecycle.sessionTimeout} configuration parameter.
     * If not defined, the default session timeout is five minutes.
     * <p>
     * This method should be called from the Activity onPause method.
     */
    @ReactMethod
    public void lifecyclePause() {
        MobileCore.lifecyclePause();
    }

    /**
     * Collect PII data. Although using this call enables collection of PII data, the SDK does not
     * automatically send the data to any Adobe endpoint.
     *
     * @param data the map containing the PII data to be collected
     */
    @ReactMethod
    public void collectPii(final ReadableMap data) {
        MobileCore.collectPii(RCTACPMapUtil.toStringMap(data));
    }

    /**
     * Collects data from the current Activity / context to be used later by the SDK.
     * <p>
     * This method marshals the {@code activity} instance and extracts the intent data / extras. It should be called to support
     * the following use cases:
     * <ol>
     * <li>Tracking Deep Link click-through
     * <ul>
     * <li>Update AndroidManifest.xml to support intent-filter in the activity with the intended action and type of data.</li>
     * <li>Handle the intent in the activity.</li>
     * <li>Pass activity with deepLink intent to SDK in {@code collectLaunchInfo}.</li>
     * </ul>
     * </li>
     * <li>Tracking Push Message click-through
     * <ul>
     * <li>Push message data must be added to the Intent used to open target activity on click-through.</li>
     * <li>The data can be added in intent extras which is then collected by SDK when target activity is passed in {@code collectedLaunchInfo}.</li>
     * </ul>
     * </li>
     * <li>Tracking Local Notification click-through
     * <ul>
     * <li>Add manifest-declared broadcast receiver {@code <receiver android:name=".LocalNotificationHandler" />} in your app.</li>
     * <li>Pass notifications activity reference in {@code collectLaunchInfo}.</li>
     * </ul>
     * </li>
     * </ol>
     * <p>
     * Invoke this method from {@link Activity#onResume} callback in your activity.
     */
    // MobileCore.collectLaunchInfo is private
    @ReactMethod
    public void collectLaunchInfo() {
//        Activity act = getCurrentActivity();
//        if (act != null) {
//            MobileCore.collectLaunchInfo(act);
//        }
        Log.d(getName(), "collectLaunchInfo() cannot be invoked on Android");
    }

    @ReactMethod
    public void setAppGroup(final String appGroup) {
        Log.d(getName(), "setAppGroup() cannot be invoked on Android");
    }

    @ReactMethod
    public void downloadRules() {
        Log.d(getName(), "downloadRules() cannot be invoked on Android");
    }

    // Helper method/s
    private void handleError(Promise promise, ExtensionError error) {
        if (error == null || promise == null) {
            return;
        }

        promise.reject(String.valueOf(error.getErrorCode()), error.getErrorName(), new RuntimeException(error.getErrorName()));
    }

}
