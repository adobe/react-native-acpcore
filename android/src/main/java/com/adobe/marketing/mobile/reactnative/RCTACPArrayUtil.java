/* ***********************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2019 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
package com.adobe.marketing.mobile.reactnative;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;

import java.util.Map;

public class RCTACPArrayUtil {

    public static Object[] toObjectArray(ReadableArray readableArray) {
        Object[] array = new Object[readableArray.size()];

        for (int i = 0; i < readableArray.size(); i++) {
            ReadableType type = readableArray.getType(i);

            switch (type) {
                case Null:
                    array[i] = null;
                    break;
                case Boolean:
                    array[i] = readableArray.getBoolean(i);
                    break;
                case Number:
                    array[i] = readableArray.getDouble(i);
                    break;
                case String:
                    array[i] = readableArray.getString(i);
                    break;
                case Map:
                    array[i] = RCTACPMapUtil.toMap(readableArray.getMap(i));
                    break;
                case Array:
                    array[i] = RCTACPArrayUtil.toObjectArray(readableArray.getArray(i));
                    break;
            }
        }

        return array;
    }

    public static WritableArray toWritableArray(Object[] array) {
        WritableArray writableArr = Arguments.createArray();

        for (int i = 0; i < array.length; i++) {
            Object value = array[i];

            if (value == null) {
                writableArr.pushNull();
            } else if (value instanceof Boolean) {
                writableArr.pushBoolean((Boolean) value);
            } else if (value instanceof Double) {
                writableArr.pushDouble((Double) value);
            } else if (value instanceof Integer) {
                writableArr.pushInt((Integer) value);
            } else if (value instanceof String) {
                writableArr.pushString((String) value);
            } else if (value instanceof Map) {
                writableArr.pushMap(RCTACPMapUtil.toWritableMap((Map<String, Object>) value));
            } else if (value.getClass().isArray()) {
                writableArr.pushArray(RCTACPArrayUtil.toWritableArray((Object[]) value));
            }
        }

        return writableArr;
    }
    
}
