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

    /**
     * Registers the Identity extension with the {@code MobileCore}
     * <p>
     * <p>
     * <p>
     * This will allow the extension to send and receive events to and from the SDK.
     *
     * @throws Exception If the registration was not successful.
     */
    @ReactMethod
    public void registerExtension() throws Exception {
        Identity.registerExtension();
    }

    // =======================================================================
    // Identity Methods
    // =======================================================================

    /**
     * Updates the given customer IDs with the Adobe Experience Cloud ID Service.
     * <p>
     * Synchronizes the provided customer identifiers to the Adobe Experience Cloud ID Service.
     * If a customer ID type matches an existing ID type, then it is updated with the new ID value
     * and authentication state. New customer IDs are added. All given customer IDs are given the default
     * authentication state of {@link com.adobe.marketing.mobile.VisitorID.AuthenticationState#UNKNOWN}.
     * <p>
     * These IDs are preserved between app upgrades, are saved and restored during the standard application backup process,
     * and are removed at uninstall.
     * <p>
     * If the current SDK privacy status is {@link MobilePrivacyStatus#OPT_OUT}, then calling this method results
     * with no operations being performed.
     *
     * @param identifiers {@code ReadableMap} containing identifier type as the key and identifier as the value
     */
    @ReactMethod
    public void syncIdentifiers(final ReadableMap identifiers) {
        Identity.syncIdentifiers(RCTACPMapUtil.toStringMap(identifiers));
    }

    /**
     * Updates the given customer IDs with the Adobe Experience Cloud ID Service.
     * <p>
     * Synchronizes the provided customer identifiers to the Adobe Experience Cloud ID Service.
     * If a customer ID type matches an existing ID type, then it is updated with the new ID value
     * and authentication state. New customer IDs are added.
     * <p>
     * These IDs are preserved between app upgrades, are saved and restored during the standard application backup process,
     * and are removed at uninstall.
     * <p>
     * If the current SDK privacy status is {@link MobilePrivacyStatus#OPT_OUT}, then calling this method results
     * with no operations being performed.
     *
     * @param identifiers         {@code ReadableMap} containing identifier type as the key and identifier as the value
     * @param authenticationState {@code String} value indicating authentication state for the user {authenticated, logged_out, unknown}
     */
    @ReactMethod
    public void syncIdentifiersWithAuthState(final ReadableMap identifiers,
                                final String authenticationState) {
        VisitorID.AuthenticationState authState = RCTACPIdentityDataBridge.authenticationStateFromString(authenticationState);
        Identity.syncIdentifiers(RCTACPMapUtil.toStringMap(identifiers), RCTACPIdentityDataBridge.authenticationStateFromString(authenticationState));
    }

    /**
     * Updates the given customer ID with the Adobe Experience Cloud ID Service.
     * <p>
     * Synchronizes the provided customer identifier type key and value with the given
     * authentication state to the Adobe Experience Cloud ID Service.
     * If the given customer ID type already exists in the service, then
     * it is updated with the new ID and authentication state. Otherwise a new customer ID is added.
     * <p>
     * This ID is preserved between app upgrades, is saved and restored during the standard application backup process,
     * and is removed at uninstall.
     * <p>
     * If the current SDK privacy status is {@link MobilePrivacyStatus#OPT_OUT}, then calling this method results
     * with no operations being performed.
     *
     * @param identifierType      {@code String} containing identifier type
     * @param identifier          {@code String} containing identifier value
     * @param authenticationState {@code String} value indicating authentication state for the user {authenticated, logged_out, unknown}
     */
    @ReactMethod
    public void syncIdentifier(final String identifierType,
                               final String identifier,
                               final String authenticationState) {
        VisitorID.AuthenticationState authState = RCTACPIdentityDataBridge.authenticationStateFromString(authenticationState);
        Identity.syncIdentifier(identifierType, identifier, authState);
    }

    /**
     * Appends Adobe visitor data to a URL string.
     * <p>
     * If the provided URL is null or empty, it is returned as is. Otherwise, the following information is added to the
     * {@link String} returned in the {@link Promise}:
     * <ul>
     * <li>The {@code adobe_mc} attribute is an URL encoded list containing:
     * <ul>
     * <li>Experience Cloud ID (ECID)</li>
     * <li>Experience Cloud Org ID</li>
     * <li>A timestamp taken when this request was made</li>
     * </ul>
     * </li>
     * <li>The optional {@code adobe_aa_vid} attribute is the URL encoded Analytics Custom Visitor ID, if available</li>
     * </ul>
     *
     * @param baseURL {@code String} URL to which the visitor info needs to be appended
     * @param promise {@code Promise} invoked with the updated URL {@code String}
     */
    @ReactMethod
    public void appendVisitorInfoForURL(final String baseURL, final Promise promise) {
        Identity.appendVisitorInfoForURL(baseURL, new AdobeCallback<String>() {
            @Override
            public void call(String s) {
                promise.resolve(s);
            }
        });
    }

    /**
     * Returns all customer identifiers which were previously synced with the Adobe Experience Cloud.
     *
     * @param promise {@link Promise} invoked with the list of {@link VisitorID} objects
     */
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

    /**
     * Retrieves the Adobe Experience Cloud Visitor ID from the Adobe Experience Cloud ID Service.
     * <p>
     * The Adobe Experience Cloud ID (ECID) is generated at initial launch and is stored and used from that point forward.
     * This ID is preserved between app upgrades, is saved and restored during the standard application backup process,
     * and is removed at uninstall.
     *
     * @param promise {@link Promise} invoked with the Experience Cloud ID {@code String}
     */
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
