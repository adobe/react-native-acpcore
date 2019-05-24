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
import ACPCore from '../js/ACPCore';
import ACPMobileLogLevel from '../js/models/ACPMobileLogLevel';
import ACPMobilePrivacyStatus from '../js/models/ACPMobilePrivacyStatus';
import ACPExtensionEvent from '../js/models/ACPExtensionEvent';

describe('ACPCore', () => {

  test('extensionVersion is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'extensionVersion');
    await ACPCore.extensionVersion();
    expect(spy).toHaveBeenCalled();
  });

  test('start is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'start');
    await ACPCore.start();
    expect(spy).toHaveBeenCalled();
  });

  test('configureWithAppId is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'configureWithAppId');
    let appId = "testAppId";
    await ACPCore.configureWithAppId(appId);
    expect(spy).toHaveBeenCalledWith(appId);
  });

  test('updateConfiguration is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'updateConfiguration');
    let config = {"ssl": "false"};
    await ACPCore.updateConfiguration(config);
    expect(spy).toHaveBeenCalledWith(config);
  });

  test('setLogLevel is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'setLogLevel');
    let logLevel = ACPMobileLogLevel.DEBUG;
    await ACPCore.setLogLevel(logLevel);
    expect(spy).toHaveBeenCalledWith(logLevel);
  });

  test('getLogLevel is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'getLogLevel');
    await ACPCore.getLogLevel();
    expect(spy).toHaveBeenCalled();
  });

  test('log is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'log');
    let logLevel = ACPMobileLogLevel.DEBUG;
    let tag = "ACPCoreTests";
    let message = "Hello from jest tests!";
    await ACPCore.log(logLevel, tag, message);
    expect(spy).toHaveBeenCalledWith(logLevel, tag, message);
  });

  test('setPrivacyStatus is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'setPrivacyStatus');
    let privacyStatus = ACPMobilePrivacyStatus.UNKNOWN;
    await ACPCore.setPrivacyStatus(privacyStatus);
    expect(spy).toHaveBeenCalledWith(privacyStatus);
  });

  test('getPrivacyStatus is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'getPrivacyStatus');
    await ACPCore.getPrivacyStatus();
    expect(spy).toHaveBeenCalled();
  });

  test('getSdkIdentities is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'getSdkIdentities');
    await ACPCore.getSdkIdentities();
    expect(spy).toHaveBeenCalled();
  });

  test('dispatchEvent is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'dispatchEvent');
    let testEvent = new ACPExtensionEvent("eventName", "eventType", "eventSource", {"testDataKey": "testDataValue"});
    await ACPCore.dispatchEvent(testEvent);
    expect(spy).toHaveBeenCalledWith(testEvent);
  });

  test('dispatchEventWithResponseCallback is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'dispatchEventWithResponseCallback');
    let testEvent = new ACPExtensionEvent("eventName", "eventType", "eventSource", {"testDataKey": "testDataValue"});
    await ACPCore.dispatchEventWithResponseCallback(testEvent);
    expect(spy).toHaveBeenCalledWith(testEvent);
  });

  test('dispatchResponseEvent is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'dispatchResponseEvent');
    let testEvent = new ACPExtensionEvent("eventName", "eventType", "eventSource", {"testDataKey": "testDataValue"});
    let testEvent1 = new ACPExtensionEvent("eventName1", "eventType1", "eventSource1", {"testDataKey1": "testDataValue1"});
    await ACPCore.dispatchResponseEvent(testEvent, testEvent1);
    expect(spy).toHaveBeenCalledWith(testEvent, testEvent1);
  });

  test('trackAction is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'trackAction');
    let actionName = "testAction";
    let contextData = {"testKey": "testValue"};
    await ACPCore.trackAction(actionName, contextData);
    expect(spy).toHaveBeenCalledWith(actionName, contextData);
  });

  test('trackState is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'trackState');
    let stateName = "testState";
    let contextData = {"testKey": "testValue"};
    await ACPCore.trackState(stateName, contextData);
    expect(spy).toHaveBeenCalledWith(stateName, contextData);
  });

  test('setAdvertisingIdentifier is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'setAdvertisingIdentifier');
    let adId = "testAdId";
    await ACPCore.setAdvertisingIdentifier(adId);
    expect(spy).toHaveBeenCalledWith(adId);
  });

  test('setPushIdentifier is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'setPushIdentifier');
    let pushIdentifier = "testPushId";
    await ACPCore.setPushIdentifier(pushIdentifier);
    expect(spy).toHaveBeenCalledWith(pushIdentifier);
  });

  test('lifecycleStart is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'lifecycleStart');
    let contextData = {"testKey": "testValue"};
    await ACPCore.lifecycleStart(contextData);
    expect(spy).toHaveBeenCalledWith(contextData);
  });

  test('lifecyclePause is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'lifecyclePause');
    await ACPCore.lifecyclePause();
    expect(spy).toHaveBeenCalled();
  });

  test('collectPii is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'collectPii');
    let contextData = {"testKey": "testValue"};
    await ACPCore.collectPii(contextData);
    expect(spy).toHaveBeenCalledWith(contextData);
  });

  test('setSmallIconResourceID is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'setSmallIconResourceID');
    let resourceID = 1
    await ACPCore.setSmallIconResourceID(resourceID);
    expect(spy).toHaveBeenCalledWith(resourceID);
  });

  test('setLargeIconResourceID is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'setLargeIconResourceID');
    let resourceID = 1
    await ACPCore.setLargeIconResourceID(resourceID);
    expect(spy).toHaveBeenCalledWith(resourceID);
  });

  test('collectLaunchInfo is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'collectLaunchInfo');
    await ACPCore.collectLaunchInfo();
    expect(spy).toHaveBeenCalled();
  });

  test('setAppGroup is called with correct parameters', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'setAppGroup');
    let appGroup = "testAppGroup"
    await ACPCore.setAppGroup(appGroup);
    expect(spy).toHaveBeenCalledWith(appGroup);
  });

  test('downloadRules is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'downloadRules');
    await ACPCore.downloadRules();
    expect(spy).toHaveBeenCalled();
  });

});
