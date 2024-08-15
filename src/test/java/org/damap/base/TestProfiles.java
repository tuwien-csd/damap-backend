package org.damap.base;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.util.Collections;
import java.util.Map;

public class TestProfiles {
  public static class InvenioDAMAPEnabledProfile implements QuarkusTestProfile {

    /**
     * Returns additional config to be applied to the test. This will override any existing config
     * (including in application.(properties,yaml), however existing config will be merged with this
     * (i.e. application.(properties,yaml) config will still take effect, unless a specific config
     * key has been overridden).
     */
    @Override
    public Map<String, String> getConfigOverrides() {
      return Collections.singletonMap("invenio.disabled", "false");
    }
  }
}
