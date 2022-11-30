package at.ac.tuwien.damap.rest.base.service;

public interface ServiceUpdate<Entity, Schema> {
    Entity update(String id, Schema data);
}
