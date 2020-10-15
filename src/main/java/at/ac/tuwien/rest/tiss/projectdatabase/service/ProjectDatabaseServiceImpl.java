package at.ac.tuwien.rest.tiss.projectdatabase.service;

import at.ac.tuwien.rest.tiss.addressbook.dto.OrganisationalUnit;
import at.ac.tuwien.rest.tiss.addressbook.dto.OrganisationalUnitDetails;
import at.ac.tuwien.rest.tiss.addressbook.dto.Person;
import at.ac.tuwien.rest.tiss.addressbook.dto.PersonDetails;
import at.ac.tuwien.rest.tiss.addressbook.service.AddressBookService;
import at.ac.tuwien.rest.tiss.projectdatabase.dto.*;
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
    public List<ProjectMemberDetails> getProjectStaff(String projectId) {
        ProjectDetails projectDetails = projectDatabaseRestService.getProjectDetails(projectId).getProject();
        List<ProjectMemberDetails> projectMemberDetailsList = new ArrayList<>();

        projectDetails.getInstitutes().getInstitute().forEach(institute -> institute.getProjectMembers().getProjectMember().forEach(projectMember -> {
            PersonDetails personDetails = addressBookService.getPersonDetailsById(projectMember.getTid());
            projectMemberDetailsList.add(ProjectMemberDetails.fromProjectMemberAndPersonDetails(projectMember, personDetails));
        }));
        return projectMemberDetailsList;
    }
}
