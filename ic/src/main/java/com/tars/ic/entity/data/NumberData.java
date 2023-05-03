package com.tars.ic.entity.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NumberData {

  private Long total;
  private Long unreceived;
  private Long received;
}
