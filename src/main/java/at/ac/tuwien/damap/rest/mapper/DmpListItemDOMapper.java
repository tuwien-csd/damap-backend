package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.rest.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.domain.PersonDO;
import at.ac.tuwien.damap.rest.domain.ProjectDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DmpListItemDOMapper {

    public DmpListItemDO mapEntityToDO(Access access, Dmp dmp, DmpListItemDO dmpListItemDO) {
        dmpListItemDO.setId(dmp.id);
        dmpListItemDO.setTitle(dmp.getTitle());
        dmpListItemDO.setCreated(dmp.getCreated());
        dmpListItemDO.setModified(dmp.getModified());
        dmpListItemDO.setDescription(dmp.getDescription());
        dmpListItemDO.setAccessType(access.getRole());

        if (dmp.getContact() != null) {
            PersonDO contactDO = new PersonDO();
            PersonDOMapper.mapEntityToDO(dmp.getContact(), contactDO);
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
