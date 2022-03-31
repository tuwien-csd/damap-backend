package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import lombok.extern.jbosslog.JBossLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

@JBossLog
@QuarkusTest
public class ValidatorTest {

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
