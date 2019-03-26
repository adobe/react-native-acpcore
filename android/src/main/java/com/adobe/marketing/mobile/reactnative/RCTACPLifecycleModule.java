/* ***********************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2019 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
package com.adobe.marketing.mobile.reactnative;


import android.util.Log;

import com.adobe.marketing.mobile.InvalidInitException;
import com.adobe.marketing.mobile.Lifecycle;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RCTACPLifecycleModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RCTACPLifecycleModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    // Required for RN modules
    @Override
    public String getName() {
        return "ACPLifecycle";
    }

    @ReactMethod
    public void extensionVersion(final Promise promise) {
        promise.resolve(Lifecycle.extensionVersion());
    }

    @ReactMethod
    public void registerExtension() {
        try {
            Lifecycle.registerExtension();
        } catch (InvalidInitException e) {
            Log.d(getName(), "Registering Lifecycle extension failed with error: " + e.getMessage());
        }
    }

}
