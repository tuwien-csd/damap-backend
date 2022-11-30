package at.ac.tuwien.damap.rest.base;

import javax.ws.rs.core.MultivaluedMap;

import lombok.Data;

@Data
public class Search {
    Pagination pagination;
    String query;

    public static Search fromMap(MultivaluedMap<String, String> map) {
        Search search = new Search();
        search.pagination = Pagination.fromMap(map);
        search.query = map.getFirst("q");

        return search;
    }
}
