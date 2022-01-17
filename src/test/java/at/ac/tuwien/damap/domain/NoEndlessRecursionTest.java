package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
class EntityAndMapperTest {

    @Inject
    TestDOFactory testDOFactory;

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testToStringEqualsAndHash() {
        final DmpDO first = testDOFactory.getOrCreateTestDmpDO();
        final DmpDO second = testDOFactory.getOrCreateTestDmpDO();
        Assertions.assertTrue(first.toString().contains(first.getClass().getSimpleName() + "("));

        Assertions.assertNotSame(first, second);
        Assertions.assertEquals(first, second);
        Assertions.assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testToStringEqualsAndHashEmpty() {
        final DmpDO first = testDOFactory.getOrCreateTestDmpDOEmpty();
        final DmpDO second = testDOFactory.getOrCreateTestDmpDOEmpty();
        Assertions.assertTrue(first.toString().contains(first.getClass().getSimpleName() + "("));

        Assertions.assertNotSame(first, second);
        Assertions.assertEquals(first, second);
        Assertions.assertEquals(first.hashCode(), second.hashCode());
    }
}

