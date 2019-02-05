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

const { RCTACPSignal: Native } = NativeModules;

const EXTENSION_VERSION = '1.0.0';

export class ACPSignal {
  /**
   * Returns the version of the ACPSignal extension
   * @param  {string} Promise a promise that resolves with the extension verison
   */
  static extensionVersion(): Promise<string> {
    return Promise.resolve(EXTENSION_VERSION);
  }

  /**
   * Registers the ACPSignal extension with ACPCore
   */
  static registerExtension() {
    Native.registerExtension();
  }
}
