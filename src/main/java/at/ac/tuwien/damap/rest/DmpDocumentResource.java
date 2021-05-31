package at.ac.tuwien.damap.rest;

import at.ac.tuwien.conversion.DocumentConversionService;
import at.ac.tuwien.damap.rest.service.DmpService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;

@Path("/document")
public class DmpDocumentResource {

    private static final Logger log = LoggerFactory.getLogger(DmpDocumentResource.class);

    @Inject
    DocumentConversionService documentConversionService;

    @Inject
    DmpService dmpService;

    @GET
    @Path("/{dmpId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFWFTemplate(@PathParam("dmpId") long dmpId) throws IOException {
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
