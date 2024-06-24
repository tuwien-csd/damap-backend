package org.damap.base.rest.dmp.domain;

import org.damap.base.rest.dmp.service.DmpService;
import org.damap.base.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import lombok.extern.jbosslog.JBossLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;

@JBossLog
@QuarkusTest
class ValidatorTest {

    @Inject
    TestDOFactory testDOFactory;

    @Inject
    DmpService dmpService;

    @Test
    @TestSecurity(authorizationEnabled = false)
    void validatorTriggerTest() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        //set title with length of 300, limit is 255
        dmpDO.setTitle("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            dmpService.update(dmpDO);
        });
    }
}
