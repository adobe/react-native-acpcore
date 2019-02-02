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

import { NativeModules } from 'react-native';

const { RCTACPCore } = NativeModules;

// Define our enums
export type ACPMobilePrivacyStatus =
  | 'ACP_PRIVACY_STATUS_OPT_IN'
  | 'ACP_PRIVACY_STATUS_OPT_OUT'
  | 'ACP_PRIVACY_STATUS_UNKNOWN';
const ACPPrivacyStatusValues: {|
  optIn: ACPMobilePrivacyStatus,
  optOut: ACPMobilePrivacyStatus,
  unknown: ACPMobilePrivacyStatus,
|} = {
  optIn: RCTACPCore.ACP_PRIVACY_STATUS_OPT_IN,
  optOut: RCTACPCore.ACP_PRIVACY_STATUS_OPT_OUT,
  unknown: RCTACPCore.ACP_PRIVACY_STATUS_UNKNOWN,
};

export type ACPMobileLogLevel =
  | 'ACP_LOG_LEVEL_ERROR'
  | 'ACP_LOG_LEVEL_WARNING'
  | 'ACP_LOG_LEVEL_DEBUG'
  | 'ACP_LOG_LEVEL_VERBOSE';
const ACPLogLevelValues: {|
  error: ACPMobileLogLevel,
  warning: ACPMobileLogLevel,
  debug: ACPMobileLogLevel,
  verbose: ACPMobileLogLevel,
|} = {
  error: RCTACPCore.ACP_LOG_LEVEL_ERROR,
  warning: RCTACPCore.ACP_LOG_LEVEL_WARNING,
  debug: RCTACPCore.ACP_LOG_LEVEL_DEBUG,
  verbose: RCTACPCore.ACP_LOG_LEVEL_VERBOSE,
};

const EXTENSION_VERSION = "1.0.0";

export class ACPCore {

  /**
   * Returns the version of the ACPCore extension
   * @param  {string} Promise a promise that resolves with the extension verison
   */
  static extensionVersion(): Promise<string> {
    return Promise.resolve(EXTENSION_VERSION);
  }

/**
 * Start the Core processing. This should be called after the initial set of extensions have been registered.
 * This call will wait for any outstanding registrations to complete and then start event processing.
 * You can use the callback to kickoff additional operations immediately after any operations kicked off during registration.
 * @param  {boolean} Promise a promise that resolves when core has started
 */
  static start(): Promise<boolean> {
    return RCTACPCore.start();
  }

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
 * @param  {String} appId a unique identifier assigned to the app instance by the Adobe Mobile Services. It is automatically
 * added to the ADBMobile JSON file when downloaded from the Adobe Mobile Services UI and can be
 * found in Manage App Settings. A value of `nil` has no effect.
 */
  static configureWithAppId(appId: String) {
    RCTACPCore.configureWithAppId(appId);
  }

  /**
   * Load configuration from local file
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
   * @param  {String} filepath absolute path to a local configuration file. A value of `nil` has no effect.
   */
  static configureWithFileInPath(filepath: String) {
    RCTACPCore.configureWithFileInPath(filepath);
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
 * @param  {{ string: any }} configMap configuration key/value pairs to be updated or added. A value of `nil` has no effect.
 */
  static updateConfiguration(configMap: { string: any }) {
    RCTACPCore.updateConfiguration(configMap);
  }

  static setLogLevel(mode: ACPMobileLogLevel) {
    RCTACPCore.setLogLevel(mode);
  }

  static setPrivacyStatus(privacyStatus: ACPMobilePrivacyStatus) {
    RCTACPCore.setPrivacyStatus(privacyStatus);
  }

  static getPrivacyStatus(): Promise<ACPMobilePrivacyStatus> {
    return RCTACPCore.getPrivacyStatus();
  }

  static getSdkIdentities(): Promise<?string> {
    return RCTACPCore.getSdkIdentities();
  }

  // dispatchEvent TODO

  // dispatchEventWithResponseCallback TODO

  // dispatchResponseEvent TODO

  static trackAction(action: String, contextData:  { string: string }) {
    RCTACPCore.trackAction(action, contextData);
  }

  static trackState(state: String, contextData:  { string: string }) {
    RCTACPCore.trackState(state, contextData);
  }

  static setAdvertisingIdentifier(advertisingIdentifier: String) {
    RCTACPCore.setAdvertisingIdentifier(advertisingIdentifier);
  }

  static setPushIdentifier(pushIdentifier: String) {
    RCTACPCore.setPushIdentifier(pushIdentifier);
  }

  static lifecycleStart(additionalContextData: { string: string }) {
    RCTACPCore.lifecycleStart(additionalContextData);
  }

  static lifecyclePause() {
    RCTACPCore.lifecyclePause();
  }

  static collectPii(data: { string: string }) {
    RCTACPCore.collectPii(data);
  }

  static collectLaunchInfo() {
    RCTACPCore.collectLaunchInfo();
  }

  static setAppGroup(appGroup: String) {
    RCTACPCore.setAppGroup(appGroup);
  }

  static downloadRules() {
    RCTACPCore.downloadRules();
  }

}
