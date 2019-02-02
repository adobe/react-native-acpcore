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

#import "RCTACPIdentityDataBridge.h"

// Visitor ID Auth State
static NSString* const ACP_VISITOR_AUTH_STATE_AUTHENTICATED = @"ACP_VISITOR_AUTH_STATE_AUTHENTICATED";
static NSString* const ACP_VISITOR_AUTH_STATE_LOGGED_OUT = @"ACP_VISITOR_AUTH_STATE_LOGGED_OUT";
static NSString* const ACP_VISITOR_AUTH_STATE_UNKNOWN = @"ACP_VISITOR_AUTH_STATE_UNKNOWN";

// Visitor ID
static NSString* const VISITOR_ID_ID_ORIGIN_KEY = @"idOrigin";
static NSString* const VISITOR_ID_ID_TYPE_KEY = @"idType";
static NSString* const VISITOR_ID_ID_KEY = @"id";
static NSString* const VISITOR_ID_AUTH_STATE_KEY = @"authenticationState";

@implementation RCTACPIdentityDataBridge

+ (NSDictionary *) getIdentityConstants {
    return @{ ACP_VISITOR_AUTH_STATE_AUTHENTICATED: ACP_VISITOR_AUTH_STATE_AUTHENTICATED,
              ACP_VISITOR_AUTH_STATE_LOGGED_OUT: ACP_VISITOR_AUTH_STATE_LOGGED_OUT,
              ACP_VISITOR_AUTH_STATE_UNKNOWN: ACP_VISITOR_AUTH_STATE_UNKNOWN};
}

+ (NSDictionary *)dictionaryFromVisitorId: (ACPMobileVisitorId *) visitorId {
    NSMutableDictionary *visitorIdDict = [NSMutableDictionary dictionary];
    visitorIdDict[VISITOR_ID_ID_ORIGIN_KEY] = visitorId.idOrigin;
    visitorIdDict[VISITOR_ID_ID_TYPE_KEY] = visitorId.idType;
    visitorIdDict[VISITOR_ID_ID_KEY] = visitorId.identifier;
    visitorIdDict[VISITOR_ID_AUTH_STATE_KEY] = [RCTACPIdentityDataBridge stringFromAuthState:visitorId.authenticationState];
    
    return visitorIdDict;
}

+ (NSString *)stringFromAuthState: (ACPMobileVisitorAuthenticationState) authState {
    switch (authState) {
        case ACPMobileVisitorAuthenticationStateAuthenticated:
            return ACP_VISITOR_AUTH_STATE_AUTHENTICATED;
        case ACPMobileVisitorAuthenticationStateLoggedOut:
            return ACP_VISITOR_AUTH_STATE_LOGGED_OUT;
        default:
            return ACP_VISITOR_AUTH_STATE_UNKNOWN;
    }
}

+ (ACPMobileVisitorAuthenticationState) authStateFromString: (NSString *) authStateString {
    if ([authStateString isEqualToString:ACP_VISITOR_AUTH_STATE_AUTHENTICATED]) {
        return ACPMobileVisitorAuthenticationStateAuthenticated;
    } else if ([authStateString isEqualToString:ACP_VISITOR_AUTH_STATE_LOGGED_OUT]) {
        return ACPMobileVisitorAuthenticationStateLoggedOut;
    }
    
    return ACPMobileVisitorAuthenticationStateUnknown;
}


@end
