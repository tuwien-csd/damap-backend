package at.ac.tuwien.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpSecurityAndPrivacy {

    private String description;
    private String title;
}
