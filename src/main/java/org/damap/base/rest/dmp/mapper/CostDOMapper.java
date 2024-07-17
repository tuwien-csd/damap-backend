package org.damap.base.rest.dmp.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.Cost;
import org.damap.base.rest.dmp.domain.CostDO;

/** CostDOMapper class. */
@UtilityClass
public class CostDOMapper {

  /**
   * mapEntityToDO.
   *
   * @param cost a {@link org.damap.base.domain.Cost} object
   * @param costDO a {@link org.damap.base.rest.dmp.domain.CostDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.CostDO} object
   */
  public CostDO mapEntityToDO(Cost cost, CostDO costDO) {
    costDO.setId(cost.id);
    costDO.setTitle(cost.getTitle());
    costDO.setValue(cost.getValue());
    costDO.setCurrencyCode(cost.getCurrencyCode());
    costDO.setDescription(cost.getDescription());
    if (cost.getType() != null) costDO.setType(cost.getType());
    costDO.setCustomType(cost.getCustomType());

    return costDO;
  }

  /**
   * mapDOtoEntity.
   *
   * @param costDO a {@link org.damap.base.rest.dmp.domain.CostDO} object
   * @param cost a {@link org.damap.base.domain.Cost} object
   * @return a {@link org.damap.base.domain.Cost} object
   */
  public Cost mapDOtoEntity(CostDO costDO, Cost cost) {
    cost.setTitle(costDO.getTitle());
    cost.setValue(costDO.getValue());
    cost.setCurrencyCode(costDO.getCurrencyCode());
    cost.setDescription(costDO.getDescription());
    cost.setType(costDO.getType());
    cost.setCustomType(costDO.getCustomType());

    return cost;
  }
}
