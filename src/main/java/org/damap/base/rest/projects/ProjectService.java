package org.damap.base.rest.projects;

import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;
import org.damap.base.rest.base.ResultList;
import org.damap.base.rest.base.service.ServiceRead;
import org.damap.base.rest.base.service.ServiceSearch;
import org.damap.base.rest.dmp.domain.ContributorDO;
import org.damap.base.rest.dmp.domain.ProjectDO;

/** ProjectService interface. */
public interface ProjectService extends ServiceSearch<ProjectDO>, ServiceRead<ProjectDO> {

  /**
   * getProjectStaff.
   *
   * @param projectId a {@link java.lang.String} object
   * @return a {@link java.util.List} object
   */
  List<ContributorDO> getProjectStaff(String projectId);

  /**
   * getProjectSupplement.
   *
   * @param projectId a {@link java.lang.String} object
   * @return a {@link org.damap.base.rest.projects.ProjectSupplementDO} object
   */
  ProjectSupplementDO getProjectSupplement(String projectId);

  /**
   * getProjectLeader.
   *
   * @param projectId a {@link java.lang.String} object
   * @return a {@link org.damap.base.rest.dmp.domain.ContributorDO} object
   */
  ContributorDO getProjectLeader(String projectId);

  /**
   * getRecommended.
   *
   * @param queryParams a {@link jakarta.ws.rs.core.MultivaluedMap} object
   * @return a {@link org.damap.base.rest.base.ResultList} object
   */
  ResultList<ProjectDO> getRecommended(MultivaluedMap<String, String> queryParams);
}
