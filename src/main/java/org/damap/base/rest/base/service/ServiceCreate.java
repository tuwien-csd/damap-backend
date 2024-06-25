package org.damap.base.rest.base.service;

public interface ServiceCreate<E, S> {
  E create(S data);
}
