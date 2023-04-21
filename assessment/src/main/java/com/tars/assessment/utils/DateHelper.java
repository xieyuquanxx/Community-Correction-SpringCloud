package com.tars.assessment.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    private static SimpleDateFormat dateFormat =
            new SimpleDateFormat("YYYY-MM-dd");

    public static String getNow() {
        return dateFormat.format(new Date());
    }
}
