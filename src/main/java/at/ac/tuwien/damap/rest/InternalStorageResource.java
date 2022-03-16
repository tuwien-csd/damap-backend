package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.storage.InternalStorageDO;
import at.ac.tuwien.damap.rest.storage.InternalStorageService;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
