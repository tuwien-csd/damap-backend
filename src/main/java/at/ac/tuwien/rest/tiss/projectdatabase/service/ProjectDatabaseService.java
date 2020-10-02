package at.ac.tuwien.rest.tiss.projectdatabase.service;

import at.ac.tuwien.rest.tiss.addressbook.dto.Person;
import at.ac.tuwien.rest.tiss.projectdatabase.dto.ProjectMemberDetails;
import at.ac.tuwien.rest.tiss.projectdatabase.dto.ProjectOverview;

import java.util.List;


public interface ProjectDatabaseService {

    List<ProjectOverview> getProjectSuggestionsForPerson(Person person);

    List<ProjectMemberDetails> getProjectStaff(String projectId);
}
