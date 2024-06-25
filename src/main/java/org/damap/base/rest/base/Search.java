package org.damap.base.rest.base;

import jakarta.ws.rs.core.MultivaluedMap;
import lombok.Data;

@Data
public class Search {
  private Pagination pagination = new Pagination();
  private String query;

  public static Search fromMap(MultivaluedMap<String, String> map) {
    Search search = new Search();
    search.pagination = Pagination.fromMap(map);
    search.query = map.getFirst("q");

    return search;
  }
}
