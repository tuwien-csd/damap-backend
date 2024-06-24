package org.damap.base.conversion;

import java.io.InputStream;
import java.util.Properties;

public interface TemplateFileBrokerService {

    Properties getScienceEuropeTemplateResource();

    InputStream loadScienceEuropeTemplate();

    Properties getFWFTemplateResource();

    InputStream loadFWFTemplate();

    InputStream loadHorizonEuropeTemplate();

    Properties getHorizonEuropeTemplateResource();
}
