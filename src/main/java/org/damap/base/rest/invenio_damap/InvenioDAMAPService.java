package org.damap.base.rest.invenio_damap;

import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import java.text.MessageFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.List;
import org.damap.base.rest.dmp.domain.DatasetDO;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.dmp.service.DmpService;
import org.damap.base.rest.madmp.dto.Dataset;
import org.damap.base.rest.version.VersionDO;
import org.damap.base.rest.version.VersionService;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class InvenioDAMAPService {

  protected DmpService dmpService;
  protected VersionService versionService;

  @Inject
  InvenioDAMAPService(DmpService dmpService, VersionService versionService) {
    this.dmpService = dmpService;
    this.versionService = versionService;
  }

  // Resolves user identity based on the DMPs and returns dmp list and list of
  // identifiers if successful.
  public SimpleEntry<List<DmpDO>, String> resolveDmpsAndIds(JsonWebToken jwt) {
    JsonObject invenioDamapClaim = jwt.getClaim("invenio-damap");
    if (invenioDamapClaim == null) {
      throw new UnauthorizedException("Missing invenio-damap claim in jwt.");
    }

    JsonObject identifiers = invenioDamapClaim.getJsonObject("identifiers");
    if (identifiers == null || identifiers.isEmpty()) {
      throw new UnauthorizedException("No valid authentication schema was provided.");
    }

    List<DmpDO> personDmpList = null;
    String matchingIdentifier = null;

    for (String key : identifiers.keySet()) {
      String identifier = identifiers.getString(key);
      List<DmpDO> dmps = dmpService.getDmpDOListByPersonId(identifier);

      // Check if returned DMPs list is empty.
      // If yes, no resolving can take place, move to the next identifier.
      if (dmps.isEmpty()) continue;

      // If a second identifier matches, something went wrong
      if (matchingIdentifier == null) {
        matchingIdentifier = identifier;
        personDmpList = dmps;
      } else {
        throw new UnauthorizedException("Mismatch in resolved identities.");
      }
    }

    // Check if identifier is null. This means that no resolving was possible.
    if (matchingIdentifier == null)
      throw new UnauthorizedException("Identities could not be resolved.");

    return new SimpleEntry<>(personDmpList, matchingIdentifier);
  }

  @Transactional
  public DmpDO addDataSetToDMP(long dmpId, Dataset dataset) {

    DmpDO dmpDO = dmpService.getDmpById(dmpId);
    var datasetDO =
        dmpDO.getDatasets().stream()
            .filter(
                ds -> {
                  var localIdentifier = ds.getDatasetId();
                  var externalIdentifier = dataset.getDatasetId();

                  if (localIdentifier == null || externalIdentifier == null) {
                    return false;
                  }

                  return localIdentifier.getIdentifier() != null
                      && externalIdentifier.getIdentifier() != null
                      && localIdentifier.getType() != null
                      && externalIdentifier.getType() != null
                      && localIdentifier.getIdentifier().equals(externalIdentifier.getIdentifier())
                      && localIdentifier
                          .getType()
                          .toString()
                          .equalsIgnoreCase(externalIdentifier.getType().name());
                })
            .findFirst()
            .orElse(null);

    if (datasetDO == null) {
      datasetDO = new DatasetDO();
      dmpDO.getDatasets().add(datasetDO);
    }
    InvenioDamapResourceMapper.mapMaDMPDatasetToDatasetDO(dataset, datasetDO, dmpDO);
    dmpDO = dmpService.update(dmpDO);

    VersionDO version = new VersionDO();
    version.setDmpId(dmpId);
    version.setVersionName(
        MessageFormat.format("Added dataset `{0}` from remote datasource", dataset.getTitle()));
    version.setVersionDate(new Date());
    versionService.create(version);

    return dmpDO;
  }
}
