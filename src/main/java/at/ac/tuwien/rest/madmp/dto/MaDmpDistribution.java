package at.ac.tuwien.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.net.*;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpDistribution {

    private URI access_url;
    private Date available_until;
    private String byte_size;
    private String data_access;
    private String description;
    private URI download_url;
    private String format;
    private MaDmpHost host;
    private MaDmpLicense license;
    private String title;
}
