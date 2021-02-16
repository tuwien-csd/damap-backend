package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.rest.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.domain.PersonDO;
import at.ac.tuwien.damap.rest.domain.ProjectDO;

public class DmpListItemDOMapper {

    public static void mapAtoB(Access access, Dmp dmp, DmpListItemDO dmpListItemDO) {
        dmpListItemDO.setId(dmp.id);
        dmpListItemDO.setTitle(dmp.getTitle());
        dmpListItemDO.setCreated(dmp.getCreated());
        dmpListItemDO.setModified(dmp.getModified());
        dmpListItemDO.setDescription(dmp.getDescription());
        dmpListItemDO.setAccessType(access.getRole());

        PersonDO contactDO = new PersonDO();
        PersonDOMapper.mapAtoB(dmp.getContact(), contactDO);
        dmpListItemDO.setContact(contactDO);

        ProjectDO projectDO = new ProjectDO();
        ProjectDOMapper.mapAtoB(dmp.getProject(), projectDO);
        dmpListItemDO.setProject(projectDO);
    }
}
