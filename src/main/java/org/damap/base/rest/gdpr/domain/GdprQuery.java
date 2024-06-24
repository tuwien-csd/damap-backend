package org.damap.base.rest.gdpr.domain;

import lombok.Data;

import java.util.List;

/**
 * Container for information necessary to build a query for GDPR data.
 */
@Data
public class GdprQuery {

    private Class<?> root;
    /** The name of the field that will be joined on. */
    private String fieldName;
    /** The name of the field that is annotated with @GdprKey */
    private String key;
    // context, base and extended hold the select values
    private List<String> context;
    private List<String> base;
    private List<String> extended;
    // contextJoins, baseJoins and extendedJoins hold join information
    // (for selected fields that are not primitive or enum)
    private List<GdprQuery> contextJoins;
    private List<GdprQuery> baseJoins;
    private List<GdprQuery> extendedJoins;

}
