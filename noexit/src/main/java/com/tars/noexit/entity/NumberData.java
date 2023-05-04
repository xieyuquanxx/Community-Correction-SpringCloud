package com.tars.noexit.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NumberData {

  private Long reportNumber;
  private Long zjNumber;
  private Long bkNumber;
}
