package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DmpListItemDOMapper {

    public DmpListItemDO mapEntityToDO(Access access, Dmp dmp, DmpListItemDO dmpListItemDO) {
        dmpListItemDO.setId(dmp.id);
        dmpListItemDO.setTitle(dmp.getTitle());
        dmpListItemDO.setCreated(dmp.getCreated());
        dmpListItemDO.setModified(dmp.getModified());
        dmpListItemDO.setDescription(dmp.getDescription());
        if (access != null) {
            dmpListItemDO.setAccessType(access.getRole());
        }

        if (dmp.getContact() != null) {
            ContributorDO contactDO = new ContributorDO();
            ContributorDOMapper.mapEntityToDO(dmp.getContact(), contactDO);
            dmpListItemDO.setContact(contactDO);
        }

        if (dmp.getProject() != null) {
            ProjectDO projectDO = new ProjectDO();
            ProjectDOMapper.mapEntityToDO(dmp.getProject(), projectDO);
            dmpListItemDO.setProject(projectDO);
        }

        return dmpListItemDO;
    }
}
