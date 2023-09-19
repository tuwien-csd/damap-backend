package at.ac.tuwien.damap.rest.base.service;

import jakarta.ws.rs.core.MultivaluedMap;

import at.ac.tuwien.damap.rest.base.ResultList;

public interface ServiceSearch<Entity> {
    ResultList<Entity> search(MultivaluedMap<String, String> queryParams);
}
