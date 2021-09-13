package at.ac.tuwien.damap.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpLicense {

    private String license_ref;
    private Date start_date;
}
