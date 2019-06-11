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

RCT_EXPORT_METHOD(getUrlVariables:(RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [ACPIdentity getUrlVariables:^(NSString * _Nullable urlVariables) {
        resolve(urlVariables);
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
