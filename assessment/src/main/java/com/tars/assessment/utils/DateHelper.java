package com.tars.assessment.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

  private static final SimpleDateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd");

  public static String getNow() {
    return dateFormat.format(new Date());
  }
}
