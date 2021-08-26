package at.ac.tuwien.damap.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpContact extends MaDmpPerson {

    private MaDmpIdentifier contact_id;

    private String mbox;
    private String name;

}
