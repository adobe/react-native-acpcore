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

const AUTHENTICATED = "ACP_VISITOR_AUTH_STATE_AUTHENTICATED";
const LOGGED_OUT = "ACP_VISITOR_AUTH_STATE_LOGGED_OUT";
const UNKNOWN = "ACP_VISITOR_AUTH_STATE_UNKNOWN";

class ACPMobileVisitorAuthenticationState {

  static get AUTHENTICATED() {
    return AUTHENTICATED;
  }

  static get LOGGED_OUT() {
    return LOGGED_OUT;
  }

  static get UNKNOWN() {
    return UNKNOWN;
  }

}

module.exports = ACPMobileVisitorAuthenticationState;
