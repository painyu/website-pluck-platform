package com.website.pluck.platform.common.validator;

import com.website.pluck.platform.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

public abstract class Assert {
    public Assert() {
    }

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
