package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Host;
import at.ac.tuwien.damap.rest.domain.*;

public class HostDOMapper {

    public static void mapEntityToDO(Host host, HostDO hostDO) {
        hostDO.setId(host.id);
        hostDO.setHostId(host.getHostId());
        hostDO.setTitle(host.getTitle());
        hostDO.setDate(host.getDate());
    }

    public static void mapDOtoEntity(HostDO hostDO, Host host){
        if (hostDO.getId() != null)
            host.id = hostDO.getId();
        host.setHostId(hostDO.getHostId());
        host.setTitle(hostDO.getTitle());
        host.setDate(hostDO.getDate());
    }
}
