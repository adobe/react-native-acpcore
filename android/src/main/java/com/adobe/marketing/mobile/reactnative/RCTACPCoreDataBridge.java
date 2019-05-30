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

import com.adobe.marketing.mobile.Event;
import com.adobe.marketing.mobile.LoggingMode;
import com.adobe.marketing.mobile.MobilePrivacyStatus;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;

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

    /**
     * Converts a {@link ReadableMap} into an {@link Event}
     *
     * @param map
     * @return An {@link Event}
     */
    public static Event eventFromReadableMap(final ReadableMap map) {
        if (map == null) {
            return null;
        }

        Event event = new Event.Builder(getNullableString(map, EVENT_NAME_KEY), getNullableString(map, EVENT_TYPE_KEY), getNullableString(map, EVENT_SOURCE_KEY))
                .setEventData(RCTACPMapUtil.toMap(map.getMap(EVENT_DATA_KEY)))
                .build();
        return event;
    }

    public static ReadableMap readableMapFromEvent(final Event event) {
        if (event == null) {
            return null;
        }

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
        if (logModeString == null) {
            return LoggingMode.DEBUG;
        }

        if (logModeString.equals(ACP_LOG_LEVEL_ERROR)) {
            return LoggingMode.ERROR;
        } else if (logModeString.equals(ACP_LOG_LEVEL_WARNING)) {
            return LoggingMode.WARNING;
        } else if (logModeString.equals(ACP_LOG_LEVEL_DEBUG)) {
            return LoggingMode.DEBUG;
        } else if (logModeString.equals(ACP_LOG_LEVEL_VERBOSE)) {
            return LoggingMode.VERBOSE;
        }

        return LoggingMode.DEBUG;
    }

    public static String stringFromLoggingMode(final LoggingMode logMode) {
        if (logMode == null) {
            return ACP_LOG_LEVEL_DEBUG;
        }

        switch (logMode) {
            case ERROR:
                return  ACP_LOG_LEVEL_ERROR;
            case WARNING:
                return  ACP_LOG_LEVEL_WARNING;
            case DEBUG:
                return  ACP_LOG_LEVEL_DEBUG;
            case VERBOSE:
                return  ACP_LOG_LEVEL_VERBOSE;
        }

        return ACP_LOG_LEVEL_DEBUG;
    }

    public static MobilePrivacyStatus privacyStatusFromString(final String privacyStatusString) {
        if (privacyStatusString == null) {
            return MobilePrivacyStatus.UNKNOWN;
        }

        if (privacyStatusString.equals(ACP_PRIVACY_STATUS_OPT_IN)) {
            return MobilePrivacyStatus.OPT_IN;
        } else if (privacyStatusString.equals(ACP_PRIVACY_STATUS_OPT_OUT)) {
            return MobilePrivacyStatus.OPT_OUT;
        }

        return MobilePrivacyStatus.UNKNOWN;
    }

    public static String stringFromPrivacyStatus(final MobilePrivacyStatus privacyStatus) {
        if (privacyStatus == null) {
            return ACP_PRIVACY_STATUS_UNKNOWN;
        }

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
