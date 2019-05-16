/*
Copyright 2019 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
*/
package com.adobe.marketing.mobile.reactnative;

import android.util.Log;

import com.adobe.marketing.mobile.InvalidInitException;
import com.adobe.marketing.mobile.Signal;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RCTACPSignalModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RCTACPSignalModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    // Required for RN modules
    @Override
    public String getName() {
        return "ACPSignal";
    }

    @ReactMethod
    public void extensionVersion(final Promise promise) {
        promise.resolve(Signal.extensionVersion());
    }

    @ReactMethod
    public void registerExtension() {
        try {
            Signal.registerExtension();
        } catch (InvalidInitException e) {
            Log.d(getName(), "Registering Signal extension failed with error: " + e.getMessage());
        }
    }

}
