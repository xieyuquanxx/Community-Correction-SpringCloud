package com.tars.ic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat formatter =
            new SimpleDateFormat(
                    "yyyy-MM-dd");

    public static String now() {
        return formatter.format(new Date());
    }
}
