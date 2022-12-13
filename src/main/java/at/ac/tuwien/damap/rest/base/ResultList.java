package at.ac.tuwien.damap.rest.base;

import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;

import lombok.Data;

@Data
public class ResultList<T> {
    private Search search;
    private List<T> items;

    public static <T> ResultList<T> fromItemsAndSearch(List<T> items, Search search) {
        ResultList<T> result = new ResultList<>();
        result.setItems(items != null ? items : List.of());
        result.setSearch(search != null ? search : (Search.fromMap(new MultivaluedHashMap<String, String>())));

        if (result.search.getPagination() == null) {
            result.search.setPagination(new Pagination());
            search.getPagination().setNumTotalItems(items.size());
        }

        search.getPagination().calculateFields();

        return result;
    }
}
