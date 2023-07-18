package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.ECostType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import jakarta.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CostDO {

    private Long id;
    @Size(max = 255)
    private String title;
    private Float value;
    @Size(max = 255)
    private String currencyCode; // controlled vocabulary: ISO 4217
    @Size(max = 4000)
    private String description;
    private ECostType type;
    @Size(max = 255)
    private String customType;
}
