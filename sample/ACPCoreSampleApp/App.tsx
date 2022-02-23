/*
Copyright 2022 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
*/

import React from 'react';
import {StyleSheet, Text, View, Button, ScrollView} from 'react-native';
import {
  ACPCore,
  ACPLifecycle,
  ACPSignal,
  ACPIdentity,
  ACPExtensionEvent,
} from '@adobe/react-native-acpcore';

const App = () => {
  const coreExtensionVersion = () => {
    ACPCore.extensionVersion().then(version =>
      console.log('AdobeExperienceSDK: ACPCore version: ' + version),
    );
  };

  const lifecycleExtensionVersion = () => {
    ACPLifecycle.extensionVersion().then(version =>
      console.log('AdobeExperienceSDK: ACPLifecycle version: ' + version),
    );
  };

  const identityExtensionVersion = () => {
    ACPIdentity.extensionVersion().then(version =>
      console.log('AdobeExperienceSDK: ACPIdentity version: ' + version),
    );
  };

  const signalExtensionVersion = () => {
    ACPSignal.extensionVersion().then(version =>
      console.log('AdobeExperienceSDK: ACPSignal version: ' + version),
    );
  };

  const setPrivacyOptIn = () => {
    ACPCore.setPrivacyStatus('ACP_PRIVACY_STATUS_OPT_IN');
  };

  const setPrivacyOptOut = () => {
    ACPCore.setPrivacyStatus('ACP_PRIVACY_STATUS_OPT_OUT');
  };

  const getPrivacyStatus = () => {
    ACPCore.getPrivacyStatus().then(status =>
      console.log('AdobeExperienceSDK: Privacy Status = ' + status),
    );
  };

  const getLogLevel = () => {
    ACPCore.getLogLevel().then(level =>
      console.log('AdobeExperienceSDK: Log Level = ' + level),
    );
  };

  const log = () => {
    ACPCore.log(
      'ACP_LOG_LEVEL_VERBOSE',
      'React Native Tag',
      'React Native Message',
    );
  };

  const getSdkIdentities = () => {
    ACPCore.getSdkIdentities().then(identities =>
      console.log('AdobeExperienceSDK: Identities = ' + identities),
    );
  };

  const setAdvertisingIdentifier = () => {
    ACPCore.setAdvertisingIdentifier('adID');
  };

  const collectPii = () => {
    ACPCore.collectPii({myPii: 'data'});
  };

  const dispatchEvent = () => {
    var event = new ACPExtensionEvent('eventName', 'eventType', 'eventSource', {
      testDataKey: 'testDataValue',
    });
    ACPCore.dispatchEvent(event);
  };

  const dispatchEventWithResponseCallback = () => {
    var event = new ACPExtensionEvent('eventName', 'eventType', 'eventSource', {
      testDataKey: 'testDataValue',
    });
    ACPCore.dispatchEventWithResponseCallback(event).then(responseEvent =>
      console.log('AdobeExperienceSDK: responseEvent = ' + responseEvent),
    );
  };

  const dispatchResponseEvent = () => {
    var responseEvent = new ACPExtensionEvent(
      'responseEvent',
      'eventType',
      'eventSource',
      {testDataKey: 'testDataValue'},
    );
    var requestEvent = new ACPExtensionEvent(
      'requestEvent',
      'eventType',
      'eventSource',
      {testDataKey: 'testDataValue'},
    );
    ACPCore.dispatchResponseEvent(responseEvent, requestEvent);
  };

  // identity

  const syncIdentifiers = () => {
    ACPIdentity.syncIdentifiers({id1: 'identifier1'});
  };

  const syncIdentifiersWithAuthState = () => {
    ACPIdentity.syncIdentifiersWithAuthState(
      {id1: 'identifier1'},
      'ACP_VISITOR_AUTH_STATE_AUTHENTICATED',
    );
  };

  const syncIdentifier = () => {
    ACPIdentity.syncIdentifier(
      'idType',
      'ID',
      'ACP_VISITOR_AUTH_STATE_AUTHENTICATED',
    );
  };

  const appendVisitorInfoForURL = () => {
    ACPIdentity.appendVisitorInfoForURL('test.com').then(urlWithVisitorData =>
      console.log('AdobeExperienceSDK: VisitorData = ' + urlWithVisitorData),
    );
  };

  const getUrlVariables = () => {
    ACPIdentity.getUrlVariables().then(urlVariables =>
      console.log('AdobeExperienceSDK: UrlVariables = ' + urlVariables),
    );
  };

  const getIdentifiers = () => {
    ACPIdentity.getIdentifiers().then(identifiers =>
      console.log('AdobeExperienceSDK: Identifiers = ' + identifiers),
    );
  };

  const getExperienceCloudId = () => {
    ACPIdentity.getExperienceCloudId().then(cloudId =>
      console.log('AdobeExperienceSDK: CloudID = ' + cloudId),
    );
  };

  return (
    <View style={styles.container}>
      <ScrollView contentContainerStyle={{marginTop: 75}}>
        <Text style={styles.welcome}>ACPCore Test App</Text>
        <Button
          title="ACPCore::extensionVersion()"
          onPress={() => coreExtensionVersion()}
        />
        <Button
          title="ACPLifecycle::extensionVersion()"
          onPress={lifecycleExtensionVersion}
        />
        <Button
          title="ACPIdentity::extensionVersion()"
          onPress={identityExtensionVersion}
        />
        <Button
          title="ACPSignal::extensionVersion()"
          onPress={signalExtensionVersion}
        />
        <Button
          title="ACPCore::setPrivacyStatus(OptIn)"
          onPress={setPrivacyOptIn}
        />
        <Button
          title="ACPCore::setPrivacyStatus(OptOut)"
          onPress={setPrivacyOptOut}
        />
        <Button
          title="ACPCore::getPrivacyStatus()"
          onPress={getPrivacyStatus}
        />
        <Button title="ACPCore::getLogLevel()" onPress={getLogLevel} />
        <Button title="ACPCore::log(...)" onPress={log} />
        <Button
          title="ACPCore::getSdkIdentities()"
          onPress={getSdkIdentities}
        />
        <Button
          title="ACPCore::setAdvertisingIdentifier(adID)"
          onPress={setAdvertisingIdentifier}
        />
        <Button title="ACPCore::collectPii()" onPress={collectPii} />
        <Button title="ACPCore::dispatchEvent()" onPress={dispatchEvent} />
        <Button
          title="ACPCore::dispatchEventWithResponseCallback()"
          onPress={dispatchEventWithResponseCallback}
        />
        <Button
          title="ACPCore::dispatchResponseEvent()"
          onPress={dispatchResponseEvent}
        />

        <Button
          title="ACPIdentity::syncIdentifiers()"
          onPress={syncIdentifiers}
        />
        <Button
          title="ACPIdentity::syncIdentifiersWithAuthState()"
          onPress={syncIdentifiersWithAuthState}
        />
        <Button
          title="ACPIdentity::syncIdentifier()"
          onPress={syncIdentifier}
        />
        <Button
          title="ACPIdentity::appendVisitorInfoForURL()"
          onPress={appendVisitorInfoForURL}
        />
        <Button
          title="ACPIdentity::getUrlVariables()"
          onPress={getUrlVariables}
        />
        <Button
          title="ACPIdentity::getIdentifiers()"
          onPress={getIdentifiers}
        />
        <Button
          title="ACPIdentity::getExperienceCloudId()"
          onPress={getExperienceCloudId}
        />
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 22,
    textAlign: 'center',
    margin: 10,
  },
});

export default App;
