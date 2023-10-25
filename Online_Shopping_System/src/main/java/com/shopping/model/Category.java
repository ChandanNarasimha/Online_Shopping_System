package com.shopping.model;

import java.util.HashMap;
import java.util.Map;


public enum Category {
    Electrinics(0),
    Fashion(1),
    HomeDecors(2),
    PlasticItems(3),
    Cosmatics(4),
    Gifts(5);

    private int value;
    private static Map map = new HashMap<>();

    private Category(int value) {
        this.value = value;
    }

    static {
        for (Category category : Category.values()) {
            map.put(category.value, category);
        }
    }

    public static Category valueOf(int category) {
        return (Category) map.get(category);
    }

    public int getValue() {
        return value;
    }
}

