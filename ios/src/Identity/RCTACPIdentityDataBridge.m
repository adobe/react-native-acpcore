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

#import "RCTACPIdentityDataBridge.h"

// Visitor ID Auth State
static NSString* const ACP_VISITOR_AUTH_STATE_AUTHENTICATED = @"ACP_VISITOR_AUTH_STATE_AUTHENTICATED";
static NSString* const ACP_VISITOR_AUTH_STATE_LOGGED_OUT = @"ACP_VISITOR_AUTH_STATE_LOGGED_OUT";
static NSString* const ACP_VISITOR_AUTH_STATE_UNKNOWN = @"ACP_VISITOR_AUTH_STATE_UNKNOWN";

// Visitor ID
static NSString* const VISITOR_ID_ID_ORIGIN_KEY = @"idOrigin";
static NSString* const VISITOR_ID_ID_TYPE_KEY = @"idType";
static NSString* const VISITOR_ID_ID_KEY = @"identifier";
static NSString* const VISITOR_ID_AUTH_STATE_KEY = @"authenticationState";

@implementation RCTACPIdentityDataBridge

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
