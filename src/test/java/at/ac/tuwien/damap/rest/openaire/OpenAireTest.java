package at.ac.tuwien.damap.rest.openaire;

import at.ac.tuwien.damap.enums.*;
import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.openaire.mapper.OpenAireMapper;
import com.google.common.io.Resources;
import generated.Response;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.jbosslog.JBossLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


@QuarkusTest
@JBossLog
class OpenAireTest {

    @Test
    void testOpenAireMapper() {
        DatasetDO datasetDO = new DatasetDO();
        try {
            URL url = Resources.getResource("xml/response.xml");
            Response response = (Response) JAXBContext.newInstance(Response.class)
                    .createUnmarshaller()
                    .unmarshal(url);
            OpenAireMapper.mapAtoB("01.2345/zenodo.0123456", response, datasetDO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(datasetDO, createResponse());
    }

    private DatasetDO createResponse() {
        DatasetDO datasetDO = new DatasetDO();
        datasetDO.setTitle("Dataset title 1 Dataset title 2");
        datasetDO.setDescription("Description 1. Description 2.");
        datasetDO.setDataAccess(EDataAccessType.OPEN);
        datasetDO.setStartDate(new GregorianCalendar(2021, Calendar.MAY, 20).getTime());
        datasetDO.setType(List.of(EDataType.IMAGES, EDataType.AUDIOVISUAL_DATA, EDataType.SOFTWARE_APPLICATIONS,
                EDataType.PLAIN_TEXT, EDataType.OTHER));
        datasetDO.setSource(EDataSource.REUSED);
        IdentifierDO identifierDO = new IdentifierDO();
        identifierDO.setType(EIdentifierType.DOI);
        identifierDO.setIdentifier("01.2345/zenodo.0123456");
        datasetDO.setDatasetId(identifierDO);
        datasetDO.setSize(100L);
        //default values for accessrights
        datasetDO.setSelectedProjectMembersAccess(EAccessRight.READ);
        datasetDO.setOtherProjectMembersAccess(EAccessRight.READ);
        datasetDO.setPublicAccess(EAccessRight.READ);

        return datasetDO;
    }
}
