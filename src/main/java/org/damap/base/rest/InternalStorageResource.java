package org.damap.base.rest;

import org.damap.base.rest.storage.InternalStorageDO;
import org.damap.base.rest.storage.InternalStorageService;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/storages")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class InternalStorageResource {

    @Inject
    InternalStorageService internalStorageService;

    @GET
    @Path("/{languageCode}")
    public List<InternalStorageDO> getAllByLanguage(@PathParam String languageCode) {
        log.debug("Return all internal storage options for language " + languageCode);
        return internalStorageService.getAllByLanguage(languageCode);
    }


}
