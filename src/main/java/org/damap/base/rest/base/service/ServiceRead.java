package org.damap.base.rest.base.service;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

public interface ServiceRead<E> {
    E read(String id, MultivaluedMap<String, String> queryParams);

    default E read(String id) {
        return read(id, new MultivaluedHashMap<>());
    }
}
