package at.ac.tuwien.damap.rest.openaire.mapper;

import at.ac.tuwien.damap.enums.EDataAccessType;
import at.ac.tuwien.damap.enums.EDataSource;
import at.ac.tuwien.damap.enums.EDataType;
import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import eu.openaire.oaf.QualifierType;
import eu.openaire.oaf.Result;
import eu.openaire.oaf.StructuredPropertyElementType;
import generated.Response;
import lombok.experimental.UtilityClass;

import javax.xml.bind.JAXBElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@UtilityClass
public class OpenAireMapper {

    public DatasetDO mapAtoB(String doi, Response openaireResponse, DatasetDO datasetDO) {
        datasetDO.setSource(EDataSource.REUSED);
        try {
            Result result = openaireResponse.getResults().getResult().getMetadata().getEntity().getResult();
            List<JAXBElement<?>> elements = result.getCreatorOrResulttypeOrLanguage();
            for (JAXBElement<?> element : elements) {
                if (element.getDeclaredType() == StructuredPropertyElementType.class)
                    datasetDO = mapAtoB(element.getName().getLocalPart(), (StructuredPropertyElementType) element.getValue(), datasetDO);
                else if (element.getDeclaredType() == QualifierType.class)
                    datasetDO = mapAtoB(element.getName().getLocalPart(), (QualifierType) element.getValue(), datasetDO);
                else if (element.getDeclaredType() == String.class)
                    datasetDO = mapAtoB(element.getName().getLocalPart(), (String) element.getValue(), datasetDO);
            }
        } catch (NullPointerException e) {
            // Return null if no result present
            return null;
        }
        IdentifierDO identifierDO = new IdentifierDO();
        identifierDO.setType(EIdentifierType.DOI);
        identifierDO.setIdentifier(doi);
        datasetDO.setDatasetId(identifierDO);
        return datasetDO;
    }

    public DatasetDO mapAtoB(String propertyName, StructuredPropertyElementType elementType, DatasetDO datasetDO) {
        if ("title".equals(propertyName)) {
            if (datasetDO.getTitle() == null)
                datasetDO.setTitle(elementType.getValue());
            else
                datasetDO.setTitle(datasetDO.getTitle() + ' ' + elementType.getValue());
        }
        return datasetDO;
    }

    public DatasetDO mapAtoB(String propertyName, QualifierType qualifierType, DatasetDO datasetDO) {
        if ("bestaccessright".equals(propertyName)) {
            datasetDO.setDataAccess(getDataAccessType(qualifierType.getClassid().toLowerCase()));
        } else if ("resourcetype".equals(propertyName)) {
            List<EDataType> types = datasetDO.getType();
            String type = qualifierType.getClassname().toLowerCase();
            switch (type) {
                case "image":
                    types.add(EDataType.IMAGES);
                    break;
                case "film":
                case "sound":
                    types.add(EDataType.AUDIOVISUAL_DATA);
                    break;
                case "software":
                    types.add(EDataType.SOFTWARE_APPLICATIONS);
                    break;
                case "text":
                    types.add(EDataType.PLAIN_TEXT);
                    break;
                default:
                    types.add(EDataType.OTHER);
                    break;
            }
        }
        return datasetDO;
    }

    public DatasetDO mapAtoB(String propertyName, String string, DatasetDO datasetDO) {
        switch (propertyName) {
            case "description":
                if (datasetDO.getDescription() == null)
                    datasetDO.setDescription(string);
                else
                    datasetDO.setDescription(datasetDO.getDescription() + ' ' + string);
                break;
            case "size":
                Long size = Long.valueOf(string);
                datasetDO.setSize(size);
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
}
