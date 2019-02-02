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

#import "ACPExtensionEvent+ReactNative.h"

@implementation ACPExtensionEvent (ReactNative)

static NSString* const EVENT_NAME_KEY = @"eventName";
static NSString* const EVENT_TYPE_KEY = @"eventType";
static NSString* const EVENT_SOURCE_KEY = @"eventSource";
static NSString* const EVENT_DATA_KEY = @"eventData";

+ (ACPExtensionEvent *)eventFromDictionary: (nonnull NSDictionary *) dict {
    NSString *name = [dict objectForKey:EVENT_NAME_KEY];
    NSString *type = [dict objectForKey:EVENT_TYPE_KEY];
    NSString *source = [dict objectForKey:EVENT_SOURCE_KEY];
    
    if (name && type && source && [[dict objectForKey:EVENT_DATA_KEY] isKindOfClass:[NSDictionary class]]) {
        return [ACPExtensionEvent extensionEventWithName:name type:type source:source data:[dict objectForKey:EVENT_DATA_KEY] error:nil];
    }
    
    return nil;
}

+ (NSDictionary *)dictionaryFromEvent: (nonnull ACPExtensionEvent *) event {
    NSMutableDictionary *eventDict = [NSMutableDictionary dictionary];
    eventDict[EVENT_NAME_KEY] = event.eventName;
    eventDict[EVENT_TYPE_KEY] = event.eventType;
    eventDict[EVENT_SOURCE_KEY] = event.eventSource;
    eventDict[EVENT_DATA_KEY] = event.eventData;
    
    return eventDict;
}

@end
