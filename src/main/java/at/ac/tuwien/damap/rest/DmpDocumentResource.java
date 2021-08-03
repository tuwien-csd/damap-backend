package at.ac.tuwien.damap.rest;

import at.ac.tuwien.conversion.DocumentConversionService;
import at.ac.tuwien.damap.rest.service.DmpService;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;

@Path("/document")
@Produces(MediaType.APPLICATION_OCTET_STREAM)
@JBossLog
public class DmpDocumentResource {

    @Inject
    DocumentConversionService documentConversionService;

    @Inject
    DmpService dmpService;

    @GET
    @Path("/{dmpId}")
    public Response getFWFTemplate(@PathParam("dmpId") long dmpId) throws Exception {
        log.info("Return DMP document file for DMP with id=" + dmpId);

        String filename = dmpService.getDefaultFileName(dmpId);

        XWPFDocument document = documentConversionService.getFWFTemplate(dmpId);

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
}
