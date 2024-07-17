package org.damap.base.rest.config.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import lombok.Data;

/** PersonServiceConfigurations class. */
@Data
public class PersonServiceConfigurations {
  List<ServiceConfig> configs;

  /**
   * of.
   *
   * @param input a {@link java.lang.String} object
   * @return a {@link org.damap.base.rest.config.domain.PersonServiceConfigurations} object
   * @throws java.io.IOException if any.
   */
  public static PersonServiceConfigurations of(String input) throws IOException {
    ObjectMapper x = new ObjectMapper();
    var jsonConfigs = x.readTree(input).get("person-services");

    PersonServiceConfigurations entity = new PersonServiceConfigurations();
    entity.configs =
        x.readValue(jsonConfigs.toString(), new TypeReference<List<ServiceConfig>>() {});

    return entity;
  }
}
