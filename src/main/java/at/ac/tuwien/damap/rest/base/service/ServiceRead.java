package at.ac.tuwien.damap.rest.base.service;

public interface ServiceRead<Entity> {
    Entity read(String id);
}
