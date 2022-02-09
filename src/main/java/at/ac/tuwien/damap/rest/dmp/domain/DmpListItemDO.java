package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EFunctionRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DmpListItemDO {

    private long id;
    private String title;
    private ContributorDO contact;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date created;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date modified;
    private String description;
    private ProjectDO project;
    private EFunctionRole accessType;
}
