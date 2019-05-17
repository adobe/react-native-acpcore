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

#import "RCTACPCoreDataBridge.h"

@implementation RCTACPCoreDataBridge

// Logging mode
static NSString* const ACP_LOG_LEVEL_ERROR = @"ACP_LOG_LEVEL_ERROR";
static NSString* const ACP_LOG_LEVEL_WARNING = @"ACP_LOG_LEVEL_WARNING";
static NSString* const ACP_LOG_LEVEL_DEBUG = @"ACP_LOG_LEVEL_DEBUG";
static NSString* const ACP_LOG_LEVEL_VERBOSE = @"ACP_LOG_LEVEL_VERBOSE";

// Privacy Status
static NSString* const ACP_PRIVACY_STATUS_OPT_IN = @"ACP_PRIVACY_STATUS_OPT_IN";
static NSString* const ACP_PRIVACY_STATUS_OPT_OUT = @"ACP_PRIVACY_STATUS_OPT_OUT";
static NSString* const ACP_PRIVACY_STATUS_UNKNOWN = @"ACP_PRIVACY_STATUS_UNKNOWN";

+ (ACPMobilePrivacyStatus)privacyStatusFromString: (NSString *) statusString {
    if ([statusString isEqualToString:ACP_PRIVACY_STATUS_OPT_IN]) {
        return ACPMobilePrivacyStatusOptIn;
    } else if ([statusString isEqualToString:ACP_PRIVACY_STATUS_OPT_OUT]) {
        return ACPMobilePrivacyStatusOptOut;
    }

    return ACPMobilePrivacyStatusUnknown;
}

+ (ACPMobileLogLevel) logLevelFromString: (NSString *) logLevelString {
    if ([logLevelString isEqualToString:ACP_LOG_LEVEL_ERROR]) {
        return ACPMobileLogLevelError;
    } else if ([logLevelString isEqualToString:ACP_LOG_LEVEL_WARNING]) {
        return ACPMobileLogLevelWarning;
    } else if ([logLevelString isEqualToString:ACP_LOG_LEVEL_DEBUG]) {
        return ACPMobileLogLevelDebug;
    } else if ([logLevelString isEqualToString:ACP_LOG_LEVEL_VERBOSE]) {
        return ACPMobileLogLevelVerbose;
    }

    return ACPMobileLogLevelDebug;
}

+ (NSString *)stringFromPrivacyStatus: (ACPMobilePrivacyStatus) status {
    switch (status) {
        case ACPMobilePrivacyStatusOptIn:
            return ACP_PRIVACY_STATUS_OPT_IN;
            break;
        case ACPMobilePrivacyStatusOptOut:
            return ACP_PRIVACY_STATUS_OPT_OUT;
            break;
        case ACPMobilePrivacyStatusUnknown:
            return ACP_PRIVACY_STATUS_UNKNOWN;
            break;
    }
}

+ (NSString *)stringFromLogLevel: (ACPMobileLogLevel) logLevel {
    switch (logLevel) {
        case ACPMobileLogLevelError:
            return ACP_LOG_LEVEL_ERROR;
        case ACPMobileLogLevelWarning:
            return ACP_LOG_LEVEL_WARNING;
        case ACPMobileLogLevelDebug:
            return ACP_LOG_LEVEL_DEBUG;
        case ACPMobileLogLevelVerbose:
            return ACP_LOG_LEVEL_VERBOSE;
    }
}

+ (NSDictionary *)sanitizeDictionaryToContainClass: (Class) type WithDictionary:(NSDictionary *)dict {
    NSMutableDictionary *sanitizedDict = [NSMutableDictionary dictionary];

    for (id key in dict) {
        id obj = [dict objectForKey:key];
        if ([key isKindOfClass:type] && [obj isKindOfClass:type]) {
            sanitizedDict[key] = obj;
        }
    }

    return sanitizedDict;
}

@end
