package org.damap.base.rest.openaire.mapper;

import eu.openaire.oaf.QualifierType;
import eu.openaire.oaf.Result;
import eu.openaire.oaf.StructuredPropertyElementType;
import generated.Response;
import jakarta.xml.bind.JAXBElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import lombok.experimental.UtilityClass;
import org.damap.base.enums.*;
import org.damap.base.rest.dmp.domain.DatasetDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;

/** OpenAireMapper class. */
@UtilityClass
public class OpenAireMapper {

  /**
   * mapAtoB.
   *
   * @param doi a {@link java.lang.String} object
   * @param openaireResponse a {@link generated.Response} object
   * @param datasetDO a {@link org.damap.base.rest.dmp.domain.DatasetDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.DatasetDO} object
   */
  public DatasetDO mapAtoB(String doi, Response openaireResponse, DatasetDO datasetDO) {
    datasetDO.setSource(EDataSource.REUSED);
    try {
      Result result =
          openaireResponse.getResults().getResult().getMetadata().getEntity().getResult();
      List<JAXBElement<?>> elements = result.getCreatorOrResulttypeOrLanguage();
      for (JAXBElement<?> element : elements) {
        if (element.getDeclaredType() == StructuredPropertyElementType.class)
          datasetDO =
              mapAtoB(
                  element.getName().getLocalPart(),
                  (StructuredPropertyElementType) element.getValue(),
                  datasetDO);
        else if (element.getDeclaredType() == QualifierType.class)
          datasetDO =
              mapAtoB(
                  element.getName().getLocalPart(), (QualifierType) element.getValue(), datasetDO);
        else if (element.getDeclaredType() == String.class)
          datasetDO =
              mapAtoB(element.getName().getLocalPart(), (String) element.getValue(), datasetDO);
      }
    } catch (NullPointerException e) {
      // Return null if no result present
      return null;
    }
    IdentifierDO identifierDO = new IdentifierDO();
    identifierDO.setType(EIdentifierType.DOI);
    identifierDO.setIdentifier(doi);
    datasetDO.setDatasetId(identifierDO);
    datasetDO.setSelectedProjectMembersAccess(EAccessRight.READ);
    datasetDO.setOtherProjectMembersAccess(EAccessRight.READ);
    datasetDO.setPublicAccess(EAccessRight.READ);
    return datasetDO;
  }

  /**
   * mapAtoB.
   *
   * @param propertyName a {@link java.lang.String} object
   * @param elementType a {@link eu.openaire.oaf.StructuredPropertyElementType} object
   * @param datasetDO a {@link org.damap.base.rest.dmp.domain.DatasetDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.DatasetDO} object
   */
  public DatasetDO mapAtoB(
      String propertyName, StructuredPropertyElementType elementType, DatasetDO datasetDO) {
    if ("title".equals(propertyName)) {
      if (datasetDO.getTitle() == null) datasetDO.setTitle(elementType.getValue());
      else datasetDO.setTitle(datasetDO.getTitle() + ' ' + elementType.getValue());
      if (datasetDO.getTitle().length() > 255)
        datasetDO.setTitle(datasetDO.getTitle().substring(0, 255));
    }
    return datasetDO;
  }

  /**
   * mapAtoB.
   *
   * @param propertyName a {@link java.lang.String} object
   * @param qualifierType a {@link eu.openaire.oaf.QualifierType} object
   * @param datasetDO a {@link org.damap.base.rest.dmp.domain.DatasetDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.DatasetDO} object
   */
  public DatasetDO mapAtoB(String propertyName, QualifierType qualifierType, DatasetDO datasetDO) {
    if ("bestaccessright".equals(propertyName)) {
      datasetDO.setDataAccess(getDataAccessType(qualifierType.getClassid().toLowerCase()));
    } else if ("resourcetype".equals(propertyName)) {
      List<EDataType> types = datasetDO.getType();
      String type = qualifierType.getClassname().toLowerCase();
      switch (type) {
        case "image":
          addType(EDataType.IMAGES, types);
          break;
        case "film":
        case "sound":
          addType(EDataType.AUDIOVISUAL_DATA, types);
          break;
        case "software":
          addType(EDataType.SOFTWARE_APPLICATIONS, types);
          break;
        case "text":
          addType(EDataType.PLAIN_TEXT, types);
          break;
        default:
          addType(EDataType.OTHER, types);
          break;
      }
    }
    return datasetDO;
  }

  /**
   * mapAtoB.
   *
   * @param propertyName a {@link java.lang.String} object
   * @param string a {@link java.lang.String} object
   * @param datasetDO a {@link org.damap.base.rest.dmp.domain.DatasetDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.DatasetDO} object
   */
  public DatasetDO mapAtoB(String propertyName, String string, DatasetDO datasetDO) {
    switch (propertyName) {
      case "description":
        if (datasetDO.getDescription() == null) datasetDO.setDescription(string);
        else datasetDO.setDescription(datasetDO.getDescription() + ' ' + string);
        break;
      case "size":
        try {
          Long size = Long.valueOf(string);
          datasetDO.setSize(size);
        } catch (NumberFormatException ignored) {
          // Ignore
        }
        break;
      case "dateofacceptance":
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
          datasetDO.setStartDate(formatter.parse(string));
        } catch (ParseException ignored) {
          // Ignore
        }
        break;
      default:
    }

    return datasetDO;
  }

  private EDataAccessType getDataAccessType(String bestAccessRight) {
    switch (bestAccessRight) {
      case "open":
        return EDataAccessType.OPEN;
      case "restricted":
        return EDataAccessType.RESTRICTED;
      case "closed":
        return EDataAccessType.CLOSED;
      default:
        return null;
    }
  }

  private void addType(EDataType type, List<EDataType> types) {
    if (!types.contains(type)) types.add(type);
  }
}
