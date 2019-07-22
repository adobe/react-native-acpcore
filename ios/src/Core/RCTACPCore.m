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

#import <React/RCTConvert.h>
#import "RCTACPCore.h"
#import "ACPCore.h"
#import "ACPExtensionEvent+ReactNative.h"
#import "RCTACPCoreDataBridge.h"

@implementation RCTACPCore

RCT_EXPORT_MODULE(ACPCore);

static NSString* const EXTENSION_NAME = @"ACPCore";
static NSString* const FAILED_TO_CONVERT_EVENT_MESSAGE = @"Failed to convert dictionary to Event";

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

+ (void) initialize {
    [super initialize];
    [ACPCore setWrapperType:ACPMobileWrapperTypeReactNative];
}

- (NSData *)dataFromHexString:(NSString *)string {
    NSMutableData *result = [[NSMutableData alloc] init];

    for (int i = 0; i + 2 <= string.length; i += 2) {
        NSRange range = NSMakeRange(i, 2);
        NSString* hexStr = [string substringWithRange:range];
        NSScanner* scanner = [NSScanner scannerWithString:hexStr];
        unsigned int intValue;
        [scanner scanHexInt:&intValue];
        unsigned char uc = (unsigned char) intValue;
        [result appendBytes:&uc length:1];
    }
    
    return [NSData dataWithData:result];
}

