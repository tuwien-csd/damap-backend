package org.damap.base.rest.storage;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.experimental.UtilityClass;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.domain.InternalStorage;
import org.damap.base.domain.InternalStorageTranslation;
import org.damap.base.repo.InternalStorageRepo;
import org.damap.base.repo.InternalStorageTranslationRepo;

@UtilityClass
@JBossLog
public class InternalStorageTranslationValidator {

  public void validateForCreation(
      InternalStorageTranslationDO internalStorageTranslationDO,
      InternalStorageRepo internalStorageRepo,
      InternalStorageTranslationRepo internalStorageTranslationRepo)
      throws ClientErrorException {
    log.info("Validating internal storage translation for creation");

    // Check if no translation for the same language exists
    if (internalStorageTranslationRepo.existsTranslationForStorageIdAndLanguageCode(
        internalStorageTranslationDO.getStorageId(),
        internalStorageTranslationDO.getLanguageCode())) {
      throw new ClientErrorException(
          "Translation for language code "
              + internalStorageTranslationDO.getLanguageCode()
              + " already exists",
          Response.Status.BAD_REQUEST);
    }

    InternalStorageTranslationValidator.validationCommon(
        internalStorageTranslationDO, internalStorageRepo);
  }

  public void validateForUpdate(
      String id,
      InternalStorageTranslationDO internalStorageTranslationDO,
      InternalStorageTranslationRepo internalStorageTranslationRepo,
      InternalStorageRepo internalStorageRepo)
      throws ClientErrorException {
    log.info("Validating internal storage translation for update");

    InternalStorageTranslation internalStorageTranslation =
        internalStorageTranslationRepo.findById(Long.parseLong(id));

    if (internalStorageTranslation == null) {
      throw new NotFoundException("No internal storage translation with ID " + id + " found");
    }

    if (internalStorageTranslationRepo.existsTranslationForStorageIdAndLanguageCodeExceptId(
        internalStorageTranslationDO.getStorageId(),
        internalStorageTranslationDO.getLanguageCode(),
        Long.parseLong(id))) {
      throw new ClientErrorException(
          "Translation for language code "
              + internalStorageTranslationDO.getLanguageCode()
              + " already exists",
          Response.Status.BAD_REQUEST);
    }

    InternalStorageTranslationValidator.validationCommon(
        internalStorageTranslationDO, internalStorageRepo);
  }

  public void validationCommon(
      InternalStorageTranslationDO internalStorageTranslationDO,
      InternalStorageRepo internalStorageRepo)
      throws ClientErrorException {
    log.info("Validating common internal storage translation");

    InternalStorage internalStorage =
        internalStorageRepo.findById(internalStorageTranslationDO.getStorageId());

    if (internalStorage == null) {
      throw new NotFoundException(
          "No internal storage found for id " + internalStorageTranslationDO.getStorageId());
    }

    // Check if language code is 'deu' or 'eng'
    if (!internalStorageTranslationDO.getLanguageCode().equals("deu")
        && !internalStorageTranslationDO.getLanguageCode().equals("eng")) {
      throw new ClientErrorException(
          "Language code must be 'deu' or 'eng'", Response.Status.BAD_REQUEST);
    }
  }
}
