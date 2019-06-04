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
        if (authStateString == null) {
            return VisitorID.AuthenticationState.UNKNOWN;
        }

        if (authStateString.equals(ACP_VISITOR_AUTH_STATE_AUTHENTICATED)) {
            return VisitorID.AuthenticationState.AUTHENTICATED;
        } else if (authStateString.equals(ACP_VISITOR_AUTH_STATE_LOGGED_OUT)) {
            return VisitorID.AuthenticationState.LOGGED_OUT;
        }

        return VisitorID.AuthenticationState.UNKNOWN;
    }

    public static String stringFromAuthState(final VisitorID.AuthenticationState authenticationState) {
        if (authenticationState == null) {
            return ACP_VISITOR_AUTH_STATE_UNKNOWN;
        }

        if (authenticationState == VisitorID.AuthenticationState.AUTHENTICATED) {
            return ACP_VISITOR_AUTH_STATE_AUTHENTICATED;
        } else if (authenticationState == VisitorID.AuthenticationState.LOGGED_OUT) {
            return ACP_VISITOR_AUTH_STATE_LOGGED_OUT;
        }

        return ACP_VISITOR_AUTH_STATE_UNKNOWN;
    }

    public static VisitorID visitorIdentifierFromReadableMap(final ReadableMap map) {
        if (map == null) {
            return null;
        }

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
        if (visitorID == null) {
            return null;
        }

        WritableMap visitorIDMap = new WritableNativeMap();
        visitorIDMap.putString(ACP_VISITOR_ID_ORIGIN, visitorID.getIdOrigin());
        visitorIDMap.putString(ACP_VISITOR_ID_TYPE, visitorID.getIdType());
        visitorIDMap.putString(ACP_VISITOR_IDENTIFIER, visitorID.getId());
        visitorIDMap.putString(ACP_VISITOR_AUTH_STATE, stringFromAuthState(visitorID.getAuthenticationState()));

        return visitorIDMap;
    }

}
