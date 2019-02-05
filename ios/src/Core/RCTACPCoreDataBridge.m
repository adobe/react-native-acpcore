/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2019 Adobe
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of Adobe and its suppliers, if any. The intellectual
 * and technical concepts contained herein are proprietary to Adobe
 * and its suppliers and are protected by all applicable intellectual
 * property laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe.
 **************************************************************************/

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

+ (NSDictionary *)getCoreConstants {
    return @{ ACP_LOG_LEVEL_ERROR: ACP_LOG_LEVEL_ERROR,
              ACP_LOG_LEVEL_WARNING: ACP_LOG_LEVEL_WARNING,
              ACP_LOG_LEVEL_DEBUG: ACP_LOG_LEVEL_DEBUG,
              ACP_LOG_LEVEL_VERBOSE: ACP_LOG_LEVEL_VERBOSE,
              ACP_PRIVACY_STATUS_OPT_IN: ACP_PRIVACY_STATUS_OPT_IN,
              ACP_PRIVACY_STATUS_OPT_OUT: ACP_PRIVACY_STATUS_OPT_OUT,
              ACP_PRIVACY_STATUS_UNKNOWN: ACP_PRIVACY_STATUS_UNKNOWN };
}

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

+ (NSDictionary *)sanitizeDictionaryToContainClass: (Class) type WithDictionary:(NSDictionary *)dict {
    NSMutableDictionary *sanitizedDict = [NSMutableDictionary dictionary];
    
    for(id key in dict) {
        id obj = [dict objectForKey:key];
        if ([key isKindOfClass:type] && [obj isKindOfClass:type]) {
            sanitizedDict[key] = obj;
        }
    }
    
    return sanitizedDict;
}

@end
