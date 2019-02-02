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
import com.facebook.react.bridge.WritableNativeMap;

import java.util.HashMap;

public final class RCTACPCoreDataBridge {

    // @{link LoggingMode}
    public final static String ACP_LOG_LEVEL_ERROR = "ACP_LOG_LEVEL_ERROR";
    public final static String ACP_LOG_LEVEL_WARNING = "ACP_LOG_LEVEL_WARNING";
    public final static String ACP_LOG_LEVEL_DEBUG = "ACP_LOG_LEVEL_DEBUG";
    public final static String ACP_LOG_LEVEL_VERBOSE = "ACP_LOG_LEVEL_VERBOSE";

    // @{link @VisitorID.AuthenticationState}
    public final static String ACP_PRIVACY_STATUS_OPT_IN = "ACP_PRIVACY_STATUS_OPT_IN";
    public final static String ACP_PRIVACY_STATUS_OPT_OUT = "ACP_PRIVACY_STATUS_OPT_OUT";
    public final static String ACP_PRIVACY_STATUS_UNKNOWN = "ACP_PRIVACY_STATUS_UNKNOWN";

    // Event Object Keys
    public final static String EVENT_NAME_KEY = "eventName";
    public final static String EVENT_TYPE_KEY = "eventType";
    public final static String EVENT_SOURCE_KEY = "eventSource";
    public final static String EVENT_DATA_KEY = "eventData";


    public static HashMap<String, Object> getCoreConstants() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(ACP_LOG_LEVEL_ERROR, ACP_LOG_LEVEL_ERROR);
        map.put(ACP_LOG_LEVEL_WARNING, ACP_LOG_LEVEL_WARNING);
        map.put(ACP_LOG_LEVEL_DEBUG, ACP_LOG_LEVEL_DEBUG);
        map.put(ACP_LOG_LEVEL_VERBOSE, ACP_LOG_LEVEL_VERBOSE);

        map.put(ACP_PRIVACY_STATUS_OPT_IN, ACP_PRIVACY_STATUS_OPT_IN);
        map.put(ACP_PRIVACY_STATUS_OPT_OUT, ACP_PRIVACY_STATUS_OPT_OUT);
        map.put(ACP_PRIVACY_STATUS_UNKNOWN, ACP_PRIVACY_STATUS_UNKNOWN);

        return map;
    }

    /**
     * Converts a {@link ReadableMap} into an {@link Event}
     *
     * @param map
     * @return An {@link Event}
     */
    public static Event eventFromReadableMap(final ReadableMap map) {
        Event event = new Event.Builder(getNullableString(map, EVENT_NAME_KEY), getNullableString(map, EVENT_TYPE_KEY), getNullableString(map, EVENT_SOURCE_KEY))
                .setEventData(RCTACPMapUtil.toMap(map.getMap(EVENT_DATA_KEY)))
                .build();
        return event;
    }

    public static ReadableMap readableMapFromEvent(final Event event) {
        WritableNativeMap map = new WritableNativeMap();
        map.putString(EVENT_NAME_KEY, event.getName());
        map.putString(EVENT_TYPE_KEY, event.getType());
        map.putString(EVENT_SOURCE_KEY, event.getSource());
        map.putMap(EVENT_DATA_KEY, RCTACPMapUtil.toWritableMap(event.getEventData()));
        return map;
    }

    /**
     * Takes in a {@link String} and returns the associated enum {error, warning, debug, verbose}
     *
     * @param logModeString
     * @return The @{link LoggingMode} associated with logModeString
     */
    public static LoggingMode loggingModeFromString(final String logModeString) {
        if (logModeString.equals(ACP_LOG_LEVEL_ERROR)) {
            return LoggingMode.ERROR;
        } else if (logModeString.equals(ACP_LOG_LEVEL_WARNING)) {
            return LoggingMode.WARNING;
        } else if (logModeString.equals(ACP_LOG_LEVEL_DEBUG)) {
            return LoggingMode.DEBUG;
        } else if (logModeString.equals(ACP_LOG_LEVEL_VERBOSE)) {
            return LoggingMode.VERBOSE;
        }

        return null;
    }


    public static MobilePrivacyStatus privacyStatusFromString(final String privacyStatusString) {
        if (privacyStatusString.equals(ACP_PRIVACY_STATUS_OPT_IN)) {
            return MobilePrivacyStatus.OPT_IN;
        } else if (privacyStatusString.equals(ACP_PRIVACY_STATUS_OPT_OUT)) {
            return MobilePrivacyStatus.OPT_OUT;
        }

        return MobilePrivacyStatus.UNKNOWN;
    }

    public static String stringFromPrivacyStatus(final MobilePrivacyStatus privacyStatus) {
        if (privacyStatus == MobilePrivacyStatus.OPT_IN) {
            return ACP_PRIVACY_STATUS_OPT_IN;
        } else if (privacyStatus == MobilePrivacyStatus.OPT_OUT) {
            return ACP_PRIVACY_STATUS_OPT_OUT;
        }

        return ACP_PRIVACY_STATUS_UNKNOWN;
    }

    // Helper methods

    public static String getNullableString(final ReadableMap data, final String key) {
        return data.hasKey(key) ? data.getString(key) : null;
    }

}
