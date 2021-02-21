package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Host;
import at.ac.tuwien.damap.rest.domain.*;

public class HostDOMapper {

    public static void mapEntityToDO(Host host, HostDO hostDO) {
        hostDO.setHostId(host.getHostId());
        hostDO.setName(host.getName());
        hostDO.setDate(host.getDate());
    }

    public static void mapDOtoEntity(HostDO hostDO, Host host){
        host.setHostId(hostDO.getHostId());
        host.setName(hostDO.getName());
        host.setDate(hostDO.getDate());
    }
}
