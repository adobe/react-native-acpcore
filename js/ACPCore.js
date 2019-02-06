/** ***********************************************************************
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
*
* @flow
* @format
*/

'use strict';

const Core = require('react-native').NativeModules.ACPCore;

export type ACPMobilePrivacyStatus =
  | "ACP_PRIVACY_STATUS_OPT_IN"
  | "ACP_PRIVACY_STATUS_OPT_OUT"
  | "ACP_PRIVACY_STATUS_UNKNOWN";

export type ACPMobileLogLevel =
  | "ACP_LOG_LEVEL_ERROR"
  | "ACP_LOG_LEVEL_WARNING"
  | "ACP_LOG_LEVEL_DEBUG"
  | "ACP_LOG_LEVEL_VERBOSE";

module.exports = {

  extensionVersion(): Promise<string> {
    return Promise.resolve("1.0.0");
  },

};
