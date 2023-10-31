package at.ac.tuwien.damap.rest.base.service;

public interface ServiceCreate<E, S> {
    E create(S data);
}
