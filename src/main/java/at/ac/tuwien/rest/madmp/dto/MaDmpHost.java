package at.ac.tuwien.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpHost {

    private String availability;
    private String backup__frequency;
    private String backup_type;
    private String certified_with;
    private String description;
    private String geo_location;
    private String pid_system;
    private String storage_type;
    private String support_versioning;
    private String title;
    private URI url;
    
}
