package com.kavi.database.util;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class Util {

    public static int nullSafeCompare(final Comparable obj1, final Comparable obj2) {
        if (obj1 == null ^ obj2 == null) {
            return obj1 == null ? -1 : 1;
        }
        if (obj1 == null && obj2 == null) {
            return 0;
        }

        return obj1.compareTo(obj2);
    }

    public static Comparable convertToType(Class type, String value) throws Exception {
        if (value == null) {
            return null;
        }
        if (type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == String.class) {
            return value;
        } else if (type == Double.class) {
            return Double.parseDouble(value);
        } else {
            throw new Exception("Unknown data type - " + type.getCanonicalName());
        }
    }
}
