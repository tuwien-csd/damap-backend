package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.Host;
import at.ac.tuwien.damap.rest.dmp.domain.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HostDOMapper {

    public HostDO mapEntityToDO(Host host, HostDO hostDO) {
        hostDO.setId(host.id);
        hostDO.setTitle(host.getTitle());

        return hostDO;
    }

    public Host mapDOtoEntity(HostDO hostDO, Host host){
        if (hostDO.getId() != null)
            host.id = hostDO.getId();
        host.setTitle(hostDO.getTitle());

        return host;
    }
}
