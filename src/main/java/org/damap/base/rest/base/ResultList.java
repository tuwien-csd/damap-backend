package org.damap.base.rest.base;

import jakarta.ws.rs.core.MultivaluedHashMap;
import java.util.List;
import lombok.Data;

/** ResultList class. */
@Data
public class ResultList<T> {
  private Search search;
  private List<T> items;

  /**
   * fromItemsAndSearch.
   *
   * @param items a {@link java.util.List} object
   * @param search a {@link org.damap.base.rest.base.Search} object
   * @param <T> a T class
   * @return a {@link org.damap.base.rest.base.ResultList} object
   */
  public static <T> ResultList<T> fromItemsAndSearch(List<T> items, Search search) {
    ResultList<T> result = new ResultList<>();
    result.setItems(items != null ? items : List.of());
    result.setSearch(search != null ? search : (Search.fromMap(new MultivaluedHashMap<>())));

    if (result.search.getPagination() == null) {
      Pagination p = new Pagination();
      p.setNumTotalItems(result.items.size());
      result.search.setPagination(p);
    }

    result.search.getPagination().calculateFields();

    return result;
  }
}
