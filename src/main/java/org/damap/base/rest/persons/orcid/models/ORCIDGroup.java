package org.damap.base.rest.persons.orcid.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ORCIDGroup<T extends ORCIDAffiliationSummary> {
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    List<T> summaries = List.of();
}
