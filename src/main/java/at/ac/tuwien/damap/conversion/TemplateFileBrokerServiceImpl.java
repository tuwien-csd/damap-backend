package at.ac.tuwien.damap.conversion;

/*
    extend this class in your custom project, in order to load you custom resources/templates
 */

import io.quarkus.arc.DefaultBean;
import lombok.extern.jbosslog.JBossLog;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationScoped
@DefaultBean
@JBossLog
public class TemplateFileBrokerServiceImpl implements TemplateFileBrokerService {

    @Override
    public Properties getScienceEuropeTemplateResource() {
        return loadResource("template/scienceEuropeTemplate.resource");
    }

    @Override
    public InputStream loadScienceEuropeTemplate() {
        return loadTemplate("template/scienceEuropeTemplate.docx");
    }

    @Override
    public Properties getFWFTemplateResource() {
        return loadResource("template/scienceEuropeTemplate.resource");
    }

    @Override
    public InputStream loadFWFTemplate() {
        return loadTemplate("template/FWFTemplate.docx");
    }

    public Properties loadResource(String  resource) {
        Properties prop = new Properties();
        try (
            InputStream input = this.getClass().getClassLoader().getResourceAsStream(resource)) {
            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            log.error("could not load resource file: " + resource);
        }
        return prop;
    }

    public InputStream loadTemplate(String template) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(template);
    }
}
