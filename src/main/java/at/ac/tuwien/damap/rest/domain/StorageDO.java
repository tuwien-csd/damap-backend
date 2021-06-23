package at.ac.tuwien.damap.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StorageDO extends HostDO {

    private String url;
    private String backupFrequency;
    private String storageLocation;
    private String backupLocation;
}
