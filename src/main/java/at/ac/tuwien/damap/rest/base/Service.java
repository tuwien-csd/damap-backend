package at.ac.tuwien.damap.rest.base;

import javax.ws.rs.core.MultivaluedMap;

public abstract class Service<Entity, Schema> {
    ResultList<Entity> search(MultivaluedMap<String, String> queryParams) {
        return null;
    };

    protected ResultList<Entity> postSearchPagination(ResultList<Entity> searchResult) {
        if (searchResult != null && searchResult.search != null && searchResult.search.pagination != null) {
            searchResult.search.pagination.calculateFields();
        }

        return searchResult;
    };

    protected Search parseSearch(MultivaluedMap<String, String> queryParams) {
        return Search.fromMap(queryParams);
    };


    Entity create(Schema data) {
        return null;
    };

    Entity read(String id) {
        return null;
    };

    Entity update(String id, Schema data) {
        return null;
    };

    boolean delete(String id) {
        return false;
    };
}
