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

import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@JBossLog
public class VersionTest {

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
        assertEquals(versionDO.getVersionName(), "TestVersion");
    }

    @Test
    void getVersionListTest(){
        VersionDO versionDO = testDOFactory.getOrCreateTestVersionDO();

        List<VersionDO> versionDOList = versionService.getDmpVersions(versionDO.getDmpId());
        assertNotNull(versionDOList);
        assertFalse(versionDOList.isEmpty());
        assertEquals("TestVersion", versionDOList.get(0).getVersionName());
    }

    @Test
    void updateVersionTest(){
        VersionDO versionDO = testDOFactory.getOrCreateTestVersionDO();
        versionDO.setVersionName("TestVersionUpdate");
        versionService.createOrUpdate(versionDO);

        VersionDO versionDOupdate = new VersionDO();
        final Optional<DmpVersion> testDmpVersion = dmpVersionRepo.getAll().stream()
                .filter(a -> a.getVersionName().equals("TestVersionUpdate"))
                .findAny();
        testDmpVersion.ifPresent(version -> VersionDOMapper.mapEntityToDO(version, versionDOupdate));

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

    private void updateDMP(long id){
        Dmp dmp = dmpRepo.findById(id);
        dmp.setTitle("TestDmp updated for revision");
        dmp.persist();
    }
}
