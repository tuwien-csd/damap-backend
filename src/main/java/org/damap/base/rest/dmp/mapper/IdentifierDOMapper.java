package org.damap.base.rest.dmp.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.Identifier;
import org.damap.base.rest.dmp.domain.IdentifierDO;

/** IdentifierDOMapper class. */
@UtilityClass
public class IdentifierDOMapper {

  // TODO possibly make identifier unique?

  /**
   * mapEntityToDO.
   *
   * @param identifier a {@link org.damap.base.domain.Identifier} object
   * @param identifierDO a {@link org.damap.base.rest.dmp.domain.IdentifierDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.IdentifierDO} object
   */
  public IdentifierDO mapEntityToDO(Identifier identifier, IdentifierDO identifierDO) {
    identifierDO.setIdentifier(identifier.getIdentifier());
    identifierDO.setType(identifier.getIdentifierType());

    return identifierDO;
  }

  /**
   * mapDOtoEntity.
   *
   * @param identifierDO a {@link org.damap.base.rest.dmp.domain.IdentifierDO} object
   * @param identifier a {@link org.damap.base.domain.Identifier} object
   * @return a {@link org.damap.base.domain.Identifier} object
   */
  public Identifier mapDOtoEntity(IdentifierDO identifierDO, Identifier identifier) {
    identifier.setIdentifier(identifierDO.getIdentifier());
    identifier.setIdentifierType(identifierDO.getType());

    return identifier;
  }
}
