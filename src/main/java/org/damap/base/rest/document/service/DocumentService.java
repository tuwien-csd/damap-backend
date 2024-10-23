package org.damap.base.rest.document.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.damap.base.conversion.ExportTemplateBroker;
import org.damap.base.conversion.TemplateSelectorServiceImpl;
import org.damap.base.enums.ETemplateType;
import org.damap.base.rest.dmp.service.DmpService;
import org.damap.base.rest.document.dto.MultipartBodyDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@JBossLog
public class DocumentService {

  @Inject ExportTemplateBroker exportTemplateBroker;

  @Inject TemplateSelectorServiceImpl templateSelectorService;

  @Inject DmpService dmpService;

  @Inject @RestClient GotenbergRestService gotenbergRestService;

  public StreamingOutput getPreviewPDF(long dmpId, ETemplateType template) {
    // Fetch the document based on the template
    XWPFDocument document =
        (template != null)
            ? exportTemplateBroker.exportTemplateByType(dmpId, template)
            : exportTemplateBroker.exportTemplate(dmpId);

    // Create a temporary Word document
    File tempFile;
    try {
      tempFile = File.createTempFile("dmp", ".docx");
      try (FileOutputStream out = new FileOutputStream(tempFile)) {
        document.write(out);
      }
    } catch (IOException e) {
      log.error("Error creating temporary file", e);
      throw new RuntimeException("Error creating temporary file");
    }

    byte[] pdfBytes;
    try {
      MultipartBodyDTO multipartBodyDTO = new MultipartBodyDTO();
      multipartBodyDTO.file = tempFile;

      pdfBytes = gotenbergRestService.convertToPDF(multipartBodyDTO);
    } catch (Exception e) {
      log.error("Error converting document to PDF", e);
      throw new RuntimeException("Error converting document to PDF");
    } finally {
      // Delete the temporary file if it exists
      if (tempFile.exists()) {
        if (!tempFile.delete()) {
          log.warn("Failed to delete temporary file: " + tempFile.getAbsolutePath());
        }
      }
    }

    byte[] finalPdfBytes = pdfBytes;
    return output -> {
      output.write(finalPdfBytes);
      output.flush();
    };
  }

  public ETemplateType getTemplateType(long dmpId) {
    return templateSelectorService.selectTemplate(dmpService.getDmpById(dmpId));
  }
}
