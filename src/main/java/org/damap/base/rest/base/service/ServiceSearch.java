package org.damap.base.rest.base.service;

import jakarta.ws.rs.core.MultivaluedMap;
import org.damap.base.rest.base.ResultList;

public interface ServiceSearch<E> {
  ResultList<E> search(MultivaluedMap<String, String> queryParams);
}
