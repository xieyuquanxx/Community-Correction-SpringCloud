package com.tars.oss.api;


import java.util.List;
import lombok.Getter;

@Getter
public enum ResponseStatus {

  SUCCESS("200", "success"),
  FAIL("500", "failed"),

  HTTP_STATUS_200("200", "ok"),
  HTTP_STATUS_400("400", "request error"),
  HTTP_STATUS_401("401", "no authentication"),
  HTTP_STATUS_403("403", "no authorities"),
  HTTP_STATUS_500("500", "server error");

  public static final List<ResponseStatus> HTTP_STATUS_ALL =
      List.of(HTTP_STATUS_200,
          HTTP_STATUS_400,
          HTTP_STATUS_401,
          HTTP_STATUS_403,
          HTTP_STATUS_500);

  /**
   * response code
   */
  private final String responseCode;

  /**
   * description.
   */
  private final String description;

  ResponseStatus(String responseCode, String description) {
    this.responseCode = responseCode;
    this.description = description;
  }
}