package at.ac.tuwien.dmap.rest.domain;

import at.ac.tuwien.dmap.domain.DataManagementPlan;
import at.ac.tuwien.tiss.util.mapper_generator.annotaions.MapFrom;
import at.ac.tuwien.tiss.util.mapper_generator.annotaions.MapTo;
import lombok.Data;

@Data
@MapFrom(DataManagementPlan.class)
@MapTo(DataManagementPlan.class)
public class DataManagementPlanDto {
    private Long id;
    private long version;
}
