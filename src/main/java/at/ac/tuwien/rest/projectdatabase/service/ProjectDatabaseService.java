package at.ac.tuwien.rest.projectdatabase.service;

import at.ac.tuwien.damap.rest.domain.ProjectDO;
import at.ac.tuwien.damap.rest.domain.ProjectMemberDO;
import at.ac.tuwien.damap.rest.domain.PersonDO;
import at.ac.tuwien.rest.addressbook.dto.OrganisationalUnit;
import at.ac.tuwien.rest.addressbook.dto.PersonDTO;
import at.ac.tuwien.rest.addressbook.mapper.PersonDTOMapper;
import at.ac.tuwien.rest.addressbook.service.AddressBookRestService;
import at.ac.tuwien.rest.projectdatabase.dto.*;
import at.ac.tuwien.rest.projectdatabase.mapper.ProjectDTOMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;


@ApplicationScoped
public class ProjectDatabaseService {

    private static final Logger log = LoggerFactory.getLogger(ProjectDatabaseService.class);

    @Inject
    @RestClient
    private AddressBookRestService addressBookRestService;

    @Inject
    @RestClient
    private ProjectDatabaseRestService projectDatabaseRestService;

    /*
        returns a list of projects lead by "personId"
        todo expand to also include all projects from the persons orgunits
     */
    public List<ProjectDO> getProjectSuggestionsForPerson(String personId) {

        List<ProjectDO> projects = new ArrayList<>();
        PersonDTO personDTO = addressBookRestService.getPersonDetailsById(personId);
        personDTO.getEmploymentList().forEach(employment -> {
            OrganisationalUnit orgUnit = employment.getOrganisationalUnit();
            projectDatabaseRestService.getProjectsByCriteria(orgUnit.getId().toString(), personId).forEach(projectDTO -> {
                ProjectDO projectDO = new ProjectDO();
                ProjectDTOMapper.mapAtoB(projectDTO, projectDO);
                projects.add(projectDO);
            });
        });
        return projects;
    }

    public List<ProjectMemberDO> getProjectStaff(String projectId) {
        ProjectDTO projectDTO  = projectDatabaseRestService.getProjectDetails(projectId);
        List<ProjectMemberDO> projectMemberList = new ArrayList<>();

        projectDTO.getProjectMembers().forEach(projectMemberDTO -> {
            PersonDTO personDTO = addressBookRestService.getPersonDetailsById(String.valueOf(projectMemberDTO.getId()));
            PersonDO personDO = new PersonDO();
            PersonDTOMapper.mapAtoB(personDTO, personDO);
            ProjectMemberDO projectMemberDO = new ProjectMemberDO();
            projectMemberDO.setPerson(personDO);
            projectMemberDO.setRoleInProject(projectMemberDTO.getRole());
            projectMemberList.add(projectMemberDO);
        });
        return projectMemberList;

    }
}
