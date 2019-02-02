// @flow

/** ***********************************************************************
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
************************************************************************* */

import { NativeModules } from 'react-native';

const { RCTACPIdentity } = NativeModules;

// Define our enums
export type ACPMobileVisitorAuthenticationState =
  | "ACP_VISITOR_AUTH_STATE_AUTHENTICATED"
  | "ACP_VISITOR_AUTH_STATE_LOGGED_OUT"
  | "ACP_VISITOR_AUTH_STATE_UNKNOWN";
const ACPMobileVisitorAuthenticationValues: {|
  authenticated: ACPMobileVisitorAuthenticationState,
  loggedOut: ACPMobileVisitorAuthenticationState,
  unknown: ACPMobileVisitorAuthenticationState,
|} = {
  authenticated: RCTACPIdentity.ACP_VISITOR_AUTH_STATE_AUTHENTICATED,
  loggedOut: RCTACPIdentity.ACP_VISITOR_AUTH_STATE_LOGGED_OUT,
  unknown: RCTACPIdentity.ACP_VISITOR_AUTH_STATE_UNKNOWN,
};

export class ACPVisitorID {
  constructor(idOrigin: ?string, idType: string, identifier: ?string, authenticationState: ?ACPMobileVisitorAuthenticationState) {
    this.idOrigin = idOrigin;
    this.idType = idType;
    this.identifier = identifier;
    this.authenticationState = authenticationState;
  }

  get idOrigin(): ?string {
    return this.idOrigin;
  }

  get idType(): string {
    return this.idType;
  }

  get identifier(): ?string {
    return this.identifier;
  }

  get authenticationState(): ?ACPMobileVisitorAuthenticationState {
    return this.authenticationState;
  }
}

const EXTENSION_VERSION = '1.0.0';

export class ACPIdentity {
  static ACPMobileVisitorAuthenticationState = ACPMobileVisitorAuthenticationValues;

  /**
   * Returns the version of the ACPIdentity extension
   * @param  {string} Promise [description]
   */
  static extensionVersion(): Promise<string> {
    return Promise.resolve(EXTENSION_VERSION);
  }

  static registerExtension() {
    RCTACPIdentity.registerExtension();
  }

  static syncIdentifiers(identifiers: {string: string}) {
    RCTACPIdentity.syncIdentifiers(identifiers);
  }

  static syncIdentifiersWithAuthState(identifiers: {string: string}, authenticationState: ACPMobileVisitorAuthenticationState) {
    RCTACPIdentity.syncIdentifiers(identifiers, authenticationState);
  }

  static syncIdentifier(identifierType: String, identifier: String, authenticationState: ACPMobileVisitorAuthenticationState) {
    RCTACPIdentity.syncIdentifier(identifierType, identifier, authenticationState);
  }

  static appendVisitorInfoForURL(baseURL: String): Promise<?string> {
    return RCTACPIdentity.appendVisitorInfoForURL(baseURL);
  }

  static getIdentifiers(): Promise<Array<?ACPVisitorID>> {
    return RCTACPIdentity.getIdentifiers();
  }

  static getExperienceCloudId(): Promise<?string> {
    return RCTACPIdentity.getExperienceCloudId();
  }
}
