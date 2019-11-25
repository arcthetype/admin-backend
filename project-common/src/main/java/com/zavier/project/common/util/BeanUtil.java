package com.zavier.project.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanUtil {

    public static <T> T copyProperties(Object source, Class<T> target) {
        if (source == null) {
            return null;
        }
        T t = BeanUtils.instantiateClass(target);
        BeanUtils.copyProperties(source, t);
        return t;
    }

    public static <T> List<T> copyListProperties(List<?> sourceList, Class<T> targetCls) {
        if (CollectionUtil.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>();
        for (Object source : sourceList) {
            T t = BeanUtils.instantiateClass(targetCls);
            BeanUtils.copyProperties(source, t);
            list.add(t);
        }
        return list;
    }
}
