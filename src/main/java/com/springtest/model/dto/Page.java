package com.springtest.model.dto;

import java.util.List;

/**
 * Search result of a search method, meant to be used as return type in the service layer for search methods,
 * when both the result and the total results count are needed.
 *
 * @param <T>
 */
public class Page <T>{
    public List<T> list;
    public long total;
}
