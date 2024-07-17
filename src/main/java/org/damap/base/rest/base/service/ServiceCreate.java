package org.damap.base.rest.base.service;

/** ServiceCreate interface. */
public interface ServiceCreate<E, S> {
  /**
   * create.
   *
   * @param data a S object
   * @return a E object
   */
  E create(S data);
}
