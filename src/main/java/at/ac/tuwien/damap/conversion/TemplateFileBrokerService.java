package at.ac.tuwien.damap.conversion;

import java.io.InputStream;
import java.util.Properties;

public interface TemplateFileBrokerService {

    Properties getScienceEuropeTemplateResource();

    InputStream loadScienceEuropeTemplate();

    Properties getFWFTemplateResource();

    InputStream loadFWFTemplate();
}
