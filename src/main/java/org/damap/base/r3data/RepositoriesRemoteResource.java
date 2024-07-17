package org.damap.base.r3data;

import generated.Repository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
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
  List<Repository> getAll();

  /**
   * getById.
   *
   * @param id a {@link java.lang.String} object
   * @return a {@link org.re3data.schema._2_2.Re3Data} object
   */
  @GET
  @Path("/v1/repository/{id}")
  Re3Data getById(@PathParam String id);

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
  List<Repository> search(
      @QueryParam("subjects[]") List<String> subjects,
      @QueryParam("contentTypes[]") List<String> contentTypes,
      @QueryParam("countries[]") List<String> countries,
      @QueryParam("certificates[]") List<String> certificates,
      @QueryParam("pidSystems[]") List<String> pidSystems,
      @QueryParam("aidSystems[]") List<String> aidSystems,
      @QueryParam("repositoryAccess[]") List<String> repositoryAccess,
      @QueryParam("dataAccess[]") List<String> dataAccess,
      @QueryParam("dataUpload[]") List<String> dataUpload,
      @QueryParam("dataLicenses[]") List<String> dataLicenses,
      @QueryParam("repositoryTypes[]") List<String> repositoryTypes,
      @QueryParam("institutionTypes[]") List<String> institutionTypes,
      @QueryParam("versioning[]") List<String> versioning,
      @QueryParam("metadataStandards[]") List<String> metadataStandards);
}
