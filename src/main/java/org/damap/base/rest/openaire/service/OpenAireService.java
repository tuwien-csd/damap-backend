package org.damap.base.rest.openaire.service;

import generated.Response;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.enums.EErrorCode;
import org.damap.base.exception.DamapApiException;
import org.damap.base.exception.ErrorDto;
import org.damap.base.rest.openaire.OpenAireRemoteResource;
import org.damap.base.rest.openaire.domain.OpenAireResearchProduct;
import org.damap.base.rest.openaire.domain.OpenAireSearchResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/** OpenAireService class. */
@ApplicationScoped
@JBossLog
public class OpenAireService {

  private static final int SINGLE_RESULT = 1;

  private final OpenAireRemoteResource openAireRemoteResource;

  @Inject
  public OpenAireService(@RestClient OpenAireRemoteResource openAireRemoteResource) {
    this.openAireRemoteResource = openAireRemoteResource;
  }

  /**
   * search.
   *
   * @param doi a {@link java.lang.String} object
   * @return a {@link generated.Response} object
   */
  public Response search(String doi) {
    return openAireRemoteResource.search(doi);
  }

  /**
   * Find a research product in the OpenAIRE Graph by DOI.
   *
   * @param doi DOI to search for
   * @return the matching research product
   * @throws DamapApiException when OpenAIRE has no matching research product
   */
  public OpenAireResearchProduct searchResearchProduct(String doi) {
    OpenAireSearchResponse response =
        openAireRemoteResource.searchResearchProducts(doi, SINGLE_RESULT);

    if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
      throw new DamapApiException(
          new ErrorDto(
              EErrorCode.OPENAIRE_NOT_FOUND, "A research product couldn't be found with OpenAire"),
          jakarta.ws.rs.core.Response.Status.NOT_FOUND);
    }

    return response.getResults().get(0);
  }
}
