package at.ac.tuwien.damap.rest.access.domain;

import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessDO extends ContributorDO {

    @NotNull
    @Positive
    private long dmpId;

    @NotNull
    private EFunctionRole access;

    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date start;

    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date until;

}
