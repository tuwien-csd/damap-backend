package at.ac.tuwien.damap.rest.base.service;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public interface ServiceRead<E> {
    E read(String id, MultivaluedMap<String, String> queryParams);

    default E read(String id) {
        return read(id, new MultivaluedHashMap<>());
    }
}
