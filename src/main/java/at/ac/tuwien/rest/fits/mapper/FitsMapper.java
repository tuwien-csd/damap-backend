package at.ac.tuwien.rest.fits.mapper;

import at.ac.tuwien.damap.domain.Dataset;
import edu.harvard.fits.Fits;
import edu.harvard.fits.FitsMetadataType;
import edu.harvard.fits.IdentificationType;
import lombok.experimental.UtilityClass;

import javax.xml.bind.JAXBElement;
import java.util.Optional;

@UtilityClass
public class FitsMapper {

    // TODO: Replace with enum?
    static final String[] FILE_TYPES = new String[]{
            "STANDARD_OFFICE_DOCUMENTS",
            "NETWORKBASED_DATA",
            "DATABASES",
            "IMAGES",
            "STRUCTURED_GRAPHICS",
            "AUDIOVISUAL_DATA",
            "SCIENTIFIC_STATISTICAL_DATA",
            "RAW_DATA",
            "PLAIN_TEXT",
            "STRUCTURED_TEXT",
            "ARCHIVED_DATA",
            "SOFTWARE_APPLICATIONS",
            "SOURCE_CODE",
            "CONFIGURATION_DATA",
            "OTHER"
    };

    public Dataset mapAtoB(Fits fits, Dataset dataset) {
        dataset.setSize(getSize(fits));
        IdentificationType.Identity identity = getMajorityVoteIdentity(fits);
        dataset.setType(mapFileFormat(identity));

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
    private String mapFileFormat(IdentificationType.Identity identity) {

        if (identity == null) {
            return null;
        }

        String format = identity.getFormat();
        String mimetype = identity.getMimetype();

        if(format == null) {
            return FILE_TYPES[14];
        }

        switch (format) {
            case "Portable Network Graphics":
            case "JPEG EXIF":
                return FILE_TYPES[3];
        }

        switch (mimetype) {
            case "application/msword":
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
            case "application/vnd.ms-excel":
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
            case "application/pdf":
                return FILE_TYPES[0];
            case "text/plain":
                return FILE_TYPES[8];
            case "application/gzip":
            case "application/java-archive":
            case "application/x-7z-compressed":
            case "application/zip":
            case "application/x-tar":
            case "application/vnd.rar":
            case "application/x-bzip":
            case "application/x-bzip2":
                return FILE_TYPES[10];
            case "text/javascript":
                return FILE_TYPES[11];
            default:
                // TODO: Return null instead of 'OTHER'?
                return FILE_TYPES[14];
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
