package at.ac.tuwien.rest.projectdatabase.service;

import at.ac.tuwien.rest.projectdatabase.dto.DmpProjectMember;
import at.ac.tuwien.rest.projectdatabase.dto.ProjectOverview;

import java.util.List;


public interface ProjectDatabaseService {

    List<ProjectOverview> getProjectSuggestionsForPerson(String personId);

    List<DmpProjectMember> getProjectStaff(String projectId);
}
