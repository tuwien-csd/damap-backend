package org.damap.base.conversion;

import java.io.InputStream;
import java.util.Properties;

/** TemplateFileBrokerService interface. */
public interface TemplateFileBrokerService {

  /**
   * getScienceEuropeTemplateResource.
   *
   * @return a {@link java.util.Properties} object
   */
  Properties getScienceEuropeTemplateResource();

  /**
   * loadScienceEuropeTemplate.
   *
   * @return a {@link java.io.InputStream} object
   */
  InputStream loadScienceEuropeTemplate();

  /**
   * getFWFTemplateResource.
   *
   * @return a {@link java.util.Properties} object
   */
  Properties getFWFTemplateResource();

  /**
   * loadFWFTemplate.
   *
   * @return a {@link java.io.InputStream} object
   */
  InputStream loadFWFTemplate();

  /**
   * loadHorizonEuropeTemplate.
   *
   * @return a {@link java.io.InputStream} object
   */
  InputStream loadHorizonEuropeTemplate();

  /**
   * getHorizonEuropeTemplateResource.
   *
   * @return a {@link java.util.Properties} object
   */
  Properties getHorizonEuropeTemplateResource();
}
