package com.zavier.project.common.util;

import com.google.common.base.Joiner;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidatorUtil {
    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static String validate(Object obj) {
        List<String> errMsgList = new ArrayList<>();
        Set<ConstraintViolation<Object>> validate = validator.validate(obj);
        if (CollectionUtil.isNotEmpty(validate)) {
            validate.forEach(r -> {
                Path path = r.getPropertyPath();
                String message = r.getMessage();
                errMsgList.add(path + message);
            });
        }
        return Joiner.on(",").join(errMsgList);
    }
}
