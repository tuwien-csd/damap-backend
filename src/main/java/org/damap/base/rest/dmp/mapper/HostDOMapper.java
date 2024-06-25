package org.damap.base.rest.dmp.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.Host;
import org.damap.base.rest.dmp.domain.*;

@UtilityClass
public class HostDOMapper {

  public HostDO mapEntityToDO(Host host, HostDO hostDO) {
    hostDO.setId(host.id);
    hostDO.setTitle(host.getTitle());

    return hostDO;
  }

  public Host mapDOtoEntity(HostDO hostDO, Host host) {
    if (hostDO.getId() != null) host.id = hostDO.getId();
    host.setTitle(hostDO.getTitle());

    return host;
  }
}
