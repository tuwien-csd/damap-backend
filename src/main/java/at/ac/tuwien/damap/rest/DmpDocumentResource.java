package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.conversion.DocumentConversionService;
import at.ac.tuwien.damap.conversion.ExportFWFTemplate;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;

@Path("/document")
@Authenticated
@Produces(MediaType.APPLICATION_OCTET_STREAM)
@JBossLog
public class DmpDocumentResource {

    @Inject
    JsonWebToken jsonWebToken;

    //@Inject
    //DocumentConversionService documentConversionService;

    @Inject
    ExportFWFTemplate exportFWFTemplate;

    @Inject
    DmpService dmpService;

    @ConfigProperty(name = "damap.auth.user")
    String authUser;

    @GET
    @Path("/{dmpId}")

    public Response getTemplate(@PathParam("dmpId") long dmpId) throws Exception {
        log.info("Return DMP document file for DMP with id=" + dmpId);

        // TODO: check permission
        String personId = this.getPersonId();

        String filename = dmpService.getDefaultFileName(dmpId);

        // TODO: replace template link with template uploaded from frontend
        String template = "template/template.docx";

        XWPFDocument document = exportFWFTemplate.getTemplate(dmpId, template);

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
        if (jsonWebToken.claim(authUser).isEmpty()) {
            throw new AuthenticationFailedException("User ID is missing.");
        }
        return String.valueOf(jsonWebToken.claim(authUser).get());
    }
}
