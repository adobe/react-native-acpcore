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

const RCTACPSignal = require('react-native').NativeModules.ACPSignal;

module.exports = {
  /**
   * Returns the version of the ACPSignal extension
   * @param  {string} Promise [description]
   */
  extensionVersion(): Promise<string> {
    return Promise.resolve("1.0.0");
  },

  /**
   * Registers the ACPSignal extension with ACPCore
   */
  registerExtension() {
    RCTACPSignal.registerExtension();
  },

};
