package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.conversion.DocumentConversionService;
import at.ac.tuwien.damap.conversion.ExportScienceEuropeTemplate;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.security.SecurityService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

@Path("/api/document")
@Authenticated
@Produces(MediaType.APPLICATION_OCTET_STREAM)
@JBossLog
public class DmpDocumentResource {

    @Inject
    SecurityService securityService;

    //@Inject
    //DocumentConversionService documentConversionService;

    @Inject
    ExportScienceEuropeTemplate exportScienceEuropeTemplate;

    @Inject
    DmpService dmpService;

    @ConfigProperty(name = "damap.auth.user")
    String authUser;

    @GET
    @Path("/{dmpId}")

    public Response exportTemplate(@PathParam("dmpId") long dmpId) throws Exception {
        log.info("Return DMP document file for DMP with id=" + dmpId);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        // TODO: check permission
        String personId = this.getPersonId();

        String filename = dmpService.getDefaultFileName(dmpId);

        XWPFDocument document = exportScienceEuropeTemplate.exportTemplate(dmpId);

        StreamingOutput streamingOutput = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException,
                    WebApplicationException {
                document.write(os);
                document.close();
            }
        };

        return Response.ok(streamingOutput)
                .header("Content-Disposition", "attachment;filename=" + filename + ".docx")
                .header("Access-Control-Expose-Headers","Content-Disposition")
                .build();
    }

    private String getPersonId() {
        if (securityService == null) {
            throw new AuthenticationFailedException("User ID is missing.");
        }
        return securityService.getUserId();
    }
}