RCT_EXPORT_METHOD(extensionVersion: (RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve([ACPCore extensionVersion]);
}

RCT_EXPORT_METHOD(start: (RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [ACPCore start:^{
        resolve(@(YES));
    }];
}

RCT_EXPORT_METHOD(configureWithAppId:(NSString* __nullable) appId) {
    [ACPCore configureWithAppId:appId];
}

RCT_EXPORT_METHOD(updateConfiguration: (NSDictionary* __nullable) config) {
    [ACPCore updateConfiguration:config];
}

RCT_EXPORT_METHOD(setLogLevel: (NSString *) logLevelString) {
    [ACPCore setLogLevel:[RCTACPCoreDataBridge logLevelFromString:logLevelString]];
}

RCT_EXPORT_METHOD(getLogLevel: (RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *logLevelString = [RCTACPCoreDataBridge stringFromLogLevel:[ACPCore logLevel]];
    resolve(logLevelString);
}

RCT_EXPORT_METHOD(log: (NSString *) logLevel tag: (nonnull NSString*) tag message: (nonnull NSString*) message) {
    ACPMobileLogLevel logLevelType = [RCTACPCoreDataBridge logLevelFromString:logLevel];
    [ACPCore log:logLevelType tag:tag message:message];
}

RCT_EXPORT_METHOD(getPrivacyStatus: (RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [ACPCore getPrivacyStatus:^(ACPMobilePrivacyStatus status) {
        resolve([RCTACPCoreDataBridge stringFromPrivacyStatus:status]);
    }];
}

RCT_EXPORT_METHOD(setPrivacyStatus: (NSString *) statusString) {
    [ACPCore setPrivacyStatus:[RCTACPCoreDataBridge privacyStatusFromString:statusString]];
}

RCT_EXPORT_METHOD(getSdkIdentities: (RCTPromiseResolveBlock) resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [ACPCore getSdkIdentities:^(NSString * _Nullable content) {
        resolve(content);
    }];
}

RCT_EXPORT_METHOD(setAppGroup: (nullable NSString*) appGroup) {
    [ACPCore setAppGroup:appGroup];
}

#pragma mark - Generic methods

RCT_EXPORT_METHOD(collectPii: (nonnull NSDictionary*) data) {
    [ACPCore collectPii:[RCTACPCoreDataBridge sanitizeDictionaryToContainClass:[NSString class] WithDictionary:data]];
}

RCT_EXPORT_METHOD(lifecyclePause) {
    [ACPCore lifecyclePause];
}

RCT_EXPORT_METHOD(lifecycleStart: (nullable NSDictionary*) additionalContextData) {
    [ACPCore lifecycleStart:[RCTACPCoreDataBridge sanitizeDictionaryToContainClass:[NSString class] WithDictionary:additionalContextData]];
}

RCT_EXPORT_METHOD(setAdvertisingIdentifier: (nullable NSString*) adId) {
    [ACPCore setAdvertisingIdentifier:adId];
}

RCT_EXPORT_METHOD(setPushIdentifier: (nullable NSString*) deviceToken) {
    [ACPCore setPushIdentifier:[self dataFromHexString:deviceToken]];
}

RCT_EXPORT_METHOD(trackAction: (nullable NSString*) action data: (nullable NSDictionary*) data) {
    [ACPCore trackAction:action data:data];
}

RCT_EXPORT_METHOD(trackState: (nullable NSString*) state data: (nullable NSDictionary*) data) {
    [ACPCore trackState:state data:data];
}

RCT_EXPORT_METHOD(dispatchEvent: (nonnull NSDictionary*) eventDict resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    ACPExtensionEvent *event = [ACPExtensionEvent eventFromDictionary:eventDict];
    if (!event) {
        reject(EXTENSION_NAME, FAILED_TO_CONVERT_EVENT_MESSAGE, nil);
        return;
    }

    NSError *error = nil;
    [ACPCore dispatchEvent:event error:&error];
    if (!error) {
        resolve(@(YES));
    } else {
        [self handleError:error rejecter:reject];
    }
}

RCT_EXPORT_METHOD(dispatchEventWithResponseCallback: (nonnull NSDictionary*) requestEventDict resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    ACPExtensionEvent *requestEvent = [ACPExtensionEvent eventFromDictionary:requestEventDict];
    if (!requestEvent) {
        reject(EXTENSION_NAME, FAILED_TO_CONVERT_EVENT_MESSAGE, nil);
        return;
    }

    NSError *error = nil;
    [ACPCore dispatchEventWithResponseCallback:requestEvent responseCallback:^(ACPExtensionEvent * _Nonnull responseEvent) {
        resolve([ACPExtensionEvent dictionaryFromEvent:responseEvent]);
    } error:&error];

    if (error) {
        [self handleError:error rejecter:reject];
    }
}

RCT_EXPORT_METHOD(dispatchResponseEvent: (nonnull NSDictionary*) responseEventDict
                  requestEvent: (nonnull NSDictionary*) requestEventDict
                   resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    ACPExtensionEvent *responseEvent = [ACPExtensionEvent eventFromDictionary:responseEventDict];
    ACPExtensionEvent *requestEvent = [ACPExtensionEvent eventFromDictionary:requestEventDict];
    NSError *error = nil;

    if (responseEvent && requestEvent) {
        [ACPCore dispatchResponseEvent:responseEvent requestEvent:requestEvent error:&error];

        if (error) {
            [self handleError:error rejecter:reject];
        } else {
            resolve(@(YES));
        }

    } else {
        reject(EXTENSION_NAME, FAILED_TO_CONVERT_EVENT_MESSAGE, nil);
    }
}

RCT_EXPORT_METHOD(collectLaunchInfo: (nonnull NSDictionary*) userInfo) {
    [ACPCore collectLaunchInfo:userInfo];
}

RCT_EXPORT_METHOD(setSmallIconResourceID: (NSInteger) resourceID) {
    [ACPCore log:ACPMobileLogLevelDebug tag:EXTENSION_NAME message:@"setSmallIconResourceID is not suppported on iOS"];
}

RCT_EXPORT_METHOD(setLargeIconResourceID: (NSInteger) resourceID) {
    [ACPCore log:ACPMobileLogLevelDebug tag:EXTENSION_NAME message:@"setLargeIconResourceID is not suppported on iOS"];
}

#pragma mark - Rules Engine

RCT_EXPORT_METHOD(downloadRules) {
    [ACPCore downloadRules];
}

#pragma mark - Helper methods

- (void) handleError:(NSError *) error rejecter:(RCTPromiseRejectBlock) reject {
    if (!error || !reject) {
        return;
    }

    NSDictionary *userInfo = [error userInfo];
    NSString *errorString = [[userInfo objectForKey:NSUnderlyingErrorKey] localizedDescription];

    reject([NSString stringWithFormat: @"%lu", (long)error.code],
           errorString,
           error);
}

@end
