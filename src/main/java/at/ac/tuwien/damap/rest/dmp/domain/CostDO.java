package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.ECostType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CostDO {

    private Long id;
    private String title;
    private Float value;
    private String currencyCode; // controlled vocabulary: ISO 4217
    private String description;
    private ECostType type;
    private String customType;
}
