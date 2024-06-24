package org.damap.base.rest.config.domain;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class PersonServiceConfigurations {
    List<ServiceConfig> configs;

    public static PersonServiceConfigurations of(String input) throws IOException {
        ObjectMapper x = new ObjectMapper();
        var jsonConfigs = x.readTree(input).get("person-services");

        PersonServiceConfigurations entity = new PersonServiceConfigurations();
        entity.configs = x.readValue(jsonConfigs.toString(), new TypeReference<List<ServiceConfig>>() {
        });

        return entity;
    }

}
