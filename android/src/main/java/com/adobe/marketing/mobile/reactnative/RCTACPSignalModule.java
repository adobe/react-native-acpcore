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

    /**
     * Registers the Signal extension with the {@code MobileCore}
     * <p>
     * <p>
     * <p>
     * This will allow the extension to send and receive events to and from the SDK.
     *
     * @throws Exception If the registration was not successful.
     */
    @ReactMethod
    public void registerExtension() throws Exception {
        Signal.registerExtension();
    }


}
