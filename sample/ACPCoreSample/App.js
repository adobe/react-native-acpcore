/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 * @lint-ignore-every XPLATJSCOPYRIGHT1
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, Button, ScrollView, NativeModules} from 'react-native';
import {ACPCore, ACPLifecycle, ACPSignal, ACPIdentity, ACPMobileLogLevel, ACPMobilePrivacyStatus, ACPMobileVisitorAuthenticationState, ACPVisitorID, ACPExtensionEvent} from 'react-native-acpcore';

type Props = {};
export default class App extends Component<Props> {
  render() {
    this.initSDK();
    return (
      <View style={styles.container}>
        <ScrollView contentContainerStyle={{ marginTop: 75 }}>
        <Text style={styles.welcome}>ACPCore Test App</Text>
        <Button title="ACPCore::extensionVersion()" onPress={this.coreExtensionVersion}/>
        <Button title="ACPLifecycle::extensionVersion()" onPress={this.lifecycleExtensionVersion}/>
        <Button title="ACPIdentity::extensionVersion()" onPress={this.identityExtensionVersion}/>
        <Button title="ACPSignal::extensionVersion()" onPress={this.signalExtensionVersion}/>
        <Button title="ACPCore::setPrivacyStatus(OptIn)" onPress={this.setPrivacyOptIn}/>
        <Button title="ACPCore::setPrivacyStatus(OptOut)" onPress={this.setPrivacyOptOut}/>
        <Button title="ACPCore::getPrivacyStatus()" onPress={this.getPrivacyStatus}/>
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
        <Button title="ACPIdentity::getIdentifiers()" onPress={this.getIdentifiers}/>
        <Button title="ACPIdentity::getExperienceCloudId()" onPress={this.getExperienceCloudId}/>
        </ScrollView>
      </View>
    );
  }

  initSDK() {
    // console.log("AMSDK IMPORT: ACPCore = " + ACPCore);
    // console.log("AMSDK IMPORT: ACPLifecycle = " + ACPLifecycle);
    // console.log("AMSDK IMPORT: ACPSignal = " + ACPSignal);
    // console.log("AMSDK IMPORT: ACPIdentity = " + ACPIdentity);
    // console.log("AMSDK IMPORT: ACPMobileLogLevel = " + ACPMobileLogLevel);
    // console.log("AMSDK IMPORT: ACPMobilePrivacyStatus = " + ACPMobilePrivacyStatus);
    // console.log("AMSDK IMPORT: ACPMobileVisitorAuthenticationState = " + ACPMobileVisitorAuthenticationState);
    // console.log("AMSDK IMPORT: ACPVisitorID = " + ACPVisitorID);
    ACPCore.setLogLevel("ACP_LOG_LEVEL_VERBOSE");
    ACPCore.configureWithAppId("launch-EN1a68f9bc5b3c475b8c232adc3f8011fb");
    ACPLifecycle.registerExtension();
    ACPIdentity.registerExtension();
    ACPSignal.registerExtension();
    ACPCore.start();
  }

  coreExtensionVersion() {
    ACPCore.extensionVersion().then(version => console.log("AMSDK: ACPCore version: " + version));
  }

  lifecycleExtensionVersion() {
    ACPLifecycle.extensionVersion().then(version => console.log("AMSDK: ACPLifecycle version: " + version));
  }

  identityExtensionVersion() {
    ACPIdentity.extensionVersion().then(version => console.log("AMSDK: ACPIdentity version: " + version));
  }

  signalExtensionVersion() {
    ACPSignal.extensionVersion().then(version => console.log("AMSDK: ACPSignal version: " + version));
  }


  setPrivacyOptIn() {
    ACPCore.setPrivacyStatus("ACP_PRIVACY_STATUS_OPT_IN");
  }

  setPrivacyOptOut() {
    ACPCore.setPrivacyStatus("ACP_PRIVACY_STATUS_OPT_OUT");
  }

  getPrivacyStatus() {
    ACPCore.getPrivacyStatus().then(status => console.log("AMSDK: Privacy Status = " + status));
  }

  getSdkIdentities() {
    ACPCore.getSdkIdentities().then(identities => console.log("AMSDK: Identities = " + identities));
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
    ACPCore.dispatchEventWithResponseCallback(event).then(responseEvent => console.log("AMSDK: responseEvent = " + responseEvent));
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
    ACPIdentity.syncIdentifier("idType", "ID", "ACP_VISITOR_AUTH_STATE_AUTHENTICATED");
  }

  appendVisitorInfoForURL() {
    ACPIdentity.appendVisitorInfoForURL("test.com").then(urlWithVisitorData => console.log("AMSDK: VisitorData = " + urlWithVisitorData));
  }

  getIdentifiers() {
    ACPIdentity.getIdentifiers().then(identifiers => console.log("AMSDK: Identifiers = " + identifiers));
  }

  getExperienceCloudId() {
    ACPIdentity.getExperienceCloudId().then(cloudId => console.log("AMSDK: CloudID = " + cloudId));
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
