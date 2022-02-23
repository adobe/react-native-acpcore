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

import { NativeModules } from "react-native";

interface IACPSignal {
  extensionVersion: () => Promise<string>;
}

const RCTACPSignal: IACPSignal = NativeModules.ACPSignal;

const ACPSignal: IACPSignal = {
  /**
   * Returns the version of the ACPSignal extension
   * @param  {string} Promise a promise that resolves with the extension verison
   */
  extensionVersion(): Promise<string> {
    return Promise.resolve(RCTACPSignal.extensionVersion());
  },
};

export default ACPSignal;
