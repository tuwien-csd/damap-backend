package at.ac.tuwien.damap.r3data.mapper;

import at.ac.tuwien.damap.r3data.dto.RepositoryDetails;
import lombok.experimental.UtilityClass;
import org.re3data.schema._2_2.Languages;
import org.re3data.schema._2_2.Re3Data;
import org.re3data.schema._2_2.Yesno;

import java.util.ArrayList;

@UtilityClass
public class RepositoryMapper {

    public RepositoryDetails mapToRepositoryDetails(Re3Data re3Data, String id) {
        RepositoryDetails repositoryDetails = new RepositoryDetails();

        if (re3Data.getRepository().isEmpty()) {
            return repositoryDetails;
        }

        Re3Data.Repository repo = re3Data.getRepository().get(0);
        repositoryDetails.setId(id);
        repositoryDetails.setName(repo.getRepositoryName().getValue());
        repositoryDetails.setRepositoryURL(repo.getRepositoryURL());
        repositoryDetails.setDescription(repo.getDescription().getValue());
        repositoryDetails.setVersioning(mapYesNoToBoolean(repo.getVersioning()));
        repositoryDetails.setRepositoryIdentifier(repo.getRepositoryIdentifier());

        if (!repo.getRepositoryLanguage().isEmpty()) {
            ArrayList<String> languages = new ArrayList<>();
            for (Languages lang : repo.getRepositoryLanguage()) {
                languages.add(lang.value());
            }
            repositoryDetails.setRepositoryLanguages(languages);
        }

        if (!repo.getMetadataStandard().isEmpty()) {
            ArrayList<String> metadata = new ArrayList<>();
            for (Re3Data.Repository.MetadataStandard mds : repo.getMetadataStandard()) {
                metadata.add(mds.getMetadataStandardName().getValue().value());
            }
            repositoryDetails.setMetadataStandards(metadata);
        }

        if (!repo.getContentType().isEmpty()) {
            ArrayList<String> types = new ArrayList<>();
            for (Re3Data.Repository.ContentType ct : repo.getContentType()) {
                types.add(ct.getValue().value());
            }
            repositoryDetails.setContentTypes(types);
        }

        return repositoryDetails;
    }

    @javax.annotation.Nullable
    public Boolean mapYesNoToBoolean(Yesno value) {
        if (value == null) {
            return null;
        }
        return value.value().equals(Yesno.YES.value());
    }
}
