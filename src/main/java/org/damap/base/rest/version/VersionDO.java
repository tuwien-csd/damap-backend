package org.damap.base.rest.version;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VersionDO {

    private Long id;
    private Long dmpId;
    private Long revisionNumber;
    private String versionName;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date versionDate;
    private String editor;
}
