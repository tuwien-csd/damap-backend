package at.ac.tuwien.damap.conversion;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest

public class LoadResourceServiceTest {

    @InjectMock
    LoadResourceService loadResourceServices;

    @BeforeEach
    public void setup() {
        Mockito.when(loadResourceServices.loadResource(anyString())).thenReturn(this.createProperties());
    }

    @Test
    public void loadResourceTest() {
        String dummyResource = "dummy.resources";
        Properties prop = loadResourceServices.loadResource(dummyResource);

        System.out.println(prop.getProperty("var1"));
        System.out.println(prop.getProperty("var2"));

        Assertions.assertEquals(prop.getProperty("var1"), createProperties().get("var1"));
        Assertions.assertEquals(prop.getProperty("var2"), createProperties().get("var2"));
    }

    private Properties createProperties() {
        Properties prop = new Properties();
        prop.setProperty("var1", "new sentence for testing");
        prop.setProperty("var2", "another sentence for testing");

        return prop;
    }
}
