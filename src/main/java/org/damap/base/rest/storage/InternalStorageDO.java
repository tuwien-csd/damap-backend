package org.damap.base.rest.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** InternalStorageDO class. */
@EqualsAndHashCode
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InternalStorageDO {
  private long id;

  @NotBlank(message = "url cannot be blank")
  @NotNull(message = "url cannot be null") private String url;

  @NotBlank(message = "storage location cannot be blank")
  @NotNull(message = "storage location cannot be null") private String storageLocation;

  private String backupLocation;

  @NotNull(message = "activation status cannot be null") private Boolean active;

  // the following information comes from the translation table
  List<InternalStorageTranslationDO> translations;
}
