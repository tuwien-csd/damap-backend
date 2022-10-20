package at.ac.tuwien.damap.rest.config.domain;

import javax.json.bind.annotation.JsonbTransient;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceConfig {

    @JsonAlias({ "display-text" })
    String displayText;

    @JsonAlias({ "query-value" })
    String queryValue;

    @JsonProperty(value = "class-name")
    @JsonbTransient
    String className;
}
