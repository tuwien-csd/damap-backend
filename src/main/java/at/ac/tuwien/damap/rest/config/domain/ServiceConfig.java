package at.ac.tuwien.damap.rest.config.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceConfig {

    @JsonAlias({ "display-text" })
    String displayText;

    @JsonAlias({ "query-value" })
    String queryValue;

    @JsonProperty(value = "class-name")
    @JsonIgnore
    String className;
}
