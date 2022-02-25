package at.ac.tuwien.damap.conversion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@JBossLog

public class LoadResourceService {

    public Properties loadResource(String resource) {

        Properties prop = new Properties();

        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(resource)) {

            // load a properties file
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();

        } finally {
            return prop;
        }
    }

    public String loadVariableFromResource(Properties prop, String variable) {
        if (prop.getProperty(variable) == null) {
            log.info("Variable resource " + variable + " is not found");
        }

        return(prop.getProperty(variable, ""));
    }

}
