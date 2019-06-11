/*
Copyright 2019 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.

@format
*/

import { NativeModules } from 'react-native';
import ACPIdentity from '../js/ACPIdentity';
import ACPMobileVisitorAuthenticationState from '../js/models/ACPMobileVisitorAuthenticationState';

describe('ACPIdentity', () => {

  test('extensionVersion is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'extensionVersion');
    await ACPIdentity.extensionVersion();
    expect(spy).toHaveBeenCalled();
  });

  test('registerExtension is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'registerExtension');
    await ACPIdentity.registerExtension();
    expect(spy).toHaveBeenCalled();
  });

  test('syncIdentifiers is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'syncIdentifiers');
    let identifiers = {"testKey": "testValue"};
    await ACPIdentity.syncIdentifiers(identifiers);
    expect(spy).toHaveBeenCalledWith(identifiers);
  });

  test('syncIdentifiersWithAuthState is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'syncIdentifiersWithAuthState');
    let identifiers = {"testKey": "testValue"};
    let authState = ACPMobileVisitorAuthenticationState.LOGGED_OUT;
    await ACPIdentity.syncIdentifiersWithAuthState(identifiers, authState);
    expect(spy).toHaveBeenCalledWith(identifiers, authState);
  });

  test('syncIdentifier is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'syncIdentifier');
    let identifier = "testId"
    let identifierType = "testIdType"
    let authState = ACPMobileVisitorAuthenticationState.LOGGED_IN;
    await ACPIdentity.syncIdentifier(identifier, identifierType, authState);
    expect(spy).toHaveBeenCalledWith(identifier, identifierType, authState);
  });

  test('appendVisitorInfoForURL is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'appendVisitorInfoForURL');
    let url = "testurl.com";
    await ACPIdentity.appendVisitorInfoForURL(url);
    expect(spy).toHaveBeenCalledWith(url);
  });

  test('getUrlVariables is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'getUrlVariables');
    await ACPIdentity.getUrlVariables();
    expect(spy).toHaveBeenCalled();
  });

  test('getIdentifiers is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'getIdentifiers');
    await ACPIdentity.getIdentifiers();
    expect(spy).toHaveBeenCalled();
  });

  test('getExperienceCloudId is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'getExperienceCloudId');
    await ACPIdentity.getExperienceCloudId();
    expect(spy).toHaveBeenCalled();
  });

});
