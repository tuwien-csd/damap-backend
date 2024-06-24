package org.damap.base.rest.base.service;

public interface ServiceUpdate<E, S> {
    E update(String id, S data);
}
