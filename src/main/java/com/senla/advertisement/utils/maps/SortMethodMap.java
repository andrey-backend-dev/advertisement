package com.senla.advertisement.utils.maps;

import com.senla.advertisement.utils.enums.SortMethod;

import java.util.HashMap;
import java.util.Map;

public class SortMethodMap {

    private static Map<String, SortMethod> map = new HashMap<>();

    static {
        map.put(SortMethod.NULL.getName(), SortMethod.NULL);
        map.put(SortMethod.NAME.getName(), SortMethod.NAME);
        map.put(SortMethod.STATUS.getName(), SortMethod.STATUS);
        map.put(SortMethod.CREATION_DATE.getName(), SortMethod.CREATION_DATE);
        map.put(SortMethod.USER.getName(), SortMethod.USER);
    }

    public static SortMethod getSortMethodByName(String name) {
        return map.get(name.toLowerCase());
    }
}
