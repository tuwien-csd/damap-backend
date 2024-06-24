package org.damap.base.rest.dmp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalStorageDO extends HostDO {

    @Size(max = 255)
    private String url;
    @Size(max = 255)
    private String backupFrequency;
    @Size(max = 255)
    private String storageLocation;
    @Size(max = 255)
    private String backupLocation;
}
