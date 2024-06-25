package org.damap.base.rest.base;

import jakarta.ws.rs.core.MultivaluedMap;
import lombok.Data;

@Data
public class Pagination {
  int page = 1;
  int perPage = 10;
  Integer numPages;
  Integer numTotalItems;
  boolean hasNext;
  boolean hasPrevious;

  public static Pagination fromMap(MultivaluedMap<String, String> map) {
    Pagination p = new Pagination();
    p.setPage(map);
    p.setPerPage(map);

    return p;
  }

  public void setPage(MultivaluedMap<String, String> map) {
    var p = map.getFirst("page");
    try {
      this.page = Integer.parseUnsignedInt(p);
    } catch (Exception e) {
      this.page = 1;
    }
  }

  public void setPerPage(MultivaluedMap<String, String> map) {
    var pp = map.getFirst("perPage");
    try {
      this.perPage = Integer.parseUnsignedInt(pp);
    } catch (Exception e) {
      this.perPage = 10;
    }
  }

  public void calculateFields() {
    hasPrevious = page > 1;

    if (numTotalItems != null) {
      numPages = numTotalItems / perPage;
      if ((numTotalItems == 0) || (numTotalItems % perPage != 0)) {
        numPages += 1;
      }
      hasNext = page != numPages;
    }
  }
}
