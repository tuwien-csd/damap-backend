package at.ac.tuwien.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpContact extends MaDmpPerson {

    private MaDmpIdentifier contact_id;

    private String mbox;
    private String name;

}
