package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EAccessRight;
import at.ac.tuwien.damap.enums.EDataAccessType;
import at.ac.tuwien.damap.enums.EDataSource;
import at.ac.tuwien.damap.enums.EDataType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetDO {

    private Long id;
    @Size(max = 255)
    private String title;
    private List<EDataType> type = new ArrayList<>();
    private Long size;
    @Size(max = 4000)
    private String description;
    private Boolean personalData;
    private Boolean sensitiveData;
    private Boolean legalRestrictions;
    @Size(max = 255)
    private String license;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date startDate;
    @Size(max = 255)
    private String referenceHash;
    private EDataAccessType dataAccess;
    private EAccessRight selectedProjectMembersAccess;
    private EAccessRight otherProjectMembersAccess;
    private EAccessRight publicAccess;
    private Boolean delete;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date dateOfDeletion;
    @Size(max = 4000)
    private String reasonForDeletion;
    private ContributorDO deletionPerson;
    private Integer retentionPeriod;
    private IdentifierDO datasetId;
    private EDataSource source;
}
