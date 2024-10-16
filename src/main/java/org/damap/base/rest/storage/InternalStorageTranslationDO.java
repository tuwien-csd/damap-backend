package org.damap.base.rest.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InternalStorageTranslationDO {
  private long id;
  private long storageId;

  @NotBlank(message = "language code cannot be blank")
  @NotNull(message = "language code cannot be null") private String languageCode;

  @NotBlank(message = "title cannot be blank")
  @NotNull(message = "title cannot be null") private String title;

  private String description;

  private String backupFrequency;
}
