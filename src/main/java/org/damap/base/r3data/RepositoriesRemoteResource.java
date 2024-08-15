package org.damap.base.r3data;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.re3data.schema._2_2.Re3Data;

/** RepositoriesRemoteResource interface. */
@RegisterRestClient(configKey = "rest.r3data.repositories")
@Produces(MediaType.TEXT_XML)
public interface RepositoriesRemoteResource {

  /**
   * getAll.
   *
   * @return a {@link java.util.List} object
   */
  @GET
  @Path("/v1/repositories")
  generated.List getAll();

  /**
   * getById.
   *
   * @param id a {@link java.lang.String} object
   * @return a {@link org.re3data.schema._2_2.Re3Data} object
   */
  @GET
  @Path("/v1/repository/{id}")
  Re3Data getById(@RestPath String id);

  /**
   * search.
   *
   * @param subjects a {@link java.util.List} object
   * @param contentTypes a {@link java.util.List} object
   * @param countries a {@link java.util.List} object
   * @param certificates a {@link java.util.List} object
   * @param pidSystems a {@link java.util.List} object
   * @param aidSystems a {@link java.util.List} object
   * @param repositoryAccess a {@link java.util.List} object
   * @param dataAccess a {@link java.util.List} object
   * @param dataUpload a {@link java.util.List} object
   * @param dataLicenses a {@link java.util.List} object
   * @param repositoryTypes a {@link java.util.List} object
   * @param institutionTypes a {@link java.util.List} object
   * @param versioning a {@link java.util.List} object
   * @param metadataStandards a {@link java.util.List} object
   * @return a {@link java.util.List} object
   */
  @GET
  @Path("/beta/repositories")
  generated.List search(
      @RestQuery("subjects[]") List<String> subjects,
      @RestQuery("contentTypes[]") List<String> contentTypes,
      @RestQuery("countries[]") List<String> countries,
      @RestQuery("certificates[]") List<String> certificates,
      @RestQuery("pidSystems[]") List<String> pidSystems,
      @RestQuery("aidSystems[]") List<String> aidSystems,
      @RestQuery("repositoryAccess[]") List<String> repositoryAccess,
      @RestQuery("dataAccess[]") List<String> dataAccess,
      @RestQuery("dataUpload[]") List<String> dataUpload,
      @RestQuery("dataLicenses[]") List<String> dataLicenses,
      @RestQuery("repositoryTypes[]") List<String> repositoryTypes,
      @RestQuery("institutionTypes[]") List<String> institutionTypes,
      @RestQuery("versioning[]") List<String> versioning,
      @RestQuery("metadataStandards[]") List<String> metadataStandards);
}
