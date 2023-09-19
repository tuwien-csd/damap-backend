package at.ac.tuwien.damap.r3data;

import generated.Repository;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.re3data.schema._2_2.Re3Data;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(configKey = "rest.r3data.repositories")
@Produces(MediaType.TEXT_XML)
public interface RepositoriesRemoteResource {

    @GET
    @Path("/v1/repositories")
    List<Repository> getAll();

    @GET
    @Path("/v1/repository/{id}")
    Re3Data getById(@PathParam String id);

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
            @QueryParam("metadataStandards[]") List<String> metadataStandards
            );

}
