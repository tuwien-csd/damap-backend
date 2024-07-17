package org.damap.base.rest.base;

import jakarta.ws.rs.core.MultivaluedMap;
import lombok.Data;

/** Search class. */
@Data
public class Search {
  private Pagination pagination = new Pagination();
  private String query;

  /**
   * fromMap.
   *
   * @param map a {@link jakarta.ws.rs.core.MultivaluedMap} object
   * @return a {@link org.damap.base.rest.base.Search} object
   */
  public static Search fromMap(MultivaluedMap<String, String> map) {
    Search search = new Search();
    search.pagination = Pagination.fromMap(map);
    search.query = map.getFirst("q");

    return search;
  }
}
