package com.zavier.project.common.util;

import org.springframework.beans.BeanUtils;

public class BeanUtil {

    public static <T> T copyProperties(Object source, Class<T> target) {
        T t = BeanUtils.instantiateClass(target);
        BeanUtils.copyProperties(source, t);
        return t;
    }
}
