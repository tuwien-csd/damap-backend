package at.ac.tuwien.damap.rest.access.domain;

import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date start;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date until;

}
