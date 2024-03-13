package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.TestSetup;
import at.ac.tuwien.damap.enums.ETemplateType;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.FundingDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.util.MockDmpService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
class TemplateSelectorServiceImplTest extends TestSetup {

    @Inject
    TestDOFactory testDOFactory;

    @Inject
    MockDmpService dmpService;

    @Inject
    TemplateSelectorServiceImpl templateSelectorService;

    @Test
    @TestSecurity(authorizationEnabled = false)
    void givenDmpHasUnknownFunding_whenTemplateIsSelected_thenShouldReturnScienceEurope() {
        DmpDO testDMP = testDOFactory.createDmp(this.toString(), true);

        ProjectDO project = testDMP.getProject();
        project.setUniversityId("123456UnknwonFunder");
        project.setDescription("Test unknown funder.");
        FundingDO fundingDO = project.getFunding();
        fundingDO.getFunderId().setIdentifier("501100004955");
        project.setFunding(fundingDO);

        testDMP.setProject(project);
        DmpDO updatedDMP = dmpService.update(testDMP);

        Assertions.assertEquals(ETemplateType.SCIENCE_EUROPE, templateSelectorService.selectTemplate(updatedDMP));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void givenDmpHasFWFFunding_whenTemplateIsSelected_thenShouldReturnFWF() {
        DmpDO testDMP = testDOFactory.createDmp(this.toString(), true);

        ProjectDO project = testDMP.getProject();
        project.setUniversityId("123456FWFFunder");
        project.setDescription("Test FWF funder.");
        FundingDO fundingDO = project.getFunding();
        fundingDO.getFunderId().setIdentifier("501100002428");
        project.setFunding(fundingDO);

        testDMP.setProject(project);
        DmpDO updatedDMP = dmpService.update(testDMP);

        Assertions.assertEquals(ETemplateType.FWF, templateSelectorService.selectTemplate(updatedDMP));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void givenDmpHasUnknownFunding_whenTemplateIsSelected_thenShouldReturnHorizonEurope() {
        DmpDO testDMP = testDOFactory.createDmp(this.toString(), true);

        ProjectDO project = testDMP.getProject();
        project.setUniversityId("123456HEFunder");
        project.setDescription("Test HE funder.");
        FundingDO fundingDO = project.getFunding();
        fundingDO.getFunderId().setIdentifier("501100000780");
        project.setFunding(fundingDO);

        testDMP.setProject(project);
        DmpDO updatedDMP = dmpService.update(testDMP);

        Assertions.assertEquals(ETemplateType.HORIZON_EUROPE, templateSelectorService.selectTemplate(updatedDMP));
    }
}
