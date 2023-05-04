package com.tars.category.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountData {

  private Long baseCount; // 基础级
  private Long looseCount; // 宽松级
  private Long commonCount; // 普通级
  private Long strictCount; // 严格级

}
