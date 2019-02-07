/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2019 Adobe
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of Adobe and its suppliers, if any. The intellectual
 * and technical concepts contained herein are proprietary to Adobe
 * and its suppliers and are protected by all applicable intellectual
 * property laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe.
 **************************************************************************/

#import <React/RCTConvert.h>
#import "RCTACPCore.h"
#import "ACPCore.h"
#import "ACPExtensionEvent+ReactNative.h"
#import "RCTACPCoreDataBridge.h"

@implementation RCTACPCore

RCT_EXPORT_MODULE(ACPCore);

static NSString* const EXTENSION_NAME = @"RCTACPCore";
static NSString* const FAILED_TO_CONVERT_EVENT_MESSAGE = @"Failed to convert dictionary to Event";

// https://facebook.github.io/react-native/docs/native-modules-ios#threading
// https://stackoverflow.com/questions/50773748/difference-requiresmainqueuesetup-and-dispatch-get-main-queue
- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

/*
 * @brief Start the Core processing. This should be called after the initial set of extensions have been registered.
 *
 * This call will wait for any outstanding registrations to complete and then start event processing.
 * You can use the callback to kickoff additional operations immediately after any operations kicked off during registration.
 *
 * @param callback An optional method invoked after registrations are complete
 */
RCT_EXPORT_METHOD(start: (RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [ACPCore start:^{
        resolve(@(YES));
    }];
}

/**
 * @brief Load remote configuration specified by the given application ID
 *
 * Configure the SDK by downloading the remote configuration file hosted on Adobe servers
 * specified by the given application ID. The configuration file is cached once downloaded
 * and used in subsequent calls to this API. If the remote file is updated after the first
 * download, the updated file is downloaded and replaces the cached file.
 *
 * The \p appid is preserved, and on application restarts, the remote configuration file specified by \p appid
 * is downloaded and applied to the SDK.
 *
 * On failure to download the remote configuration file, the SDK is configured using the cached
 * file if it exists, or if no cache file exists then the existing configuration remains unchanged.
 *
 * Calls to this API will replace any existing SDK configuration except those set using
 * ACPCore::updateConfiguration: or ACPCore::setPrivacyStatus:. Configuration updates
 * made using ACPCore::updateConfiguration:
 * and ACPCore::setPrivacyStatus: are always applied on top of configuration changes made using this API.
 *
 * @param appid a unique identifier assigned to the app instance by the Adobe Mobile Services. It is automatically
 * added to the ADBMobile JSON file when downloaded from the Adobe Mobile Services UI and can be
 * found in Manage App Settings. A value of `nil` has no effect.
 */
RCT_EXPORT_METHOD(configureWithAppId:(NSString* __nullable) appId) {
    [ACPCore configureWithAppId:appId];
}

/**
 * @brief Load configuration from local file
 *
 * Configure the SDK by reading a local file containing the JSON configuration.  On application relaunch,
 * the configuration from the file at \p filepath is not preserved and this method must be called again if desired.
 *
 * On failure to read the file or parse the JSON contents, the existing configuration remains unchanged.
 *
 * Calls to this API will replace any existing SDK configuration except those set using
 * ACPCore::updateConfiguration: or ACPCore::setPrivacyStatus:. Configuration updates
 * made using ACPCore::updateConfiguration:
 * and ACPCore::setPrivacyStatus: are always applied on top of configuration changes made using this API.
 *
 * @param filepath absolute path to a local configuration file. A value of `nil` has no effect.
 */
RCT_EXPORT_METHOD(configureWithFileInPath: (NSString* __nullable) filepath) {
    [ACPCore configureWithFileInPath:filepath];
}

/**
 * @brief Update specific configuration parameters
 *
 * Update the current SDK configuration with specific key/value pairs. Keys not found in the current
 * configuration are added. Configuration updates are preserved and applied over existing or new
 * configurations set by calling ACPCore::configureWithAppId: or ACPCore::configureWithFileInPath:,
 * even across application restarts.
 *
 * Using `nil` values is allowed and effectively removes the configuration parameter from the current configuration.
 *
 * @param config configuration key/value pairs to be updated or added. A value of `nil` has no effect.
 */
RCT_EXPORT_METHOD(updateConfiguration: (NSDictionary* __nullable) config) {
    [ACPCore updateConfiguration:config];
}

/**
 * @brief Set the logging level of the SDK
 *
 * @param logLevel ACPCore::ACPMobileLogLevel to be used by the SDK
 * @see ACPMobileLogLevel
 */
RCT_EXPORT_METHOD(setLogLevel: (NSString *) logLevelString) {
    [ACPCore setLogLevel:[RCTACPCoreDataBridge logLevelFromString:logLevelString]];
}

/**
 * @brief Get the current Adobe Mobile Privacy Status
 *
 * Gets the currently configured \ref ACPMobilePrivacyStatus and passes it as a parameter to the given void function.
 *
 * @param callback method invoked with the configured privacy status as a parameter
 * @see ACPMobilePrivacyStatus
 */
RCT_EXPORT_METHOD(getPrivacyStatus: (RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [ACPCore getPrivacyStatus:^(ACPMobilePrivacyStatus status) {
        resolve([RCTACPCoreDataBridge stringFromPrivacyStatus:status]);
    }];
}

/**
 * @brief Set the Adobe Mobile Privacy status
 *
 * Sets the \ref ACPMobilePrivacyStatus for this SDK. The set privacy status is preserved and applied over any new
 * configuration changes from calls to ACPCore::configureWithAppId: or ACPCore::configureWithFileInPath:,
 * even across application restarts.
 *
 * @param status ACPCore::ACPMobilePrivacyStatus to be set to the SDK
 * @see ACPMobilePrivacyStatus
 */
RCT_EXPORT_METHOD(setPrivacyStatus: (NSString *) statusString) {
    [ACPCore setPrivacyStatus:[RCTACPCoreDataBridge privacyStatusFromString:statusString]];
}

/**
 * @brief Calls the provided callback with a JSON string containing all of the user's identities known by the SDK
 * @param callback a void-returning method that has an NSString param containing a JSON string
 */
RCT_EXPORT_METHOD(getSdkIdentities: (RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [ACPCore getSdkIdentities:^(NSString * _Nullable content) {
        resolve(content);
    }];
}

///**
// * @brief set the app group used to sharing user defaults and files among containing app and extension apps
// * @note This *must* be called in AppDidFinishLaunching and before any other interactions with the Adobe Mobile library have happened.
// * Only the first call to this function will have any effect.
// */
RCT_EXPORT_METHOD(setAppGroup: (nullable NSString*) appGroup) {
    [ACPCore setAppGroup:appGroup];
}

#pragma mark - Generic methods

/**
 *    @brief Submits a generic PII collection request event with type `generic.pii`.
 *    @param data a dictionary containing PII data
 */
RCT_EXPORT_METHOD(collectPii: (nonnull NSDictionary*) data) {
    [ACPCore collectPii:[RCTACPCoreDataBridge sanitizeDictionaryToContainClass:[NSString class] WithDictionary:data]];
}

/**
 * @brief Submits a generic event to pause lifecycle collection with event type `generic.lifecycle`.
 *
 * When using the Adobe Lifecycle extension, the following applies:
 *   - Pauses the current lifecycle session. Calling pause on an already paused session updates the paused timestamp,
 *     having the effect of resetting the session timeout timer. If no lifecycle session is running, then calling
 *     this method does nothing.
 *   - A paused session is resumed if ADBMobileMarketing::lifecycleStart: is called before the session timeout. After
 *     the session timeout, a paused session is closed and calling ADBMobileMarketing::lifecycleStart: will create
 *     a new session. The session timeout is defined by the `lifecycle.sessionTimeout` configuration parameter.
 *   - If not defined, the default session timeout is five minutes.
 */
RCT_EXPORT_METHOD(lifecyclePause) {
    [ACPCore lifecyclePause];
}

/**
 * @brief Submits a generic event to start/resume lifecycle collection with event type `generic.lifecycle`.
 *
 * When using the Adobe Lifecycle extension, the following applies:
 *   - Start a new lifecycle session or resume a previously paused lifecycle session. If a previously paused session
 *     timed out, then a new session is created. If a current session is running, then calling this method does nothing.
 *   - Additional context data may be passed when calling this method. Lifecycle data and any additional data are
 *     sent as context data parameters to Analytics, to Target as mbox parameters, and for Audience Manager they are
 *     sent as customer variables. Any additional data is also used by the Rules Engine when processing rules.
 *
 * @param additionalContextData optional additional context for this session.
 */
RCT_EXPORT_METHOD(lifecycleStart: (nullable NSDictionary*) additionalContextData) {
    [ACPCore lifecycleStart:[RCTACPCoreDataBridge sanitizeDictionaryToContainClass:[NSString class] WithDictionary:additionalContextData]];
}

/**
 * @brief Submits a generic event containing the provided IDFA with event type `generic.identity`.
 *
 * When using the Adobe Identity extension, the following applies:
 *   - If the IDFA was set in the SDK, the IDFA will be sent in lifecycle. It can also be accessed in Signals (Postbacks).
 *   - This ID is preserved between app upgrades, is saved and restored during the standard application backup process,
 *     and is removed at uninstall.
 *   - If the Mobile SDK is configured with `identity.adidEnabled` set to `false`, then the advertising identifier
 *     is not set or stored.
 *
 * @param adId the advertising idenifier string.
 */
RCT_EXPORT_METHOD(setAdvertisingIdentifier: (nullable NSString*) adId) {
    [ACPCore setAdvertisingIdentifier:adId];
}

/**
 * @brief Submits a generic event containing the provided push token with event type `generic.identity`.
 *
 * When using the Adobe Identity extension, the following applies:
 *   - If the current SDK privacy status is \ref ACPMobilePrivacyStatusOptOut, then the push identifier is not set.
 *
 * @param deviceToken the device token for push notifications
 * @see ACPMobilePrivacyStatus
 */
RCT_EXPORT_METHOD(setPushIdentifier: (nullable NSString*) deviceToken) {
    [ACPCore setPushIdentifier:[RCTConvert NSData:deviceToken]];
}

/**
 *  @brief This method sends a generic Analytics action tracking hit with context data.
 *
 *  Actions represent events that occur in your application that you want to measure; the corresponding metrics will
 *  be incremented each time the event occurs. For example, you may want to track when an user click on the login
 *  button or a certain article was viewed.
 *
 *  @note when using the Adobe Analytics extension, calling this API will not increment page views
 *
 *  @param action NSString containing the name of the action to track
 *  @param data NSDictionary<NSString, NSString> containing context data to attach on this hit
 */
RCT_EXPORT_METHOD(trackAction: (nullable NSString*) action data: (nullable NSDictionary*) data) {
    [ACPCore trackAction:action data:data];
}

/**
 *  @brief This method sends a generic Analytics state tracking hit with context data.
 *
 *  States represent different screens or views of you application. When the user navigates between application pages,
 *  a new track call should be sent with current state name. Tracking state name is typically called from an
 *  UIViewController in the viewDidLoad method.
 *
 *  @note when using the Adobe Analytics extension, calling this API will increment page views
 *
 *  @param state NSString containing the name of the state to track
 *  @param data NSDictionary<NSString, NSString> containing context data to attach on this hit
 */
RCT_EXPORT_METHOD(trackState: (nullable NSString*) state data: (nullable NSDictionary*) data) {
    [ACPCore trackState:state data:data];
}

/**
 * @brief Called by the extension public API to dispatch an event for other extensions or the internal SDK to consume.
 * Any events dispatched by this call will not be processed until after `start` has been called.
 *
 * @param event Required parameter with {@link Event} instance to be dispatched. Should not be nil
 * @param error Optional parameter where an NSError* will be returned if valid and NO was returned
 * @return YES if the the event dispatching operation succeeded
 */
RCT_EXPORT_METHOD(dispatchEvent: (nonnull NSDictionary*) eventDict resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    ACPExtensionEvent *event = [ACPExtensionEvent eventFromDictionary:eventDict];
    if (!event) {
        reject(EXTENSION_NAME, FAILED_TO_CONVERT_EVENT_MESSAGE, nil);
        return;
    }

    NSError *error = nil;
    [ACPCore dispatchEvent:event error:&error];
    if (!error) {
        resolve(@(YES));
    } else {
        [self handleError:error rejecter:reject];
    }
}

/**
 * @brief You should use this method when the {@code Event} being passed is a request and you expect an event in response.
 * Any events dispatched by this call will not be processed until after `start` has been called.
 *
 * @param requestEvent Required parameter with {@link Event} instance to be dispatched. Should not be nil
 * @param responseCallback Required parameter with callback called when response event is available. Should not be nil
 * @param error Optional parameter where an NSError* will be returned if valid and NO was returned
 * @return YES if the the event dispatching operation succeeded
 */
RCT_EXPORT_METHOD(dispatchEventWithResponseCallback: (nonnull NSDictionary*) requestEventDict resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    ACPExtensionEvent *requestEvent = [ACPExtensionEvent eventFromDictionary:requestEventDict];
    if (!requestEvent) {
        reject(EXTENSION_NAME, FAILED_TO_CONVERT_EVENT_MESSAGE, nil);
        return;
    }

    NSError *error = nil;
    [ACPCore dispatchEventWithResponseCallback:requestEvent responseCallback:^(ACPExtensionEvent * _Nonnull responseEvent) {
        resolve([ACPExtensionEvent dictionaryFromEvent:responseEvent]);
    } error:&error];

    if (!error) {
        resolve(@(YES));
    } else {
        [self handleError:error rejecter:reject];
    }
}

/**
 * @brief Dispatches a response event for a paired event that was sent to {@code dispatchEventWithResponseCallback}
 * or received by an extension listener {@code hear} method.
 *
 * @param responseEvent Required parameter with {@link Event} instance to be dispatched. Should not be nil
 * @param requestEvent Required parameter with {@link Event} that {@code responseEvent} is responding to. Should not be nil
 * @param error Optional parameter where an NSError* will be returned if valid and NO was returned
 * @return YES if the the event dispatching operation succeeded
 */
RCT_EXPORT_METHOD(dispatchResponseEvent: (nonnull NSDictionary*) responseEventDict
                  requestEvent: (nonnull NSDictionary*) requestEventDict
                   resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    ACPExtensionEvent *responseEvent = [ACPExtensionEvent eventFromDictionary:responseEventDict];
    ACPExtensionEvent *requestEvent = [ACPExtensionEvent eventFromDictionary:requestEventDict];
    NSError *error = nil;

    if (responseEvent && requestEvent) {
        [ACPCore dispatchResponseEvent:responseEvent requestEvent:requestEvent error:&error];

        if (error) {
            [self handleError:error rejecter:reject];
        }

    } else {
        reject(EXTENSION_NAME, FAILED_TO_CONVERT_EVENT_MESSAGE, nil);
    }
}

/**
 * @brief Provide user info to the SDK from various launch points in your application.
 *
 * This method should be called to support the following use cases:
 *
 *  1. Tracking Deep Link click-throughs
 *     - call from application:didFinishLaunchingWithOptions:
 *     - extract userInfo from UIApplicationLaunchOptionsURLKey
 *
 *  2. Tracking Push Message click-throughs
 *     - call from application:didReceiveRemoteNotification:fetchCompletionHandler:
 *
 * @param userInfo Dictionary of data relevant to the expected use case
 */
RCT_EXPORT_METHOD(collectLaunchInfo: (nonnull NSDictionary*) userInfo) {
    [ACPCore collectLaunchInfo:userInfo];
}

#pragma mark - Rules Engine

/**
 * @brief RulesEngine API to download and refresh rules from the server.
 *
 * Forces Rules Engine to send a network request to the rules url in Configuration,
 * to refresh rules content set in the SDK.
 */
RCT_EXPORT_METHOD(downloadRules) {
    [ACPCore downloadRules];
}

#pragma mark - Helper methods

- (void) handleError:(NSError *) error rejecter:(RCTPromiseRejectBlock) reject {
    if (!error || !reject) {
        return;
    }

    NSDictionary *userInfo = [error userInfo];
    NSString *errorString = [[userInfo objectForKey:NSUnderlyingErrorKey] localizedDescription];

    reject([NSString stringWithFormat: @"%lu", (long)error.code],
           errorString,
           error);
}

@end
