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
package com.adobe.marketing.mobile;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.HashMap;

public final class RCTACPIdentityDataBridge {

    // Visitor ID Auth State
    private static final String ACP_VISITOR_AUTH_STATE_AUTHENTICATED = "ACP_VISITOR_AUTH_STATE_AUTHENTICATED";
    private static final String ACP_VISITOR_AUTH_STATE_LOGGED_OUT = "ACP_VISITOR_AUTH_STATE_LOGGED_OUT";
    private static final String ACP_VISITOR_AUTH_STATE_UNKNOWN = "ACP_VISITOR_AUTH_STATE_UNKNOWN";

    public static HashMap<String, Object> getIdentityConstants() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(ACP_VISITOR_AUTH_STATE_AUTHENTICATED, ACP_VISITOR_AUTH_STATE_AUTHENTICATED);
        map.put(ACP_VISITOR_AUTH_STATE_LOGGED_OUT, ACP_VISITOR_AUTH_STATE_LOGGED_OUT);
        map.put(ACP_VISITOR_AUTH_STATE_UNKNOWN, ACP_VISITOR_AUTH_STATE_UNKNOWN);

        return map;
    }

    /**
     * Takes in a {@link String} and returns the associated enum {authenticated, logged_out, unknown}
     *
     * @param authStateString
     * @return The @{link VisitorID.AuthenticationState} authentication state
     */
    public static VisitorID.AuthenticationState authenticationStateFromString(final String authStateString) {
        if (authStateString.equals(ACP_VISITOR_AUTH_STATE_AUTHENTICATED)) {
            return VisitorID.AuthenticationState.AUTHENTICATED;
        } else if (authStateString.equals(ACP_VISITOR_AUTH_STATE_LOGGED_OUT)) {
            return VisitorID.AuthenticationState.LOGGED_OUT;
        }

        return VisitorID.AuthenticationState.UNKNOWN;
    }

    public static String stringFromAuthState(final VisitorID.AuthenticationState authenticationState) {
        if (authenticationState == VisitorID.AuthenticationState.AUTHENTICATED) {
            return ACP_VISITOR_AUTH_STATE_AUTHENTICATED;
        } else if (authenticationState == VisitorID.AuthenticationState.LOGGED_OUT) {
            return ACP_VISITOR_AUTH_STATE_LOGGED_OUT;
        }

        return ACP_VISITOR_AUTH_STATE_UNKNOWN;
    }

    public static VisitorID visitorIdentifierFromReadableMap(final ReadableMap map) {
        return new VisitorID(RCTACPCoreDataBridge.getNullableString(map, VISITOR_ID_ID_ORIGIN_KEY),
                RCTACPCoreDataBridge.getNullableString(map, VISITOR_ID_ID_TYPE_KEY),
                RCTACPCoreDataBridge.getNullableString(map, VISITOR_ID_ID_KEY),
                authenticationStateFromString(RCTACPCoreDataBridge.getNullableString(map, VISITOR_ID_AUTH_STATE_KEY)));
    }

    /**
     * Converts a {@link VisitorID} into a {@link WritableMap}
     * @param visitorID The visitorID object
     * @return A {@link WritableMap} that represents the visitorID
     */
    public static WritableMap mapFromVisitorIdentifier(final VisitorID visitorID) {
        WritableMap visitorIDMap = new WritableNativeMap();
        visitorIDMap.putString(VISITOR_ID_ID_ORIGIN_KEY, visitorID.getIdOrigin());
        visitorIDMap.putString(VISITOR_ID_ID_TYPE_KEY, visitorID.getIdType());
        visitorIDMap.putString(VISITOR_ID_ID_KEY, visitorID.getId());
        visitorIDMap.putString(VISITOR_ID_AUTH_STATE_KEY, stringFromAuthState(visitorID.getAuthenticationState()));

        return visitorIDMap;
    }

}
