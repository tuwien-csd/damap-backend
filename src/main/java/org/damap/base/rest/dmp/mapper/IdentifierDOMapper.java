package org.damap.base.rest.dmp.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.Identifier;
import org.damap.base.rest.dmp.domain.IdentifierDO;

@UtilityClass
public class IdentifierDOMapper {

  // TODO possibly make identifier unique?

  public IdentifierDO mapEntityToDO(Identifier identifier, IdentifierDO identifierDO) {
    identifierDO.setIdentifier(identifier.getIdentifier());
    identifierDO.setType(identifier.getIdentifierType());

    return identifierDO;
  }

  public Identifier mapDOtoEntity(IdentifierDO identifierDO, Identifier identifier) {
    identifier.setIdentifier(identifierDO.getIdentifier());
    identifier.setIdentifierType(identifierDO.getType());

    return identifier;
  }
}
