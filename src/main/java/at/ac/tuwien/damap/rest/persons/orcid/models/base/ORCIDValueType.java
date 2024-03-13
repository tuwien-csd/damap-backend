package at.ac.tuwien.damap.rest.persons.orcid.models.base;

import lombok.Data;

@Data
public class ORCIDValueType {
    String value;

    public Integer asInt() {
        Integer converted = null;
        try {
            converted = Integer.parseInt(value, 10);
        } catch (Exception e) {
            // will return null
        }

        return converted;
    }
}
