package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Host;
import at.ac.tuwien.damap.rest.domain.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HostDOMapper {

    public HostDO mapEntityToDO(Host host, HostDO hostDO) {
        hostDO.setId(host.id);
        hostDO.setHostId(host.getHostId());
        hostDO.setTitle(host.getTitle());
        hostDO.setDate(host.getDate());

        return hostDO;
    }

    public Host mapDOtoEntity(HostDO hostDO, Host host){
        if (hostDO.getId() != null)
            host.id = hostDO.getId();
        host.setHostId(hostDO.getHostId());
        host.setTitle(hostDO.getTitle());
        host.setDate(hostDO.getDate());

        return host;
    }
}
