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

#import "RCTACPIdentity.h"
#import "ACPIdentity.h"
#import "RCTACPIdentityDataBridge.h"

@implementation RCTACPIdentity

RCT_EXPORT_MODULE(ACPIdentity);

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(extensionVersion: (RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve([ACPIdentity extensionVersion]);
}

RCT_EXPORT_METHOD(registerExtension) {
    [ACPIdentity registerExtension];
}

RCT_EXPORT_METHOD(appendVisitorInfoForURL:(nonnull NSString*)baseUrl resolver:(RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [ACPIdentity appendToUrl:[NSURL URLWithString:baseUrl] withCallback:^(NSURL * _Nullable urlWithVisitorData) {
        resolve(urlWithVisitorData.absoluteString);
    }];
}

RCT_EXPORT_METHOD(getIdentifiers:(RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [ACPIdentity getIdentifiers:^(NSArray<ACPMobileVisitorId *> * _Nullable visitorIDs) {
        NSMutableArray *visitorIDArr = [NSMutableArray array];
        for (ACPMobileVisitorId* visitorID in visitorIDs) {
            [visitorIDArr addObject:[RCTACPIdentityDataBridge dictionaryFromVisitorId:visitorID]];
        }

        resolve(visitorIDArr);
    }];
}

RCT_EXPORT_METHOD(getExperienceCloudId:(RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [ACPIdentity getExperienceCloudId:^(NSString * _Nullable experienceCloudId) {
        resolve(experienceCloudId);
    }];
}

RCT_EXPORT_METHOD(syncIdentifier:(nonnull NSString*)identifierType
            identifier:(nonnull NSString*)identifier
                  authentication:(NSString *)authenticationState) {
    [ACPIdentity syncIdentifier:identifierType identifier:identifier authentication:[RCTACPIdentityDataBridge authStateFromString:authenticationState]];
}

RCT_EXPORT_METHOD(syncIdentifiers:(nullable NSDictionary*)identifiers) {
    [ACPIdentity syncIdentifiers:identifiers];
}

RCT_EXPORT_METHOD(syncIdentifiersWithAuthState:(nullable NSDictionary*)identifiers
                  authentication:(NSString *)authenticationState) {
    [ACPIdentity syncIdentifiers:identifiers authentication:[RCTACPIdentityDataBridge authStateFromString:authenticationState]];
}

@end
