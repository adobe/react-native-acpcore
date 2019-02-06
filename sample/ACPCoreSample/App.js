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
import {ACPCore, ACPLifecycle} from 'react-native-acpcore';

type Props = {};
export default class App extends Component<Props> {
  render() {
    const RCTACPLifecycle = require('react-native').NativeModules.ACPLifecycle;
    console.log("AMSDK rct: " + RCTACPLifecycle);
    this.initSDK();
    return (
      <View style={styles.container}>
        <ScrollView contentContainerStyle={{ marginTop: 75 }}>
        <Text style={styles.welcome}>ACPCore Test App</Text>
        <Button title="ACPCore::extensionVersion()" onPress={this.coreExtensionVersion}/>
        </ScrollView>
      </View>
    );
  }

  initSDK() {
    ACPCore.setLogLevel("ACP_LOG_LEVEL_VERBOSE");
    ACPCore.configureWithAppId("launch-EN1a68f9bc5b3c475b8c232adc3f8011fb");
    console.log("AMSDK: ACPLifecycle = " + ACPLifecycle);
    ACPCore.start();
  }

  coreExtensionVersion() {
    ACPCore.extensionVersion().then(version => console.log("AMSDK: ACPCore version: " + version));
  }
  //
  // async identityExtensionVersion() {
  //   try {
  //     var version = await ACPIdentity.extensionVersion();
  //     console.log("ACPIdentity version: " + version);
  //   } catch (e) {
  //     console.log("failed to get version");
  //   }
  // }
  //
  // async lifecycleExtensionVersion() {
  //   ACPLifecycle.extensionVersion().then(version => console.log("ACPLifecycle version: " + version));
  // }
  //
  // async signalExtensionVersion() {
  //   ACPSignal.extensionVersion().then(version => console.log("ACPSignal version: " + version));
  // }
  //
  // setPrivacyOptIn() {
  //   ACPCore.setPrivacyStatus("ACP_PRIVACY_STATUS_OPT_IN");
  // }
  //
  // setPrivacyOptOut() {
  //   ACPCore.setPrivacyStatus("ACP_PRIVACY_STATUS_OPT_OUT");
  // }
  //
  // async getPrivacyStatus() {
  //   try {
  //     var status = await ACPCore.getPrivacyStatus();
  //     console.log("Privacy status " + status);
  //   } catch (e) {
  //     console.log("failed to get status");
  //   }
  // }
  //
  // async getSdkIdentities() {
  //   try {
  //     var identities = await ACPCore.getSdkIdentities();
  //     console.log("ACPCore identities: " + identities);
  //   } catch (e) {
  //     console.log("failed to get identities");
  //   }
  // }
  //
  // setAdvertisingIdentifier() {
  //   ACPCore.setAdvertisingIdentifier("adID");
  // }
  //
  // lifecycleStart() {
  //   ACPCore.lifecycleStart({"lifecycleStart": "myData"});
  // }
  //
  // lifecyclePause() {
  //   ACPCore.lifecyclePause();
  // }
  //
  // collectPii() {
  //   ACPCore.collectPii({"myPii": "data"});
  // }
  //
  // // identity
  //
  // syncIdentifiers() {
  //   ACPIdentity.syncIdentifiers({"id1": "identifier1"});
  // }
  //
  // syncIdentifiersWithAuthState() {
  //   ACPCore.syncIdentifiers({"id1": "identifier1"}, "ACP_VISITOR_AUTH_STATE_AUTHENTICATED");
  // }
  //
  // syncIdentifier() {
  //   ACPIdentity.syncIdentifier("idType", "ID", "ACP_VISITOR_AUTH_STATE_AUTHENTICATED");
  // }
  //
  // appendVisitorInfoForURL() {
  //   // TODO
  // }
  //
  // async getIdentifiers() {
  //   try {
  //     var cloudId = await ACPIdentity.getIdentifiers();
  //     console.log("ACPIdentity identifiers: " + identities);
  //   } catch (e) {
  //     console.log("failed to get identifiers");
  //   }
  // }
  //
  // async getExperienceCloudId() {
  //   try {
  //     var cloudId = await ACPIdentity.getExperienceCloudId();
  //     console.log("ACPIdentity cloudId: " + cloudId);
  //   } catch (e) {
  //     console.log("failed to get cloudId");
  //   }
  // }

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
