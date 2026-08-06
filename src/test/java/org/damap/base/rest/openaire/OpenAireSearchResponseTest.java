package org.damap.base.rest.openaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.damap.base.rest.openaire.domain.OpenAireResearchProduct;
import org.damap.base.rest.openaire.domain.OpenAireSearchResponse;
import org.junit.jupiter.api.Test;

class OpenAireSearchResponseTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void deserializeResearchProductsResponse() throws Exception {
    try (InputStream fixture =
        getClass().getResourceAsStream("/json/openaireResearchProductsResponse.json")) {
      assertNotNull(fixture);

      OpenAireSearchResponse response =
          objectMapper.readValue(fixture, OpenAireSearchResponse.class);

      assertEquals(1L, response.getHeader().getNumFound());
      assertEquals(1, response.getResults().size());

      OpenAireResearchProduct product = response.getResults().get(0);
      assertEquals("other", product.getType());
      assertEquals(
          "FIG. 12 in A survey of small mammals in the Volta Region of Ghana",
          product.getMainTitle());
      assertEquals(2, product.getDescriptions().size());
      assertEquals("2021-05-20", product.getPublicationDate());
      assertEquals("OPEN", product.getBestAccessRight().getLabel());
      assertEquals("10129818", product.getSize());
      assertEquals("10.5281/zenodo.4783814", product.getPids().get(0).getValue());
      assertEquals("Image", product.getInstances().get(0).getType());
      assertEquals("CC 0", product.getInstances().get(0).getLicense());
    }
  }
}
