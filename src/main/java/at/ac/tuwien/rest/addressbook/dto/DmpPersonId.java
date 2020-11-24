package at.ac.tuwien.rest.addressbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DmpPersonId {

    public enum Type {
        ORCID,
        ISNI,
        OPENID
    }

    @Getter
    @Setter
    private String identifier;

    @Getter
    @Setter
    private Type type;
}
