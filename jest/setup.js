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

jest.mock('NativeModules', () => ({
  ACPCore: {
    extensionVersion: jest.fn(() => new Promise(resolve => resolve())),
    start: jest.fn(() => new Promise(resolve => resolve())),
    configureWithAppId: jest.fn(),
    updateConfiguration: jest.fn(),
    setLogLevel: jest.fn(),
    getLogLevel: jest.fn(() => new Promise(resolve => resolve())),
    log: jest.fn(),
    setPrivacyStatus: jest.fn(),
    getPrivacyStatus: jest.fn(() => new Promise(resolve => resolve())),
    getSdkIdentities: jest.fn(() => new Promise(resolve => resolve())),
    dispatchEvent: jest.fn(),
    dispatchEventWithResponseCallback: jest.fn(() => new Promise(resolve => resolve())),
    dispatchResponseEvent: jest.fn(() => new Promise(resolve => resolve())),
    trackAction: jest.fn(),
    trackState: jest.fn(),
    setAdvertisingIdentifier: jest.fn(),
    setPushIdentifier: jest.fn(),
    lifecycleStart: jest.fn(),
    lifecyclePause: jest.fn(),
    collectPii: jest.fn(),
    setSmallIconResourceID: jest.fn(),
    setLargeIconResourceID: jest.fn(),
    collectLaunchInfo: jest.fn(),
    setAppGroup: jest.fn(),
    downloadRules: jest.fn(),
  },
  ACPIdentity: {
    extensionVersion: jest.fn(() => new Promise(resolve => resolve())),
    registerExtension: jest.fn(),
    syncIdentifiers: jest.fn(),
    syncIdentifiersWithAuthState: jest.fn(),
    syncIdentifier: jest.fn(),
    appendVisitorInfoForURL: jest.fn(() => new Promise(resolve => resolve())),
    getUrlVariables: jest.fn(() => new Promise(resolve => resolve())),
    getIdentifiers: jest.fn(() => new Promise(resolve => resolve())),
    getExperienceCloudId: jest.fn(() => new Promise(resolve => resolve()))
  },
  ACPLifecycle: {
    extensionVersion: jest.fn(() => new Promise(resolve => resolve())),
    registerExtension: jest.fn()
  },
  ACPSignal: {
    extensionVersion: jest.fn(() => new Promise(resolve => resolve())),
    registerExtension: jest.fn()
  },
}));
