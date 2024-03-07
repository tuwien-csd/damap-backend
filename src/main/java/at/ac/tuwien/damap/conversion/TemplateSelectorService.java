package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.enums.ETemplateType;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;

public interface TemplateSelectorService {

    ETemplateType selectTemplate(DmpDO dmpDO);

    boolean isHorizonEuropeTemplate(IdentifierDO identifierDO);

    boolean isFWFTemplate(IdentifierDO identifierDO);
}
