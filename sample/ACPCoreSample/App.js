/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 * @lint-ignore-every XPLATJSCOPYRIGHT1
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, Button, ScrollView} from 'react-native';

type Props = {};
export default class App extends Component<Props> {
  render() {
    return (
      <View style={styles.container}>
        <ScrollView contentContainerStyle={{ marginTop: 75 }}>
        <Text style={styles.welcome}>ACPCore Test App</Text>
        <Button title="ACPCore::extensionVersion()"/>
        <Button title="ACPIdentity::extensionVersion()"/>
        <Button title="ACPLifecycle::extensionVersion()"/>
        <Button title="ACPSignal::extensionVersion()"/>

        <Button title="ACPCore::setLogLevel(Verbose)"/>
        <Button title="ACPCore::setPrivacyStatus(OptIn)"/>
        <Button title="ACPCore::setPrivacyStatus(OptOut)"/>
        <Button title="ACPCore::getPrivacyStatus()"/>
        <Button title="ACPCore::getSdkIdentities()"/>
        <Button title="ACPCore::setAdvertisingIdentifier(adID)"/>
        <Button title="ACPCore::lifecycleStart()"/>
        <Button title="ACPCore::lifecyclePause()"/>
        <Button title="ACPCore::lifecyclePause()"/>
        <Button title="ACPCore::collectPii()"/>
        <Button title="ACPCore::setAppGroup()"/>
        <Button title="ACPCore::downloadRules()"/>

        <Button title="ACPIdentity::syncIdentifiers()"/>
        <Button title="ACPIdentity::syncIdentifiersWithAuthState()"/>
        <Button title="ACPIdentity::syncIdentifier()"/>
        <Button title="ACPIdentity::appendVisitorInfoForURL()"/>
        <Button title="ACPIdentity::getIdentifiers()"/>
        <Button title="ACPIdentity::getExperienceCloudId()"/>
        </ScrollView>
      </View>
    );
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
