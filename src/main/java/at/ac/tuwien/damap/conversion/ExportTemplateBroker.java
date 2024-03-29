package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.enums.ETemplateType;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@JBossLog
public class ExportTemplateBroker {

    private final DmpService dmpService;
    private final ExportScienceEuropeTemplate exportScienceEuropeTemplate;
    private final ExportFWFTemplate exportFWFTemplate;
    private final ExportHorizonEuropeTemplate exportHorizonEuropeTemplate;

    private final TemplateSelectorServiceImpl templateSelectorService;

    @Inject
    public ExportTemplateBroker(DmpService dmpService, ExportScienceEuropeTemplate exportScienceEuropeTemplate,
            ExportFWFTemplate exportFWFTemplate, ExportHorizonEuropeTemplate exportHorizonEuropeTemplate,
                                TemplateSelectorServiceImpl templateSelectorService) {
        this.dmpService = dmpService;
        this.exportScienceEuropeTemplate = exportScienceEuropeTemplate;
        this.exportFWFTemplate = exportFWFTemplate;
        this.exportHorizonEuropeTemplate = exportHorizonEuropeTemplate;
        this.templateSelectorService = templateSelectorService;
    }

    /**
     * Decides which export template to use.
     * Currently only supports FWF and Science Europe templates.
     *
     * @param dmpId
     * @return
     */
    public XWPFDocument exportTemplate(long dmpId) {
        return exportTemplateByType(dmpId, templateSelectorService.selectTemplate(dmpService.getDmpById(dmpId)));
    }

    public XWPFDocument exportTemplateByType(long dmpId, ETemplateType type) {
        switch (type) {
            case FWF:
                return exportFWFTemplate.exportTemplate(dmpId);
            case HORIZON_EUROPE:
                return exportHorizonEuropeTemplate.exportTemplate(dmpId);
            case SCIENCE_EUROPE:
            default:
                return exportScienceEuropeTemplate.exportTemplate(dmpId);
        }
    }
}
