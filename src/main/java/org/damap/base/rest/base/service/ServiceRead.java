package org.damap.base.rest.base.service;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

/** ServiceRead interface. */
public interface ServiceRead<E> {
  /**
   * read.
   *
   * @param id a {@link java.lang.String} object
   * @param queryParams a {@link jakarta.ws.rs.core.MultivaluedMap} object
   * @return a E object
   */
  E read(String id, MultivaluedMap<String, String> queryParams);

  /**
   * read.
   *
   * @param id a {@link java.lang.String} object
   * @return a E object
   */
  default E read(String id) {
    return read(id, new MultivaluedHashMap<>());
  }
}
