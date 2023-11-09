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

    @Inject
    DmpService dmpService;

    @Inject
    ExportScienceEuropeTemplate exportScienceEuropeTemplate;

    @Inject
    ExportFWFTemplate exportFWFTemplate;

    @Inject
    ExportHorizonEuropeTemplate exportHorizonEuropeTemplate;

    /**
     * Decides which export template to use.
     * Currently only supports FWF and Science Europe templates.
     *
     * @param dmpId
     * @return
     */
    public XWPFDocument exportTemplate(long dmpId) {
        DmpDO dmpDO = dmpService.getDmpById(dmpId);
        if (dmpDO.getProject() != null)
            if (dmpDO.getProject().getFunding() != null)
                if (dmpDO.getProject().getFunding().getFunderId() != null) {
                    IdentifierDO funderIdentifier = dmpDO.getProject().getFunding().getFunderId();
                    if (funderIdentifier.getType() != null)
                        if (funderIdentifier.getType().equals(EIdentifierType.FUNDREF))
                            // FWF FUNDREF Identifier 501100002428
                            if (funderIdentifier.getIdentifier() != null)
                                if (funderIdentifier.getIdentifier().equals("501100002428"))
                                    return exportFWFTemplate.exportTemplate(dmpId);
                }

        // default export science europe template
        return exportScienceEuropeTemplate.exportTemplate(dmpId);
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
