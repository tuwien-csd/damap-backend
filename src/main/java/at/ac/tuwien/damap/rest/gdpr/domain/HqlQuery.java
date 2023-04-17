package at.ac.tuwien.damap.rest.gdpr.domain;

import lombok.Data;

@Data
public class HqlQuery {
    // Entity name is stored separately, so it can easily be mapped to GdprResult later
    private String entityName;
    private String hql;
}
