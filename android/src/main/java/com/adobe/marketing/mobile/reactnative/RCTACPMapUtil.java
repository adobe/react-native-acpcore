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
package com.adobe.marketing.mobile.reactnative;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RCTACPMapUtil {

    public static Map<String, Object> toMap(ReadableMap readableMap) {
        if (readableMap == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType type = readableMap.getType(key);

            switch (type) {
                case Null:
                    map.put(key, null);
                    break;
                case Boolean:
                    map.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    map.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    map.put(key, readableMap.getString(key));
                    break;
                case Map:
                    map.put(key, RCTACPMapUtil.toMap(readableMap.getMap(key)));
                    break;
                case Array:
                    map.put(key, RCTACPArrayUtil.toObjectArray(readableMap.getArray(key)));
                    break;
            }
        }

        return map;
    }

    public static Map<String, String> toStringMap(ReadableMap readableMap) {
        if (readableMap == null) {
            return null;
        }

        Map<String, String> map = new HashMap<>();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType type = readableMap.getType(key);

            switch (type) {
                case Null:
                    map.put(key, null);
                    break;
                case String:
                    map.put(key, readableMap.getString(key));
                    break;
            }
        }

        return map;
    }

    public static WritableMap toWritableMap(Map<String, Object> map) {
        if (map == null) {
            return null;
        }

        WritableMap writableMap = Arguments.createMap();
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Object value = pair.getValue();

            if (value == null) {
                writableMap.putNull((String) pair.getKey());
            } else if (value instanceof Boolean) {
                writableMap.putBoolean((String) pair.getKey(), (Boolean) value);
            } else if (value instanceof Double) {
                writableMap.putDouble((String) pair.getKey(), (Double) value);
            } else if (value instanceof Integer) {
                writableMap.putInt((String) pair.getKey(), (Integer) value);
            } else if (value instanceof String) {
                writableMap.putString((String) pair.getKey(), (String) value);
            } else if (value instanceof Map) {
                writableMap.putMap((String) pair.getKey(), RCTACPMapUtil.toWritableMap((Map<String, Object>) value));
            } else if (value.getClass() != null && value.getClass().isArray()) {
                writableMap.putArray((String) pair.getKey(), RCTACPArrayUtil.toWritableArray((Object[]) value));
            }

            iterator.remove();
        }

        return writableMap;
    }

}
