package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Host;
import at.ac.tuwien.damap.rest.domain.*;

public class HostDOMapper {

    public static void mapAtoB(Host host, HostDO hostDO) {
        hostDO.setId(host.id);
        hostDO.setVersion(host.getVersion());
        hostDO.setName(host.getName());
        hostDO.setDate(host.getDate());
    }
}
