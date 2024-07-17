package org.damap.base.rest.dmp.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.Host;
import org.damap.base.rest.dmp.domain.*;

/** HostDOMapper class. */
@UtilityClass
public class HostDOMapper {

  /**
   * mapEntityToDO.
   *
   * @param host a {@link org.damap.base.domain.Host} object
   * @param hostDO a {@link org.damap.base.rest.dmp.domain.HostDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.HostDO} object
   */
  public HostDO mapEntityToDO(Host host, HostDO hostDO) {
    hostDO.setId(host.id);
    hostDO.setTitle(host.getTitle());

    return hostDO;
  }

  /**
   * mapDOtoEntity.
   *
   * @param hostDO a {@link org.damap.base.rest.dmp.domain.HostDO} object
   * @param host a {@link org.damap.base.domain.Host} object
   * @return a {@link org.damap.base.domain.Host} object
   */
  public Host mapDOtoEntity(HostDO hostDO, Host host) {
    if (hostDO.getId() != null) host.id = hostDO.getId();
    host.setTitle(hostDO.getTitle());

    return host;
  }
}
