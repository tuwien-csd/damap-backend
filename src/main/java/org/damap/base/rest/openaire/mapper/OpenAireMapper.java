package org.damap.base.rest.openaire.mapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.damap.base.enums.EAccessRight;
import org.damap.base.enums.EDataAccessType;
import org.damap.base.enums.EDataSource;
import org.damap.base.enums.EDataType;
import org.damap.base.enums.EIdentifierType;
import org.damap.base.enums.ELicense;
import org.damap.base.rest.dmp.domain.DatasetDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;
import org.damap.base.rest.openaire.domain.OpenAireAccessRight;
import org.damap.base.rest.openaire.domain.OpenAireInstance;
import org.damap.base.rest.openaire.domain.OpenAireResearchProduct;

/** Maps OpenAIRE Graph research products to DAMAP datasets. */
@UtilityClass
public class OpenAireMapper {

  private static final int MAX_TITLE_LENGTH = 255;

  /**
   * Map an OpenAIRE research product to a new DAMAP dataset.
   *
   * @param doi DOI used to retrieve the research product
   * @param product OpenAIRE research product
   * @return mapped DAMAP dataset
   */
  public DatasetDO map(String doi, OpenAireResearchProduct product) {
    Objects.requireNonNull(product, "OpenAIRE research product must not be null");

    DatasetDO dataset = new DatasetDO();
    dataset.setSource(EDataSource.REUSED);
    dataset.setTitle(truncate(product.getMainTitle(), MAX_TITLE_LENGTH));
    dataset.setDescription(joinValues(product.getDescriptions(), " "));
    dataset.setFileFormat(joinValues(product.getFormats(), ", "));
    dataset.setDataAccess(mapAccessRight(product.getBestAccessRight()));
    dataset.setStartDate(parseDate(product.getPublicationDate()));
    dataset.setSize(parseSize(product.getSize()));
    dataset.setDatasetId(createDoiIdentifier(doi));
    dataset.setSelectedProjectMembersAccess(EAccessRight.READ);
    dataset.setOtherProjectMembersAccess(EAccessRight.READ);
    dataset.setPublicAccess(EAccessRight.READ);

    mapInstances(product.getInstances(), dataset);
    if (dataset.getType().isEmpty()) {
      addType(mapType(product.getType()), dataset.getType());
    }

    return dataset;
  }

  private void mapInstances(List<OpenAireInstance> instances, DatasetDO dataset) {
    if (instances == null) {
      return;
    }

    for (OpenAireInstance instance : instances) {
      if (instance == null) {
        continue;
      }
      addType(mapType(instance.getType()), dataset.getType());
      if (dataset.getLicense() == null) {
        dataset.setLicense(mapLicense(instance.getLicense()));
      }
    }
  }

  private EDataAccessType mapAccessRight(OpenAireAccessRight accessRight) {
    if (accessRight == null || accessRight.getLabel() == null) {
      return null;
    }

    return switch (accessRight.getLabel().trim().toUpperCase(Locale.ROOT)) {
      case "OPEN", "OPEN ACCESS", "OPEN SOURCE" -> EDataAccessType.OPEN;
      case "RESTRICTED", "EMBARGO" -> EDataAccessType.RESTRICTED;
      case "CLOSED", "CLOSED ACCESS" -> EDataAccessType.CLOSED;
      default -> null;
    };
  }

  private EDataType mapType(String value) {
    if (value == null || value.isBlank()) {
      return EDataType.OTHER;
    }

    String type = value.toLowerCase(Locale.ROOT);
    if (type.contains("image")) {
      return EDataType.IMAGES;
    }
    if (type.contains("audio")
        || type.contains("video")
        || type.contains("film")
        || type.contains("sound")) {
      return EDataType.AUDIOVISUAL_DATA;
    }
    if (type.contains("source code")) {
      return EDataType.SOURCE_CODE;
    }
    if (type.contains("software") || type.contains("application")) {
      return EDataType.SOFTWARE_APPLICATIONS;
    }
    if (type.contains("database")) {
      return EDataType.DATABASES;
    }
    if (type.contains("text")
        || type.contains("article")
        || type.contains("publication")
        || type.contains("book")
        || type.contains("thesis")
        || type.contains("preprint")) {
      return EDataType.PLAIN_TEXT;
    }
    return EDataType.OTHER;
  }

  private ELicense mapLicense(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }

    String normalized = value.trim();
    if ("CC 0".equalsIgnoreCase(normalized) || "CC0".equalsIgnoreCase(normalized)) {
      return ELicense.CCZERO;
    }
    return ELicense.getByAcronymOrUrl(normalized);
  }

  private Date parseDate(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }
    try {
      return Date.from(LocalDate.parse(value).atStartOfDay().toInstant(ZoneOffset.UTC));
    } catch (DateTimeParseException ignored) {
      return null;
    }
  }

  private Long parseSize(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }
    try {
      long size = Long.parseLong(value);
      return size >= 0 ? size : null;
    } catch (NumberFormatException ignored) {
      return null;
    }
  }

  private String joinValues(List<String> values, String delimiter) {
    if (values == null) {
      return null;
    }
    String joined =
        values.stream()
            .filter(Objects::nonNull)
            .map(String::trim)
            .filter(value -> !value.isEmpty())
            .distinct()
            .reduce((first, second) -> first + delimiter + second)
            .orElse(null);
    return joined;
  }

  private String truncate(String value, int maximumLength) {
    if (value == null || value.length() <= maximumLength) {
      return value;
    }
    return value.substring(0, maximumLength);
  }

  private IdentifierDO createDoiIdentifier(String doi) {
    IdentifierDO identifier = new IdentifierDO();
    identifier.setType(EIdentifierType.DOI);
    identifier.setIdentifier(doi);
    return identifier;
  }

  private void addType(EDataType type, List<EDataType> types) {
    if (!types.contains(type)) {
      types.add(type);
    }
  }
}
