package at.ac.tuwien.damap.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CostDO {

    private Long id;
    private String title;
    private Long value;
    private String currencyCode; // controlled vocabulary: ISO 4217
    private String description;
    private String type;
    private String customType;
}
