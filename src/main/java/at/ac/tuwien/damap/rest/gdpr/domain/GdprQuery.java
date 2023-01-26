package at.ac.tuwien.damap.rest.gdpr.domain;

import lombok.Data;

import java.util.List;

@Data
public class GdprQuery {

    private String entityName;
    private String key;
    private List<String> base;
    private List<String> extended;
    private List<GdprQuery> baseJoins;
    private List<GdprQuery> extendedJoins;
}
