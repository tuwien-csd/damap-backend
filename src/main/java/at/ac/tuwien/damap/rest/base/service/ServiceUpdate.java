package at.ac.tuwien.damap.rest.base.service;

public interface ServiceUpdate<E, S> {
    E update(String id, S data);
}
