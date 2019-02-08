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

import com.adobe.marketing.mobile.VisitorID;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

public final class RCTACPIdentityDataBridge {

    // Visitor ID Auth State
    private static final String ACP_VISITOR_AUTH_STATE_AUTHENTICATED = "ACP_VISITOR_AUTH_STATE_AUTHENTICATED";
    private static final String ACP_VISITOR_AUTH_STATE_LOGGED_OUT = "ACP_VISITOR_AUTH_STATE_LOGGED_OUT";
    private static final String ACP_VISITOR_AUTH_STATE_UNKNOWN = "ACP_VISITOR_AUTH_STATE_UNKNOWN";

    // Visitor ID
    private static final String ACP_VISITOR_ID_ORIGIN = "idOrigin";
    private static final String ACP_VISITOR_ID_TYPE = "idType";
    private static final String ACP_VISITOR_IDENTIFIER = "identifier";
    private static final String ACP_VISITOR_AUTH_STATE = "authenticationState";

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
        return new VisitorID(RCTACPCoreDataBridge.getNullableString(map, ACP_VISITOR_ID_ORIGIN),
                RCTACPCoreDataBridge.getNullableString(map, ACP_VISITOR_ID_TYPE),
                RCTACPCoreDataBridge.getNullableString(map, ACP_VISITOR_IDENTIFIER),
                authenticationStateFromString(RCTACPCoreDataBridge.getNullableString(map, ACP_VISITOR_AUTH_STATE)));
    }

    /**
     * Converts a {@link VisitorID} into a {@link WritableMap}
     * @param visitorID The visitorID object
     * @return A {@link WritableMap} that represents the visitorID
     */
    public static WritableMap mapFromVisitorIdentifier(final VisitorID visitorID) {
        WritableMap visitorIDMap = new WritableNativeMap();
        visitorIDMap.putString(ACP_VISITOR_ID_ORIGIN, visitorID.getIdOrigin());
        visitorIDMap.putString(ACP_VISITOR_ID_TYPE, visitorID.getIdType());
        visitorIDMap.putString(ACP_VISITOR_IDENTIFIER, visitorID.getId());
        visitorIDMap.putString(ACP_VISITOR_AUTH_STATE, stringFromAuthState(visitorID.getAuthenticationState()));

        return visitorIDMap;
    }

}
