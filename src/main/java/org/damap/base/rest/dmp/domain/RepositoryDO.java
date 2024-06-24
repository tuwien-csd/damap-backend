package org.damap.base.rest.dmp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryDO extends HostDO{

    @Size(max = 255)
    private String repositoryId;
}
