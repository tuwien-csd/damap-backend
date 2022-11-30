package at.ac.tuwien.damap.rest.base.service;

import javax.ws.rs.core.MultivaluedMap;

import at.ac.tuwien.damap.rest.base.ResultList;
import at.ac.tuwien.damap.rest.base.Search;

public interface ServiceSearch<Entity> {
    ResultList<Entity> search(Search search, MultivaluedMap<String, String> queryParams);
}
