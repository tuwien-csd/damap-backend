package at.ac.tuwien.rest.projectdatabase.service;

import at.ac.tuwien.rest.addressbook.dto.DmpPerson;
import at.ac.tuwien.rest.addressbook.dto.OrganisationalUnit;
import at.ac.tuwien.rest.addressbook.dto.OrganisationalUnitDetails;
import at.ac.tuwien.rest.addressbook.dto.PersonDetails;
import at.ac.tuwien.rest.addressbook.service.AddressBookMapperService;
import at.ac.tuwien.rest.addressbook.service.AddressBookService;
import at.ac.tuwien.rest.projectdatabase.dto.ProjectDetails;
import at.ac.tuwien.rest.projectdatabase.dto.ProjectOverview;
import at.ac.tuwien.rest.projectdatabase.dto.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;


@ApplicationScoped
public class ProjectDatabaseServiceImpl implements ProjectDatabaseService {

    private static final Logger log = LoggerFactory.getLogger(ProjectDatabaseServiceImpl.class);

    @Inject
    @RestClient
    private AddressBookService addressBookService;

    @Inject
    private AddressBookMapperService addressBookMapperService;

    @Inject
    @RestClient
    private ProjectDatabaseRestService projectDatabaseRestService;

    @Override
    public List<ProjectOverview> getProjectSuggestionsForPerson(String personId) {

        Map<String, ProjectOverview> projects = new HashMap<>();

        PersonDetails personDetails = addressBookService.getPersonDetailsById(personId);
        personDetails.getEmploymentList().forEach(employment -> {
            OrganisationalUnit orgUnit = employment.getOrganisationalUnit();
            OrganisationalUnitDetails details = addressBookService.getOrgDetailsById(orgUnit.getId());
            projectDatabaseRestService.getProjectsByOrgCriteria(details.getId().toString(), personId).getProject().forEach(projectOverview -> projects.put(projectOverview.getProjectId(), projectOverview));
        });

        return new ArrayList<>(projects.values());
    }

    @Override
    public List<DmpProjectMember> getProjectStaff(String projectId) {
        ProjectDetails projectDetails = projectDatabaseRestService.getProjectDetails(projectId).getProject();
        List<DmpProjectMember> dmpProjectMemberList = new ArrayList<>();

        projectDetails.getInstitutes().getInstitute().forEach(institute -> institute.getProjectMembers().getProjectMember().forEach(projectMember -> {
            DmpPerson person = addressBookMapperService.getPersonById(projectMember.getTid());
            dmpProjectMemberList.add(DmpProjectMember.fromProjectMemberAndDmpPerson(projectMember, person));
        }));
        return dmpProjectMemberList;

    }
}
