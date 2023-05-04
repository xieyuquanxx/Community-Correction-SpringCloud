package com.tars.oss.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseResult<T> {

  /**
   * response code, 200 -> OK.
   */
  private String status;

  /**
   * response message.
   */
  private String message;

  /**
   * response data.
   */
  private T data;

  /**
   * response success result wrapper.
   *
   * @param data response data
   * @param <T>  type of data class
   * @return response result
   */
  public static <T> ResponseResult<T> success(T data) {
    return ResponseResult.<T>builder().data(data)
        .message(
            ResponseStatus.SUCCESS.getDescription())
        .status(ResponseStatus.SUCCESS.getResponseCode())
        .build();
  }

  /**
   * response error result wrapper.
   *
   * @param data    response data
   * @param message error message
   * @param <T>     type of data class
   * @return response result
   */
  public static <T> ResponseResult<T> fail(T data, String message) {
    return ResponseResult.<T>builder().data(data)
        .message(message)
        .status(ResponseStatus.FAIL.getResponseCode())
        .build();
  }
}