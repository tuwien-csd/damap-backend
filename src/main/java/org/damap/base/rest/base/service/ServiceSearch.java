package org.damap.base.rest.base.service;

import jakarta.ws.rs.core.MultivaluedMap;
import org.damap.base.rest.base.ResultList;

/** ServiceSearch interface. */
public interface ServiceSearch<E> {
  /**
   * search.
   *
   * @param queryParams a {@link jakarta.ws.rs.core.MultivaluedMap} object
   * @return a {@link org.damap.base.rest.base.ResultList} object
   */
  ResultList<E> search(MultivaluedMap<String, String> queryParams);
}
