package at.ac.tuwien.damap.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StringDatabaseConverter implements AttributeConverter<String, String> {

    /*
        This converter serves to equalize the behavior of different databases (postgres and oracle in our case).
        One stores empty/null strings as empty, the other as null. Now both will always return null from the database.
     */

    @Override
    public String convertToDatabaseColumn(String s) {
        return s;
    }

    @Override
    public String convertToEntityAttribute(String s) {
        if (s == null)
            return null;
        else if (s.isEmpty())
            return null;

        return s;
    }
}
