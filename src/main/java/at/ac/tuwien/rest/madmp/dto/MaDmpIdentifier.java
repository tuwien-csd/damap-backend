package at.ac.tuwien.rest.madmp.dto;

import at.ac.tuwien.damap.enums.EIdentifierType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpIdentifier {

    private String identifier;
    private String identifierType;
}
