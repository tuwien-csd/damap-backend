package at.ac.tuwien.damap.rest.gdpr.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GdprResult {

    private String entity;
    private List<Map<String, Object>> entries;

}
