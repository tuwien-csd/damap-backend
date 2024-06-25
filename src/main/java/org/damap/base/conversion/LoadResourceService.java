package org.damap.base.conversion;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Properties;
import lombok.extern.jbosslog.JBossLog;

@ApplicationScoped
@JBossLog
public class LoadResourceService {

  public String loadVariableFromResource(Properties prop, String variable) {
    if (prop.getProperty(variable) == null) {
      log.info("Variable resource " + variable + " is not found");
    }

    return (prop.getProperty(variable, ""));
  }
}
