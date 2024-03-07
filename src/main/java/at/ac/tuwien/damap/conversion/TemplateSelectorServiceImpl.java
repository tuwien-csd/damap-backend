package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.enums.EFunderIds;
import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.enums.ETemplateType;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import io.quarkus.arc.DefaultBean;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
@JBossLog
public class TemplateSelectorServiceImpl implements TemplateSelectorService{
    @Override
    public ETemplateType selectTemplate(DmpDO dmpDO) {
        if (dmpDO.getProject() != null && dmpDO.getProject().getFunding() != null) {
            IdentifierDO funderIdentifier = dmpDO.getProject().getFunding().getFunderId();
            if (funderIdentifier != null && EIdentifierType.getFunderIdentifierTypeList().contains(funderIdentifier.getType())) {
                if (isHorizonEuropeTemplate(funderIdentifier)) {
                    return ETemplateType.HORIZON_EUROPE;
                }
                if (isFWFTemplate(funderIdentifier)) {
                    return ETemplateType.FWF;
                }
            }
        }
        // default export science europe template
        return ETemplateType.SCIENCE_EUROPE;
    }

    @Override
    public boolean isHorizonEuropeTemplate(IdentifierDO identifierDO) {
        return (EFunderIds.getEUFunderIds().contains(identifierDO.getIdentifier()));
    }

    @Override
    public boolean isFWFTemplate(IdentifierDO identifierDO) {
        return (EFunderIds.getFWFFunderIds().contains(identifierDO.getIdentifier()));
    }
}
