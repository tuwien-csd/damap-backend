package org.damap.base.rest.gdpr.domain;

import java.util.List;
import java.util.Map;
import lombok.Data;

/** GdprResult class. */
@Data
public class GdprResult {

  private String entity;
  private List<Map<String, Object>> entries;
}
