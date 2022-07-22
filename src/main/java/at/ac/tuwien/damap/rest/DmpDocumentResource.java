package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.conversion.ExportFWFTemplate;
import at.ac.tuwien.damap.conversion.ExportScienceEuropeTemplate;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.validation.AccessValidator;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.ForbiddenException;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;

@Path("/api/document")
@Authenticated
@Produces(MediaType.APPLICATION_OCTET_STREAM)
@JBossLog
public class DmpDocumentResource {

    @Inject
    SecurityService securityService;

    @Inject
    AccessValidator accessValidator;

    @Inject
    ExportScienceEuropeTemplate exportScienceEuropeTemplate;

    @Inject
    ExportFWFTemplate exportFWFTemplate;

    @Inject
    DmpService dmpService;

    @GET
    @Path("/{dmpId}")
    public Response exportTemplate(@PathParam("dmpId") long dmpId) {
        log.info("Return DMP document file for DMP with id=" + dmpId);

        String personId = this.getPersonId();
        if(!accessValidator.canViewDmp(dmpId, personId)){
            throw new ForbiddenException("Not authorized to access dmp with id " + dmpId);
        }

        String filename = dmpService.getDefaultFileName(dmpId);

        // TODO dynamically adapt exported template based on funder ID
//        XWPFDocument document = exportScienceEuropeTemplate.exportTemplate(dmpId);
        XWPFDocument document = exportFWFTemplate.exportTemplate(dmpId);

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
