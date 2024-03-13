package at.ac.tuwien.damap.rest.dmp;

import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.domain.DmpVersion;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.repo.DmpVersionRepo;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.rest.version.VersionDO;
import at.ac.tuwien.damap.rest.version.VersionDOMapper;
import at.ac.tuwien.damap.rest.version.VersionService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.jbosslog.JBossLog;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@JBossLog
class VersionTest {

    @Inject
    TestDOFactory testDOFactory;

    @Inject
    VersionService versionService;

    @Inject
    DmpVersionRepo dmpVersionRepo;

    @Inject
    DmpRepo dmpRepo;

    @Inject
    DmpService dmpService;

    @Test
    void createVersionTest(){
        VersionDO versionDO = testDOFactory.getOrCreateTestVersionDO();
        assertNotNull(versionDO);
        assertNotNull(versionDO.getId());
        assertNotNull(versionDO.getDmpId());
        assertNotNull(versionDO.getVersionDate());
        assertNotNull(versionDO.getRevisionNumber());
        assertEquals("TestVersion", versionDO.getVersionName());
    }

    @Test
    void getVersionListTest(){
        VersionDO versionDO = testDOFactory.getOrCreateTestVersionDO();

        assertEquals("TestVersion", versionDO.getVersionName());

        List<VersionDO> versionDOList = versionService.getDmpVersions(versionDO.getDmpId());
        assertNotNull(versionDOList);
        assertFalse(versionDOList.isEmpty());

        System.out.println("size " + versionDOList.size());

        assertEquals(versionDO.getId(), versionDOList.get(versionDOList.size() - 1).getId());
        assertEquals("TestVersion", versionDOList.get(versionDOList.size() - 1).getVersionName());
    }

    @Test
    void updateVersionTest(){
        VersionDO versionDO = testDOFactory.getOrCreateTestVersionDO();
        versionDO.setVersionName("TestVersionUpdate");
        var versionDOupdate = versionService.createOrUpdate(versionDO);

        assertNotNull(versionDOupdate);
        assertEquals(versionDO.getId(), versionDOupdate.getId());
        assertEquals(versionDO.getRevisionNumber(), versionDOupdate.getRevisionNumber());
        assertNotEquals("TestVersion", versionDOupdate.getVersionName());
    }

    @Test
    void updateDMPTest(){
        VersionDO versionDO = testDOFactory.getOrCreateTestVersionDO();
        updateDMP(versionDO.getDmpId());

        DmpDO dmpDOCurrent = dmpService.getDmpById(versionDO.getDmpId());
        DmpDO dmpDOPreviousVersion = dmpService.getDmpByIdAndRevision(versionDO.getDmpId(), versionDO.getRevisionNumber());

        assertNotEquals(dmpDOCurrent.getTitle(), dmpDOPreviousVersion.getTitle());
    }

    @Transactional
    private void updateDMP(long id){
        Dmp dmp = dmpRepo.findById(id);
        dmp.setTitle("TestDmp updated for revision");
        dmp.persist();
    }
}
