package at.ac.tuwien.damap.rest.base.service;

import javax.ws.rs.core.MultivaluedMap;

import at.ac.tuwien.damap.rest.base.ResultList;

public interface ServiceSearch<E> {
    ResultList<E> search(MultivaluedMap<String, String> queryParams);
}
