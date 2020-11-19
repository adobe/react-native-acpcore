/*
Copyright 2019 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.

@flow
@format
*/

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, Button, ScrollView, NativeModules} from 'react-native';
import {ACPCore, ACPLifecycle, ACPSignal, ACPIdentity, ACPMobileLogLevel, ACPMobilePrivacyStatus, ACPMobileVisitorAuthenticationState, ACPVisitorID, ACPExtensionEvent} from '@adobe/react-native-acpcore';

type Props = {};
export default class App extends Component<Props> {
  render() {
    return (
      <View style={styles.container}>
        <ScrollView contentContainerStyle={{ marginTop: 75 }}>
        <Text style={styles.welcome}>ACPCore Test App</Text>
        <Button title="ACPCore::extensionVersion()" onPress={() => this.coreExtensionVersion()}/>
        <Button title="ACPLifecycle::extensionVersion()" onPress={this.lifecycleExtensionVersion}/>
        <Button title="ACPIdentity::extensionVersion()" onPress={this.identityExtensionVersion}/>
        <Button title="ACPSignal::extensionVersion()" onPress={this.signalExtensionVersion}/>
        <Button title="ACPCore::setPrivacyStatus(OptIn)" onPress={this.setPrivacyOptIn}/>
        <Button title="ACPCore::setPrivacyStatus(OptOut)" onPress={this.setPrivacyOptOut}/>
        <Button title="ACPCore::getPrivacyStatus()" onPress={this.getPrivacyStatus}/>
        <Button title="ACPCore::getLogLevel()" onPress={this.getLogLevel}/>
        <Button title="ACPCore::log(...)" onPress={this.log}/>
        <Button title="ACPCore::getSdkIdentities()" onPress={this.getSdkIdentities}/>
        <Button title="ACPCore::setAdvertisingIdentifier(adID)" onPress={this.setAdvertisingIdentifier}/>
        <Button title="ACPCore::lifecycleStart()" onPress={this.lifecycleStart}/>
        <Button title="ACPCore::lifecyclePause()" onPress={this.lifecyclePause}/>
        <Button title="ACPCore::collectPii()" onPress={this.collectPii}/>
        <Button title="ACPCore::dispatchEvent()" onPress={this.dispatchEvent}/>
        <Button title="ACPCore::dispatchEventWithResponseCallback()" onPress={this.dispatchEventWithResponseCallback}/>
        <Button title="ACPCore::dispatchResponseEvent()" onPress={this.dispatchResponseEvent}/>

        <Button title="ACPIdentity::syncIdentifiers()" onPress={this.syncIdentifiers}/>
        <Button title="ACPIdentity::syncIdentifiersWithAuthState()" onPress={this.syncIdentifiersWithAuthState}/>
        <Button title="ACPIdentity::syncIdentifier()" onPress={this.syncIdentifier}/>
        <Button title="ACPIdentity::appendVisitorInfoForURL()" onPress={this.appendVisitorInfoForURL}/>
        <Button title="ACPIdentity::getUrlVariables()" onPress={this.getUrlVariables}/>
        <Button title="ACPIdentity::getIdentifiers()" onPress={this.getIdentifiers}/>
        <Button title="ACPIdentity::getExperienceCloudId()" onPress={this.getExperienceCloudId}/>
        </ScrollView>
      </View>
    );
  }

  coreExtensionVersion() {
    ACPCore.extensionVersion().then(version => console.log("AdobeExperienceSDK: ACPCore version: " + version));
  }

  lifecycleExtensionVersion() {
    ACPLifecycle.extensionVersion().then(version => console.log("AdobeExperienceSDK: ACPLifecycle version: " + version));
  }

  identityExtensionVersion() {
    ACPIdentity.extensionVersion().then(version => console.log("AdobeExperienceSDK: ACPIdentity version: " + version));
  }

  signalExtensionVersion() {
    ACPSignal.extensionVersion().then(version => console.log("AdobeExperienceSDK: ACPSignal version: " + version));
  }


  setPrivacyOptIn() {
    ACPCore.setPrivacyStatus(ACPMobilePrivacyStatus.OPT_IN);
  }

  setPrivacyOptOut() {
    ACPCore.setPrivacyStatus(ACPMobilePrivacyStatus.OPT_OUT);
  }

  getPrivacyStatus() {
    ACPCore.getPrivacyStatus().then(status => console.log("AdobeExperienceSDK: Privacy Status = " + status));
  }

  getLogLevel() {
    ACPCore.getLogLevel().then(level => console.log("AdobeExperienceSDK: Log Level = " + level));
  }

  log() {
    ACPCore.log(ACPMobileLogLevel.VERBOSE, "React Native Tag", "React Native Message");
  }

  getSdkIdentities() {
    ACPCore.getSdkIdentities().then(identities => console.log("AdobeExperienceSDK: Identities = " + identities));
  }

  setAdvertisingIdentifier() {
    ACPCore.setAdvertisingIdentifier("adID");
  }

  lifecycleStart() {
    ACPCore.lifecycleStart({"lifecycleStart": "myData"});
  }

  lifecyclePause() {
    ACPCore.lifecyclePause();
  }

  collectPii() {
    ACPCore.collectPii({"myPii": "data"});
  }

  dispatchEvent() {
    var event = new ACPExtensionEvent("eventName", "eventType", "eventSource", {"testDataKey": "testDataValue"});
    ACPCore.dispatchEvent(event);
  }

  dispatchEventWithResponseCallback() {
    var event = new ACPExtensionEvent("eventName", "eventType", "eventSource", {"testDataKey": "testDataValue"});
    ACPCore.dispatchEventWithResponseCallback(event).then(responseEvent => console.log("AdobeExperienceSDK: responseEvent = " + responseEvent));
  }

  dispatchResponseEvent() {
    var responseEvent = new ACPExtensionEvent("responseEvent", "eventType", "eventSource", {"testDataKey": "testDataValue"});
    var requestEvent = new ACPExtensionEvent("requestEvent", "eventType", "eventSource", {"testDataKey": "testDataValue"});
    ACPCore.dispatchResponseEvent(responseEvent, requestEvent);
  }

  // identity

  syncIdentifiers() {
    ACPIdentity.syncIdentifiers({"id1": "identifier1"});
  }

  syncIdentifiersWithAuthState() {
    ACPIdentity.syncIdentifiersWithAuthState({"id1": "identifier1"}, "ACP_VISITOR_AUTH_STATE_AUTHENTICATED");
  }

  syncIdentifier() {
    ACPIdentity.syncIdentifier("idType", "ID", ACPMobileVisitorAuthenticationState.AUTHENTICATED);
  }

  appendVisitorInfoForURL() {
    ACPIdentity.appendVisitorInfoForURL("test.com").then(urlWithVisitorData => console.log("AdobeExperienceSDK: VisitorData = " + urlWithVisitorData));
  }

  getUrlVariables() {
    ACPIdentity.getUrlVariables().then(urlVariables => console.log("AdobeExperienceSDK: UrlVariables = " + urlVariables));
  }

  getIdentifiers() {
    ACPIdentity.getIdentifiers().then(identifiers => console.log("AdobeExperienceSDK: Identifiers = " + identifiers));
  }

  getExperienceCloudId() {
    ACPIdentity.getExperienceCloudId().then(cloudId => console.log("AdobeExperienceSDK: CloudID = " + cloudId));
  }

}

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
  }
});
