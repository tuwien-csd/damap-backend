package org.damap.base.repo.base;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RepoSearch<T> extends PanacheRepository<T> {

  default List<T> searchByParameters(MultivaluedMap<String, Object> queryParams) {

    StringBuilder query = new StringBuilder();

    Map<String, Object> params = new HashMap<>();

    int counter = 0;

    for (Map.Entry<String, List<Object>> entry : queryParams.entrySet()) {
      String key = entry.getKey();
      List<Object> values = entry.getValue();
      if (values.size() == 1) {
        if (counter != 0) {
          query.append(" AND ");
        }
        query.append(key).append(" = :").append(key);
        params.put(key, values.get(0));
      } else {
        if (counter != 0) {
          query.append(" AND ( ");
        } else {
          query.append(" ( ");
        }
        query.append(key).append(" = :").append(key).append("0");
        params.put(key + "0", values.get(0));
        for (int i = 1; i < values.size(); i++) {
          query.append(" OR ").append(key).append(" = :").append(key).append(i);
          params.put(key + i, values.get(i));
        }
        query.append(" )");
      }

      counter++;
    }

    return list(query.toString(), params);
  }
}
