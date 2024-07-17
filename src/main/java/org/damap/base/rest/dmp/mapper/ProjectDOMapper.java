package org.damap.base.rest.dmp.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.Funding;
import org.damap.base.domain.Identifier;
import org.damap.base.domain.Project;
import org.damap.base.rest.dmp.domain.FundingDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;
import org.damap.base.rest.dmp.domain.ProjectDO;

/** ProjectDOMapper class. */
@UtilityClass
public class ProjectDOMapper {

  /**
   * mapEntityToDO.
   *
   * @param project a {@link org.damap.base.domain.Project} object
   * @param projectDO a {@link org.damap.base.rest.dmp.domain.ProjectDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.ProjectDO} object
   */
  public ProjectDO mapEntityToDO(Project project, ProjectDO projectDO) {
    projectDO.setId(project.id);
    projectDO.setUniversityId(project.getUniversityId());
    projectDO.setTitle(project.getTitle());
    projectDO.setDescription(project.getDescription());
    projectDO.setStart(project.getStart());
    projectDO.setEnd(project.getEnd());

    if (project.getFunding() != null) {
      FundingDO fundingDO = new FundingDO();
      mapEntityToDO(project.getFunding(), fundingDO);
      projectDO.setFunding(fundingDO);
    }

    projectDO.setFunderSupported(checkFunderSupported(project));

    return projectDO;
  }

  /**
   * checkFunderSupported.
   *
   * @param project a {@link org.damap.base.domain.Project} object
   * @return a boolean
   */
  public boolean checkFunderSupported(Project project) {
    return project != null && project.getFunding() != null;
  }

  /**
   * mapEntityToDO.
   *
   * @param funding a {@link org.damap.base.domain.Funding} object
   * @param fundingDO a {@link org.damap.base.rest.dmp.domain.FundingDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.FundingDO} object
   */
  public FundingDO mapEntityToDO(Funding funding, FundingDO fundingDO) {
    fundingDO.setId(funding.id);
    fundingDO.setFundingStatus(funding.getFundingStatus());

    if (funding.getFunderIdentifier() != null) {
      IdentifierDO identifierFundingDO = new IdentifierDO();
      IdentifierDOMapper.mapEntityToDO(funding.getFunderIdentifier(), identifierFundingDO);
      fundingDO.setFunderId(identifierFundingDO);
    }

    if (funding.getGrantIdentifier() != null) {
      IdentifierDO identifierGrantDO = new IdentifierDO();
      IdentifierDOMapper.mapEntityToDO(funding.getGrantIdentifier(), identifierGrantDO);
      fundingDO.setGrantId(identifierGrantDO);
    }

    return fundingDO;
  }

  /**
   * mapDOtoEntity.
   *
   * @param projectDO a {@link org.damap.base.rest.dmp.domain.ProjectDO} object
   * @param project a {@link org.damap.base.domain.Project} object
   * @return a {@link org.damap.base.domain.Project} object
   */
  public Project mapDOtoEntity(ProjectDO projectDO, Project project) {
    if (projectDO.getId() != null) project.id = projectDO.getId();
    project.setUniversityId(projectDO.getUniversityId());
    project.setTitle(projectDO.getTitle());
    project.setDescription(projectDO.getDescription());
    project.setStart(projectDO.getStart());
    project.setEnd(projectDO.getEnd());

    if (projectDO.getFunding() != null) {
      Funding funding = new Funding();
      if (project.getFunding() != null) funding = project.getFunding();
      mapDOtoEntity(projectDO.getFunding(), funding);
      project.setFunding(funding);
    } else project.setFunding(null);

    return project;
  }

  /**
   * mapDOtoEntity.
   *
   * @param fundingDO a {@link org.damap.base.rest.dmp.domain.FundingDO} object
   * @param funding a {@link org.damap.base.domain.Funding} object
   * @return a {@link org.damap.base.domain.Funding} object
   */
  public Funding mapDOtoEntity(FundingDO fundingDO, Funding funding) {
    if (fundingDO.getId() != null) funding.id = fundingDO.getId();
    funding.setFundingStatus(fundingDO.getFundingStatus());

    if (fundingDO.getFunderId() != null) {
      Identifier identifierFunding = new Identifier();
      if (funding.getFunderIdentifier() != null) identifierFunding = funding.getFunderIdentifier();
      IdentifierDOMapper.mapDOtoEntity(fundingDO.getFunderId(), identifierFunding);
      funding.setFunderIdentifier(identifierFunding);
    } else funding.setFunderIdentifier(null);

    if (fundingDO.getGrantId() != null) {
      Identifier identifierGrant = new Identifier();
      if (funding.getGrantIdentifier() != null) identifierGrant = funding.getGrantIdentifier();
      IdentifierDOMapper.mapDOtoEntity(fundingDO.getGrantId(), identifierGrant);
      funding.setGrantIdentifier(identifierGrant);
    } else funding.setGrantIdentifier(null);

    return funding;
  }
}
