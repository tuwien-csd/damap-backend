package org.damap.base.rest;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.rest.storage.InternalStorageDO;
import org.damap.base.rest.storage.InternalStorageService;
import org.jboss.resteasy.reactive.RestPath;

/** InternalStorageResource class. */
@Path("/api/storages")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class InternalStorageResource {

  @Inject InternalStorageService internalStorageService;

  /**
   * getAllByLanguage.
   *
   * @param languageCode a {@link java.lang.String} object
   * @return a {@link java.util.List} object
   */
  @GET
  @Path("/{languageCode}")
  public List<InternalStorageDO> getAllByLanguage(@RestPath String languageCode) {
    log.debug("Return all internal storage options for language " + languageCode);
    return internalStorageService.getAllByLanguage(languageCode);
  }
}
