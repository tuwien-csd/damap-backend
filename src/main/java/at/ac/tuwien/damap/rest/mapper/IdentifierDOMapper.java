package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Identifier;
import at.ac.tuwien.damap.rest.domain.IdentifierDO;

public class IdentifierDOMapper {

    //TODO possibly make identifier unique?

    public static void mapEntityToDO(Identifier identifier, IdentifierDO identifierDO){
        identifierDO.setIdentifier(identifier.getIdentifier());
        identifierDO.setType(identifier.getIdentifierType());
    }

    public static void mapDOtoEntity(IdentifierDO identifierDO, Identifier identifier){
        identifier.setIdentifier(identifierDO.getIdentifier());
        identifier.setIdentifierType(identifierDO.getType());
    }
}
