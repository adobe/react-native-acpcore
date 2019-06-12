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

import com.adobe.marketing.mobile.AdobeCallback;
import com.adobe.marketing.mobile.Identity;
import com.adobe.marketing.mobile.InvalidInitException;
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
    public void registerExtension() {
        try {
            Identity.registerExtension();
        } catch (InvalidInitException e) {
            Log.d(getName(), "Registering Identity extension failed with error: " + e.getMessage());
        }
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
    public void getUrlVariables(final Promise promise) {
        Identity.getUrlVariables(new AdobeCallback<String>() {
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
