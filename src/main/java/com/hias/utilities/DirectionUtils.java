package com.hias.utilities;

import org.springframework.data.domain.Sort;

public class DirectionUtils {
    public static Sort.Direction getDirection(String direct) {
        return "desc".equals(direct) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
