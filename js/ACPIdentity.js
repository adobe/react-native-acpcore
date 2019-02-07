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

const RCTACPIdentity = require('react-native').NativeModules.ACPIdentity;

import type {ACPMobileVisitorAuthenticationState} from './models/ACPMobileVisitorAuthenticationState';
import type {ACPVisitorID} from './models/ACPVisitorID';

module.exports = {
  /**
   * Returns the version of the ACPIdentity extension
   * @param  {string} Promise [description]
   */
  extensionVersion(): Promise<string> {
    return Promise.resolve("1.0.0");
  },

  /**
   * Registers the ACPIdentity extension with ACPCore
   */
  registerExtension() {
    RCTACPIdentity.registerExtension();
  },

  syncIdentifiers(identifiers?: {string: string}) {
    RCTACPIdentity.syncIdentifiers(identifiers);
  },

  syncIdentifiersWithAuthState(identifiers?: {string: string}, authenticationState: ACPMobileVisitorAuthenticationState) {
    RCTACPIdentity.syncIdentifiersWithAuthState(identifiers, authenticationState);
  },

  syncIdentifier(identifierType: String, identifier: String, authenticationState: ACPMobileVisitorAuthenticationState) {
    RCTACPIdentity.syncIdentifier(identifierType, identifier, authenticationState);
  },

  appendVisitorInfoForURL(baseURL?: String): Promise<?string> {
    return RCTACPIdentity.appendToUrl(baseURL);
  },

  getIdentifiers(): Promise<Array<?ACPVisitorID>> {
    return RCTACPIdentity.getIdentifiers();
  },

  getExperienceCloudId(): Promise<?string> {
    return RCTACPIdentity.getExperienceCloudId();
  },

};
