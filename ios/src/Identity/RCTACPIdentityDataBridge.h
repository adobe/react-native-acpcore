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

#import <Foundation/Foundation.h>
#import "ACPCore.h"

@interface RCTACPIdentityDataBridge : NSObject

+ (NSDictionary *) getIdentityConstants;

+ (ACPMobileVisitorAuthenticationState) authStateFromString: (NSString *) authStateString;

+ (NSString *)stringFromAuthState: (ACPMobileVisitorAuthenticationState) authState;

+ (NSDictionary *)dictionaryFromVisitorId: (ACPMobileVisitorId *) visitorId;


@end

