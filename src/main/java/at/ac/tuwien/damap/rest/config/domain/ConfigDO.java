package at.ac.tuwien.damap.rest.config.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigDO {

    private String authUrl;
    private String authClient;
    private String authScope;
    private String authUser;
    private String env;
}
