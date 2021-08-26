package at.ac.tuwien.damap.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpMetadata {

    private String description;
    private String language;
    private MaDmpIdentifier metadata_standard_id;
}
