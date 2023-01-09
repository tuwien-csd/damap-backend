package at.ac.tuwien.damap.rest.fits.mapper;

import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.enums.EAccessRight;
import at.ac.tuwien.damap.enums.EDataAccessType;
import at.ac.tuwien.damap.enums.EDataSource;
import at.ac.tuwien.damap.enums.EDataType;
import edu.harvard.fits.Fits;
import edu.harvard.fits.FitsMetadataType;
import edu.harvard.fits.IdentificationType;
import lombok.experimental.UtilityClass;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class FitsMapper {

    public Dataset mapAtoB(Fits fits, Dataset dataset) {
        dataset.setSize(getSize(fits));
        IdentificationType.Identity identity = getMajorityVoteIdentity(fits);
        dataset.setType(mapFileFormat(identity));
        dataset.setSource(EDataSource.NEW);
        dataset.setDataAccess(EDataAccessType.OPEN);
        dataset.setLicense("https://creativecommons.org/licenses/by/4.0/");
        dataset.setSelectedProjectMembersAccess(EAccessRight.WRITE);
        dataset.setOtherProjectMembersAccess(EAccessRight.WRITE);
        dataset.setPublicAccess(EAccessRight.READ);

        return dataset;
    }

    /**
     * If FITS tools disagree on a files' identity there is a conflict. This methods returns
     * the identity most tools agree on, or the first one in case there is no majority vote.
     * @param fits Fits output
     * @return identity where most tools agree
     */
    private IdentificationType.Identity getMajorityVoteIdentity(Fits fits) {

        if(fits.getIdentification().getIdentity().size() == 1) {
            return fits.getIdentification().getIdentity().get(0);
        }
        else if(fits.getIdentification().getStatus().value().equals("CONFLICT")) {
            int maxNumberOfTools = 0;
            IdentificationType.Identity majorityVoteIdentity = null;
            for (IdentificationType.Identity identity : fits.getIdentification().getIdentity()) {
                if(identity.getTool().size() > maxNumberOfTools) {
                    maxNumberOfTools = identity.getTool().size();
                    majorityVoteIdentity = identity;
                }
            }
            return majorityVoteIdentity;
        }
        return null;
    }

    private Long getSize(Fits dto) {

        if(dto.getFileinfo() == null || dto.getFileinfo().getFileInfoElements().isEmpty()){
            return null;
        }

        Optional<JAXBElement<FitsMetadataType>> sizeElement =
                dto.getFileinfo().getFileInfoElements().stream().
                        filter(element -> element.getName().getLocalPart().equals("size")).findFirst();

        return sizeElement.map(fitsMetadataTypeJAXBElement -> mapSize(Long.parseLong(String.valueOf(fitsMetadataTypeJAXBElement.getValue().getValue())))).orElse(null);

    }

    // TODO: Add more file format mappings
    private List<EDataType> mapFileFormat(IdentificationType.Identity identity) {
        List<EDataType> type = new ArrayList<>();

        if (identity == null) {
            return type;
        }

        String format = identity.getFormat();
        String mimetype = identity.getMimetype();

        if (format == null) {
            type.add(EDataType.OTHER);
            return type;
        }

        switch (format) {
            case "Graphics Interchange Format":
            case "JPEG File Interchange Format":
            case "Portable Network Graphics":
            case "JPEG EXIF":
                type.add(EDataType.IMAGES);
                return type;
            default:
                // do nothing
                break;
        }

        switch (mimetype) {
            case "image/jpeg":
            case "image/png":
            case "image/tiff":
            case "image/gif":
                type.add(EDataType.IMAGES);
                return type;
            case "application/msword":
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
            case "application/vnd.ms-excel":
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
            case "application/pdf":
                type.add(EDataType.STANDARD_OFFICE_DOCUMENTS);
                return type;
            case "text/plain":
                type.add(EDataType.PLAIN_TEXT);
                return type;
            case "application/gzip":
            case "application/java-archive":
            case "application/x-7z-compressed":
            case "application/zip":
            case "application/x-tar":
            case "application/vnd.rar":
            case "application/x-bzip":
            case "application/x-bzip2":
                type.add(EDataType.ARCHIVED_DATA);
                return type;
            case "text/javascript":
                type.add(EDataType.SOURCE_CODE);
                return type;
            default:
                return type;
        }
    }


    private Long mapSize(Long size) {
        if (size < 0L) {
            return -1L;
        }
        if (size <= 100000000L) {
            return 100000000L;
        }
        if (size <= 5000000000L) {
            return 5000000000L;
        }
        if (size <= 20000000000L) {
            return 20000000000L;
        }
        if (size <= 50000000000L) {
            return 50000000000L;
        }
        if (size <= 100000000000L) {
            return 100000000000L;
        }
        if (size <= 500000000000L) {
            return 500000000000L;
        }
        if (size <= 1000000000000L) {
            return 1000000000000L;
        }
        if (size <= 5000000000000L) {
            return 5000000000000L;
        }
        if (size <= 10000000000000L) {
            return 10000000000000L;
        }
        if (size <= 100000000000000L) {
            return 100000000000000L;
        }
        if (size <= 500000000000000L) {
            return 500000000000000L;
        }
        if (size <= 1000000000000000L) {
            return 1000000000000000L;
        }
        return 1000000000000001L;
    }
}
