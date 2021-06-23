package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Cost;
import at.ac.tuwien.damap.enums.ECostType;
import at.ac.tuwien.damap.rest.domain.CostDO;

public class CostDOMapper {

    public static void mapEntityToDO(Cost cost, CostDO costDO) {
        costDO.setId(cost.id);
        costDO.setTitle(cost.getTitle());
        costDO.setValue(cost.getValue());
        costDO.setCurrencyCode(cost.getCurrencyCode());
        costDO.setDescription(cost.getDescription());
        if (cost.getType() != null)
            costDO.setType(cost.getType().getValue());
        costDO.setCustomType(cost.getCustomType());
    }

    public static void mapDOtoEntity(CostDO costDO, Cost cost){
        cost.setTitle(costDO.getTitle());
        cost.setValue(costDO.getValue());
        cost.setCurrencyCode(costDO.getCurrencyCode());
        cost.setDescription(costDO.getDescription());
        cost.setType(ECostType.getByValue(costDO.getType()));
        cost.setCustomType(costDO.getCustomType());
    }
}
