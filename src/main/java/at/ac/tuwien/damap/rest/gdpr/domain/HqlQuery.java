package at.ac.tuwien.damap.rest.gdpr.domain;

import lombok.Data;

@Data
public class HqlQuery {

    private String entity;
    private String hql;
}
