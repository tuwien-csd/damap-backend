package org.damap.base.conversion;

import org.damap.base.enums.ETemplateType;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;

public interface TemplateSelectorService {

    ETemplateType selectTemplate(DmpDO dmpDO);

    boolean isHorizonEuropeTemplate(IdentifierDO identifierDO);

    boolean isFWFTemplate(IdentifierDO identifierDO);
}
