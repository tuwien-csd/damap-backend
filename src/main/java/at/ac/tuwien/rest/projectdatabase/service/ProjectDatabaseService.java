package at.ac.tuwien.rest.projectdatabase.service;

import at.ac.tuwien.damap.rest.domain.ProjectDO;
import at.ac.tuwien.rest.addressbook.dto.DmpPerson;
import at.ac.tuwien.rest.addressbook.dto.OrganisationalUnit;
import at.ac.tuwien.rest.addressbook.dto.OrganisationalUnitDetails;
import at.ac.tuwien.rest.addressbook.dto.PersonDetails;
import at.ac.tuwien.rest.addressbook.service.AddressBookMapperService;
import at.ac.tuwien.rest.addressbook.service.AddressBookService;
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
    private AddressBookService addressBookService;

    @Inject
    private AddressBookMapperService addressBookMapperService;

    @Inject
    @RestClient
    private ProjectDatabaseRestService projectDatabaseRestService;

    /*
        returns a list of projects lead by "personId"
        todo expand to also include all projects from the persons orgunits
     */
    public List<ProjectDO> getProjectSuggestionsForPerson(String personId) {

        List<ProjectDO> projects = new ArrayList<>();
        PersonDetails personDetails = addressBookService.getPersonDetailsById(personId);
        personDetails.getEmploymentList().forEach(employment -> {
            OrganisationalUnit orgUnit = employment.getOrganisationalUnit();
            OrganisationalUnitDetails details = addressBookService.getOrgDetailsById(orgUnit.getId());
            projectDatabaseRestService.getProjectsByCriteria(details.getId().toString(), personId).forEach(projectDTO -> {
                ProjectDO projectDO = new ProjectDO();
                ProjectDTOMapper.mapAtoB(projectDTO, projectDO);
                projects.add(projectDO);
            });
        });
        return projects;
    }

    public List<DmpProjectMember> getProjectStaff(String projectId) {
        ProjectDTO projectDTO  = projectDatabaseRestService.getProjectDetails(projectId);
        List<DmpProjectMember> dmpProjectMemberList = new ArrayList<>();

        projectDTO.getProjectMembers().forEach(projectMemberDTO -> {
            DmpPerson person = addressBookMapperService.getPersonById(String.valueOf(projectMemberDTO.getId()));
            dmpProjectMemberList.add(DmpProjectMember.fromProjectMemberAndDmpPerson(projectMemberDTO, person));
        });
        return dmpProjectMemberList;

    }
}
