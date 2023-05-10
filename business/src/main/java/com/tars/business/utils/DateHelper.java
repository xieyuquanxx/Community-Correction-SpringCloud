package com.tars.business.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

  static SimpleDateFormat formatter = new SimpleDateFormat(
      "yyyy-MM-dd");

  public static String getDate() {
    return formatter.format(new Date());
  }
}
