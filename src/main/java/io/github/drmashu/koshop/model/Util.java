package io.github.drmashu.koshop.model;

import java.lang.reflect.Method;

/**
 * Created by drmashu on 2015/10/23.
 */
class Util {
    public static <E extends JsonEnumInt> E getEnumFromValue(int value, Class<E> clazz) {
        try {
            Method method = clazz.getMethod("values");
            @SuppressWarnings("unchecked")
            E[] array = (E[]) method.invoke(null);
            for (E e : array) {
                if (e.getValue() == value) {
                    return e;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}