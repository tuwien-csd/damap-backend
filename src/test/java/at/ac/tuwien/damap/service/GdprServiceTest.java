package at.ac.tuwien.damap.service;

import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.rest.gdpr.domain.GdprResult;
import at.ac.tuwien.damap.rest.gdpr.service.GdprService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class GdprServiceTest {

    @Inject
    GdprService gdprService;

    @Inject
    TestDOFactory testDOFactory;

    @BeforeEach
    public void setup() {
        testDOFactory.getOrCreateTestDmpDO();
    }

    @Test
    void testGdprExtendedData_shouldReturnData() {
        List<GdprResult> result = gdprService.getGdprExtendedData("012345");

        assertThat(result.size(), greaterThanOrEqualTo(2));

        Optional<GdprResult> consent = result.stream()
                .filter(item -> item.getEntity().equals("Consent")).findFirst();
        assertTrue(consent.isPresent());

        Optional<GdprResult> access = result.stream()
                .filter(item -> item.getEntity().equals("Access")).findFirst();
        assertTrue(access.isPresent());
        GdprResult accessResult = access.get();
        assertEquals(EFunctionRole.OWNER, accessResult.getEntries().get(0).get("role"));
        assertEquals("012345", accessResult.getEntries().get(0).get("userId"));

        Optional<GdprResult> contributor = result.stream()
                .filter(item -> item.getEntity().equals("Contributor")).findFirst();
        assertTrue(contributor.isPresent());
    }
}
