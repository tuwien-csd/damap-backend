package at.ac.tuwien.damap.rest.base.service;

public interface ServiceCreate<Entity, Schema> {
    Entity create(Schema data);
}
