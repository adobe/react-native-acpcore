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

const OPT_IN = "ACP_PRIVACY_STATUS_OPT_IN";
const OPT_OUT = "ACP_PRIVACY_STATUS_OPT_OUT";
const UNKNOWN = "ACP_PRIVACY_STATUS_UNKNOWN";

class ACPMobilePrivacyStatus {

  static get OPT_IN() {
    return OPT_IN;
  }

  static get OPT_OUT() {
    return OPT_OUT;
  }

  static get UNKNOWN() {
    return UNKNOWN;
  }

}

module.exports = ACPMobilePrivacyStatus;
