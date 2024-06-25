package org.damap.base.rest.dmp.domain;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import org.damap.base.util.TestDOFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class EntityAndMapperTest {

  @Inject TestDOFactory testDOFactory;

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
