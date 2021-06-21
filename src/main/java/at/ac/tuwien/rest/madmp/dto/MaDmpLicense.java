package at.ac.tuwien.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.net.*;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpLicense {

    private URI license_ref;
    private Date start_date;
}
