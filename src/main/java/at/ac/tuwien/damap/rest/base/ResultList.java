package at.ac.tuwien.damap.rest.base;

import java.util.List;

import lombok.Data;

@Data
public class ResultList<T> {
    Search search;
    List<T> items;
}
