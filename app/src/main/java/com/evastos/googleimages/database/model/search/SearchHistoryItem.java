package com.evastos.googleimages.database.model.search;

/**
 * Created by Eva Stos on 8.3.2015..
 */
public class SearchHistoryItem {

    private String searchQuery;

    private long id;

    private long time;

    public SearchHistoryItem() {
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SearchQuery{" +
                "searchQuery='" + searchQuery + '\'' +
                ", id=" + id +
                ", time=" + time +
                '}';
    }
}
