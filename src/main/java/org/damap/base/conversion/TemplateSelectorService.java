package org.damap.base.conversion;

import org.damap.base.enums.ETemplateType;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;

/** TemplateSelectorService interface. */
public interface TemplateSelectorService {

  /**
   * selectTemplate.
   *
   * @param dmpDO a {@link org.damap.base.rest.dmp.domain.DmpDO} object
   * @return a {@link org.damap.base.enums.ETemplateType} object
   */
  ETemplateType selectTemplate(DmpDO dmpDO);

  /**
   * isHorizonEuropeTemplate.
   *
   * @param identifierDO a {@link org.damap.base.rest.dmp.domain.IdentifierDO} object
   * @return a boolean
   */
  boolean isHorizonEuropeTemplate(IdentifierDO identifierDO);

  /**
   * isFWFTemplate.
   *
   * @param identifierDO a {@link org.damap.base.rest.dmp.domain.IdentifierDO} object
   * @return a boolean
   */
  boolean isFWFTemplate(IdentifierDO identifierDO);
}
