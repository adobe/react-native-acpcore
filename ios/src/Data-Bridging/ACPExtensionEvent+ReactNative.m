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

    if (name && type && source && ([[dict objectForKey:EVENT_DATA_KEY] isKindOfClass:[NSDictionary class]] || ![dict objectForKey:EVENT_DATA_KEY])) {
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
