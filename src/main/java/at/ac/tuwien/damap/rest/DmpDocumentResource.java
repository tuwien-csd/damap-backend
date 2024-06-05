package at.ac.tuwien.damap.rest;

import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
// import javax.xml.bind.JAXBContext;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import at.ac.tuwien.damap.conversion.ExportTemplateBroker;
import at.ac.tuwien.damap.enums.ETemplateType;
import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.validation.AccessValidator;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateExtension;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.ForbiddenException;
import lombok.extern.jbosslog.JBossLog;

@Path("/api/document")
// TODO: Reset before merging. Only for testing purpose.
// @Authenticated
@Produces(MediaType.APPLICATION_OCTET_STREAM)
@JBossLog
public class DmpDocumentResource {

    @Inject
    SecurityService securityService;

    @Inject
    AccessValidator accessValidator;

    @Inject
    ExportTemplateBroker exportTemplateBroker;

    @Inject
    DmpService dmpService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance horizonEurope(DmpDO dmp);
    }

    @TemplateExtension
    public static class TemplateExtensions {

        public static String datasetSize(DatasetDO ds) {
            return FileUtils.byteCountToDisplaySize(ds.getSize());
        }
    }

    @GET
    @Path("/{dmpId}")
    public Response exportTemplate(@PathParam("dmpId") long dmpId, @QueryParam("template") ETemplateType template) {
        log.info("Return DMP document file for DMP with id=" + dmpId);

        String personId = this.getPersonId();
        if (!accessValidator.canViewDmp(dmpId, personId)) {
            throw new ForbiddenException("Not authorized to access dmp with id " + dmpId);
        }

        String filename = dmpService.getDefaultFileName(dmpId);

        XWPFDocument document;
        if (template != null)
            document = exportTemplateBroker.exportTemplateByType(dmpId, template);
        else
            document = exportTemplateBroker.exportTemplate(dmpId);

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
                .header("Access-Control-Expose-Headers", "Content-Disposition")
                .build();
    }

    @GET
    @Path("/{dmpId}/qute")
    public Response qute(@PathParam("dmpId") long dmpId, @QueryParam("template") ETemplateType template)
            throws Exception {
        log.info("Return DMP document file for DMP with id=" + dmpId);

        String filename = dmpService.getDefaultFileName(dmpId);
        DmpDO dmp = dmpService.getDmpById(dmpId);

        var templateInstance = Templates.horizonEurope(dmp);
        var rendered = templateInstance.render();

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);

        wordMLPackage.getMainDocumentPart().getContent().addAll(XHTMLImporter.convert(rendered, filename));

        var s = XmlUtils.marshaltoString(wordMLPackage.getMainDocumentPart().getJaxbElement());

        return Response.ok(rendered)
                .header("Content-Disposition", "attachment;filename=" + filename + ".docx")
                .header("Access-Control-Expose-Headers", "Content-Disposition")
                .build();
    }

    private String getPersonId() {
        if (securityService == null) {
            throw new AuthenticationFailedException("User ID is missing.");
        }
        return securityService.getUserId();
    }
}
