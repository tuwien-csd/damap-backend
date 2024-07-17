package org.damap.base.conversion;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Properties;
import lombok.extern.jbosslog.JBossLog;

/** LoadResourceService class. */
@ApplicationScoped
@JBossLog
public class LoadResourceService {

  /**
   * loadVariableFromResource.
   *
   * @param prop a {@link java.util.Properties} object
   * @param variable a {@link java.lang.String} object
   * @return a {@link java.lang.String} object
   */
  public String loadVariableFromResource(Properties prop, String variable) {
    if (prop.getProperty(variable) == null) {
      log.info("Variable resource " + variable + " is not found");
    }

    return (prop.getProperty(variable, ""));
  }
}
