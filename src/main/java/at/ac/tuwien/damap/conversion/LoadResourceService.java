package at.ac.tuwien.damap.conversion;

import java.util.Properties;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@JBossLog
public class LoadResourceService {

    public String loadVariableFromResource(Properties prop, String variable) {
        if (prop.getProperty(variable) == null) {
            log.info("Variable resource " + variable + " is not found");
        }

        return(prop.getProperty(variable, ""));
    }

}
