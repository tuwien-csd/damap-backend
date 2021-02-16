package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Identifier;
import at.ac.tuwien.damap.rest.domain.IdentifierDO;

public class IdentifierDOMapper {

    public static void mapAtoB(Identifier identifier, IdentifierDO identifierDO){
        identifierDO.setIdentifier(identifier.getIdentifier());
        identifierDO.setType(identifier.getIdentifierType());
    }
}
