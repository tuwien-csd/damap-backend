package at.ac.tuwien.damap.rest.domain;

import at.ac.tuwien.damap.enums.PersonIdType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonIdDO {

    private String identifier;

    private PersonIdType type;
}
