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

import com.adobe.marketing.mobile.AdobeCallback;
import com.adobe.marketing.mobile.Identity;
import com.adobe.marketing.mobile.MobilePrivacyStatus;
import com.adobe.marketing.mobile.VisitorID;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;

import java.util.List;

public class RCTACPIdentityModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RCTACPIdentityModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    // Required for RN modules
    @Override
    public String getName() {
        return "ACPIdentity";
    }

    @ReactMethod
    public void extensionVersion(final Promise promise) {
        promise.resolve(Identity.extensionVersion());
    }

    @ReactMethod
    public void registerExtension() throws Exception {
        Identity.registerExtension();
    }

    @ReactMethod
    public void syncIdentifiers(final ReadableMap identifiers) {
        Identity.syncIdentifiers(RCTACPMapUtil.toStringMap(identifiers));
    }

    @ReactMethod
    public void syncIdentifiersWithAuthState(final ReadableMap identifiers,
                                final String authenticationState) {
        VisitorID.AuthenticationState authState = RCTACPIdentityDataBridge.authenticationStateFromString(authenticationState);
        Identity.syncIdentifiers(RCTACPMapUtil.toStringMap(identifiers), RCTACPIdentityDataBridge.authenticationStateFromString(authenticationState));
    }

    @ReactMethod
    public void syncIdentifier(final String identifierType,
                               final String identifier,
                               final String authenticationState) {
        VisitorID.AuthenticationState authState = RCTACPIdentityDataBridge.authenticationStateFromString(authenticationState);
        Identity.syncIdentifier(identifierType, identifier, authState);
    }

    @ReactMethod
    public void appendVisitorInfoForURL(final String baseURL, final Promise promise) {
        Identity.appendVisitorInfoForURL(baseURL, new AdobeCallback<String>() {
            @Override
            public void call(String s) {
                promise.resolve(s);
            }
        });
    }

    @ReactMethod
    public void getIdentifiers(final Promise promise) {
        Identity.getIdentifiers(new AdobeCallback<List<VisitorID>>() {
            @Override
            public void call(List<VisitorID> visitorIDS) {
                WritableArray arr = new WritableNativeArray();
                for (VisitorID vid: visitorIDS) {
                    arr.pushMap(RCTACPIdentityDataBridge.mapFromVisitorIdentifier(vid));
                }
                promise.resolve(arr);
            }
        });
    }

    @ReactMethod
    public void getExperienceCloudId(final Promise promise) {
        Identity.getExperienceCloudId(new AdobeCallback<String>() {
            @Override
            public void call(String s) {
                promise.resolve(s);
            }
        });
    }

}
