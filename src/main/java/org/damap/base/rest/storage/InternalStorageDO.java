package org.damap.base.rest.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InternalStorageDO {
    private long id;
    private String url;
    private String backupFrequency;
    private String storageLocation;
    private String backupLocation;
// the following information comes from the translation table
    private String languageCode;
    private String title;
    private String description;
}
