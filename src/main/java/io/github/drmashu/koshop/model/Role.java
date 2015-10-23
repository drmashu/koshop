package io.github.drmashu.koshop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.seasar.doma.Domain;

/**
 * Created by drmashu on 2015/10/23.
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
public enum Role implements JsonEnumInt {
    ADMINISTRATOR(0),
    MANAGER(1),
    CUSTOMER(2);
    private Role(int value) {
        this.value = value;
    }    private int value;
    @JsonValue
    @Override
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static Role of(int value) {
        return Util.getEnumFromValue(value, Role.class);
    }
}
