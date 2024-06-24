package org.damap.base.conversion;

import org.damap.base.enums.EFunderIds;
import org.damap.base.enums.EIdentifierType;
import org.damap.base.enums.ETemplateType;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.jbosslog.JBossLog;

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
