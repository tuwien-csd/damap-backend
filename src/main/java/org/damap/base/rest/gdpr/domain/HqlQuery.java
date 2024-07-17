package org.damap.base.rest.gdpr.domain;

import lombok.Data;

/** HqlQuery class. */
@Data
public class HqlQuery {
  // Entity name is stored separately, so it can easily be mapped to GdprResult later
  private String entityName;
  private String hql;
}
