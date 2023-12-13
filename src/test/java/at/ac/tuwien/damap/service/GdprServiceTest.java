package at.ac.tuwien.damap.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import at.ac.tuwien.damap.TestSetup;
import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.rest.gdpr.domain.GdprQuery;
import at.ac.tuwien.damap.rest.gdpr.domain.GdprResult;
import at.ac.tuwien.damap.rest.gdpr.service.GdprQueryUtil;
import at.ac.tuwien.damap.rest.gdpr.service.GdprService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class GdprServiceTest extends TestSetup {

    @Inject
    GdprService gdprService;

    @Inject
    TestDOFactory testDOFactory;

    @Test
    void testGdprExtendedData_shouldReturnData() {
        List<GdprResult> result = gdprService.getGdprExtendedData("012345");

        assertThat(result.size(), greaterThanOrEqualTo(3));

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

    @Test
    void testsContextJoinQuery_shouldReturnData() throws NoSuchFieldException {
        List<String> fields = Arrays.asList("id", "project.title", "project.start", "title");
        GdprQuery result = GdprQueryUtil.getContextJoinQuery(Contributor.class.getDeclaredField("dmp"), fields);
        assertEquals("dmp", result.getFieldName());
        assertEquals(1, result.getContextJoins().size());
        assertEquals("project", result.getContextJoins().get(0).getFieldName());
        assertEquals(2, result.getContextJoins().get(0).getBase().size());
    }
}
