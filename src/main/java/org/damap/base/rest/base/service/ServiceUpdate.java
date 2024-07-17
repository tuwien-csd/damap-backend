package org.damap.base.rest.base.service;

/** ServiceUpdate interface. */
public interface ServiceUpdate<E, S> {
  /**
   * update.
   *
   * @param id a {@link java.lang.String} object
   * @param data a S object
   * @return a E object
   */
  E update(String id, S data);
}
