package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Identifier;
import at.ac.tuwien.damap.rest.domain.IdentifierDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IdentifierDOMapper {

    //TODO possibly make identifier unique?

    public IdentifierDO mapEntityToDO(Identifier identifier, IdentifierDO identifierDO){
        identifierDO.setIdentifier(identifier.getIdentifier());
        identifierDO.setType(identifier.getIdentifierType());

        return identifierDO;
    }

    public Identifier mapDOtoEntity(IdentifierDO identifierDO, Identifier identifier){
        identifier.setIdentifier(identifierDO.getIdentifier());
        identifier.setIdentifierType(identifierDO.getType());

        return identifier;
    }
}
