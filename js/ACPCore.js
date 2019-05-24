/*
Copyright 2019 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.

@flow
@format
*/

'use strict';

const RCTACPCore = require('react-native').NativeModules.ACPCore;

import type {ACPExtensionEvent} from './models/ACPMobileLogLevel';

module.exports = {

  /**
   * Returns the version of the ACPCore extension
   * @param  {string} Promise a promise that resolves with the extension verison
   */
  extensionVersion(): Promise<string> {
    return Promise.resolve(RCTACPCore.extensionVersion());
  },

  /**
   * Start the Core processing.
   * This should be called after the initial set of extensions have been registered.
   * This call will wait for any outstanding registrations to complete and then start event processing.
   * You can use the callback to kickoff additional operations immediately after any operations kicked off during registration.
   * @param  {boolean} Promise a promise that resolves when core has started
   */
  start(): Promise<boolean> {
    return RCTACPCore.start();
  },

  /**
   * Load remote configuration specified by the given application ID
   *
   * Configure the SDK by downloading the remote configuration file hosted on Adobe servers
   * specified by the given application ID. The configuration file is cached once downloaded
   * and used in subsequent calls to this API. If the remote file is updated after the first
   * download, the updated file is downloaded and replaces the cached file.
   *
   * The appid is preserved, and on application restarts, the remote configuration file specified by \p appid
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
   * @param  {String?} appId a unique identifier assigned to the app instance by the Adobe Mobile Services. It is automatically
   * added to the ADBMobile JSON file when downloaded from the Adobe Mobile Services UI and can be
   * found in Manage App Settings. A value of `nil` has no effect.
   */
  configureWithAppId(appId?: String) {
    RCTACPCore.configureWithAppId(appId);
  },

  /**
   * Update specific configuration parameters
   *
   * Update the current SDK configuration with specific key/value pairs. Keys not found in the current
   * configuration are added. Configuration updates are preserved and applied over existing or new
   * configurations set by calling ACPCore::configureWithAppId: or ACPCore::configureWithFileInPath:,
   * even across application restarts.
   *
   * Using `nil` values is allowed and effectively removes the configuration parameter from the current configuration.
   *
   * @param  {{ string: any }?} configMap configuration key/value pairs to be updated or added. A value of `nil` has no effect.
   */
  updateConfiguration(configMap?: { string: any }) {
    RCTACPCore.updateConfiguration(configMap);
  },

  /**
   * Set the logging level of the SDK
   *
   * @param {ACPMobileLogLevel} mode ACPMobileLogLevel to be used by the SDK
   */
  setLogLevel(mode: string) {
    RCTACPCore.setLogLevel(mode);
  },

  /**
   * Get the {@link ACPMobileLogLevel} level for the Mobile SDK
   * @return the set {@code ACPMobileLogLevel}
   */
  getLogLevel(): Promise<string> {
    return RCTACPCore.getLogLevel();
  },

  /**
	 * Sends a log message of the given {@code ACPMobileLogLevel}. If the specified {@code mode} is
	 * more verbose than the current {@link ACPMobileLogLevel} set from {@link #setLogLevel(ACPMobileLogLevel)}
	 * then the message is not printed.
	 *
	 * @param mode the {@link ACPMobileLogLevel} used to print the message
	 * @param tag used to identify the source of the log message
	 * @param message the message to log
	 */
  log(logLevel: string, tag: string, message: string) {
    RCTACPCore.log(logLevel, tag, message);
  },

  /**
   * Set the Adobe Mobile Privacy status
   *
   * @param {ACPMobilePrivacyStatus} privacyStatus ACPMobilePrivacyStatus to be set to the SDK
   */
  setPrivacyStatus(privacyStatus: string) {
    RCTACPCore.setPrivacyStatus(privacyStatus);
  },

  /**
   * Get the current Adobe Mobile Privacy Status
   *
   * @return {ACPMobilePrivacyStatus} the current privacy status
   */
  getPrivacyStatus(): Promise<string> {
    return RCTACPCore.getPrivacyStatus();
  },

  /**
   * Calls the provided callback with a JSON string containing all of the user's identities known by the SDK
   *
   * @return {string?} known identifier as a JSON string
   */
  getSdkIdentities(): Promise<?string> {
    return RCTACPCore.getSdkIdentities();
  },

  /**
   * Called by the extension public API to dispatch an event for other extensions or the internal SDK to consume.
   * Any events dispatched by this call will not be processed until after `start` has been called.
   *
   * @param event Required parameter with {@link Event} instance to be dispatched. Should not be nil
   * @return true if the the event dispatching operation succeeded, otherwise the promise will return an error
   */
  dispatchEvent(event: ACPExtensionEvent): Promise<boolean> {
    return RCTACPCore.dispatchEvent(event);
  },

  /**
	 * This method will be used when the provided {@code ACPExtensionEvent} is used as a trigger and a response event
	 * is expected in return. The returned event needs to be sent using
	 * {@link #dispatchResponseEvent(Event, Event, ExtensionErrorCallback)}.
	 * <p>
	 *
	 * @param event            required parameter, {@link ACPExtensionEvent} instance to be dispatched, used as a trigger
	 * @param responseCallback required parameters, {@link Promise} to be called with the response event received
	 *
	 * @see ACPCore#dispatchResponseEvent(Event, Event, ExtensionErrorCallback)
   */
  dispatchEventWithResponseCallback(event: ACPExtensionEvent): Promise<ACPExtensionEvent> {
    return RCTACPCore.dispatchEventWithResponseCallback(event);
  },

  /**
	 * Dispatches a response event for a paired event that was sent to {@code dispatchEventWithResponseCallback}
	 * and received by an extension listener {@code hear} method.
	 *
	 * @param responseEvent required parameter, {@link ACPExtensionEvent} instance to be dispatched as a response for the
	 *                      event sent using {@link ACPCore#dispatchEventWithResponseCallback(ACPExtensionEvent)}
	 * @param requestEvent  required parameter, the event sent using
	 * 						{@link ACPCore#dispatchEventWithResponseCallback(ACPExtensionEvent)}
	 * @return {@code boolean} indicating if the the event dispatching operation succeeded
	 *
	 * @see ACPCore#dispatchEventWithResponseCallback(ACPExtensionEvent)
   */
  dispatchResponseEvent(responseEvent: ACPExtensionEvent, requestEvent: ACPExtensionEvent): Promise<boolean> {
    return RCTACPCore.dispatchResponseEvent(responseEvent, requestEvent);
  },

  /**
   * This method sends a generic Analytics action tracking hit with context data.
   *
   *  Actions represent events that occur in your application that you want to measure; the corresponding metrics will
   *  be incremented each time the event occurs. For example, you may want to track when an user click on the login
   *  button or a certain article was viewed.
   *
   *  note: when using the Adobe Analytics extension, calling this API will increment page views
   * @param  {String?} state containing the name of the state to track
   * @param  {{ string: string }?} contextData containing context data to attach on this hit
   */
  trackAction(action?: String, contextData?: { string: string }) {
    RCTACPCore.trackAction(action, contextData);
  },

  /**
   * This method sends a generic Analytics state tracking hit with context data.
   *
   *  States represent different screens or views of you application. When the user navigates between application pages,
   *  a new track call should be sent with current state name. Tracking state name is typically called from a
   *  Component in the componentDidMount function.
   *
   *  note: when using the Adobe Analytics extension, calling this API will increment page views
   * @param  {String?} state containing the name of the state to track
   * @param  {{ string: string }?} contextData containing context data to attach on this hit
   */
  trackState(state?: String, contextData?: { string: string }) {
    RCTACPCore.trackState(state, contextData);
  },

  /**
   * Submits a generic event containing the provided IDFA with event type `generic.identity`.
   *
   * When using the Adobe Identity extension, the following applies:
   *   - If the IDFA was set in the SDK, the IDFA will be sent in lifecycle. It can also be accessed in Signals (Postbacks).
   *   - This ID is preserved between app upgrades, is saved and restored during the standard application backup process,
   *     and is removed at uninstall.
   *   - If the Mobile SDK is configured with `identity.adidEnabled` set to `false`, then the advertising identifier
   *     is not set or stored.
   *
   * @param {String?} advertisingIdentifier the advertising idenifier string.
   */
    setAdvertisingIdentifier(advertisingIdentifier?: String) {
    RCTACPCore.setAdvertisingIdentifier(advertisingIdentifier);
  },

  /**
   * Submits a generic event containing the provided push token with event type `generic.identity`.
   *
   * When using the Adobe Identity extension, the following applies:
   *   - If the current SDK privacy status is \ref ACPMobilePrivacyStatusOptOut, then the push identifier is not set.
   *
   * @param {String?} pushIdentifier the device token for push notifications
   */
  setPushIdentifier(pushIdentifier?: String) {
    RCTACPCore.setPushIdentifier(pushIdentifier);
  },

  /**
   * Submits a generic event to start/resume lifecycle collection with event type `generic.lifecycle`.
   *
   * When using the Adobe Lifecycle extension, the following applies:
   *   - Start a new lifecycle session or resume a previously paused lifecycle session. If a previously paused session
   *     timed out, then a new session is created. If a current session is running, then calling this method does nothing.
   *   - Additional context data may be passed when calling this method. Lifecycle data and any additional data are
   *     sent as context data parameters to Analytics, to Target as mbox parameters, and for Audience Manager they are
   *     sent as customer variables. Any additional data is also used by the Rules Engine when processing rules.
   *
   * @param  {{ string: string }?} additionalContextData optional additional context for this session.
   */
  lifecycleStart(additionalContextData?: { string: string }) {
    RCTACPCore.lifecycleStart(additionalContextData);
  },

  /**
  * Submits a generic event to pause lifecycle collection with event type `generic.lifecycle`.
  *
  * When using the Adobe Lifecycle extension, the following applies:
  *   - Pauses the current lifecycle session. Calling pause on an already paused session updates the paused timestamp,
  *     having the effect of resetting the session timeout timer. If no lifecycle session is running, then calling
  *     this method does nothing.
  *   - A paused session is resumed if ACPCore::lifecycleStart: is called before the session timeout. After
  *     the session timeout, a paused session is closed and calling ACPCore::lifecycleStart: will create
  *     a new session. The session timeout is defined by the `lifecycle.sessionTimeout` configuration parameter.
  *   - If not defined, the default session timeout is five minutes.
  */
  lifecyclePause() {
    RCTACPCore.lifecyclePause();
  },

  /**
   * Collect PII data. Although using this call enables collection of PII data, the SDK does not
   * automatically send the data to any Adobe endpoint.
   * @param  {{ string: string }} data the dictionary containing the PII data to be collected
   */
  collectPii(data: { string: string }) {
    RCTACPCore.collectPii(data);
  },

  /**
	 * Sets the resource Id for small icon.
	 * @param resourceID the resource Id of the icon
	 */
  setSmallIconResourceID(resourceID: number) {
    RCTACPCore.setSmallIconResourceID(resourceID);
  },

  /**
	 * Sets the resource Id for large icon.
	 * @param resourceID the resource Id of the icon
	 */
  setLargeIconResourceID(resourceID: number) {
    RCTACPCore.setLargeIconResourceID(resourceID);
  },

  /**
   * iOS Only
   * @brief set the app group used to sharing user defaults and files among containing app and extension apps
   * @note This *must* be called in AppDidFinishLaunching and before any other interactions with the Adobe Mobile library have happened.
   * Only the first call to this function will have any effect.
   * @platform ios
   */
  collectLaunchInfo() {
    RCTACPCore.collectLaunchInfo();
  },

  /**
   * iOS Only
   * @brief set the app group used to sharing user defaults and files among containing app and extension apps
   * @note This *must* be called in AppDidFinishLaunching and before any other interactions with the Adobe Mobile library have happened.
   * Only the first call to this function will have any effect.
   * @platform ios
   */
  setAppGroup(appGroup?: String) {
    RCTACPCore.setAppGroup(appGroup);
  },

  /**
  * iOS only
  * RulesEngine API to download and refresh rules from the server.
  *
  * Forces Rules Engine to send a network request to the rules url in Configuration,
  * to refresh rules content set in the SDK.
  * @platform ios
   */
  downloadRules() {
    RCTACPCore.downloadRules();
  },

};
