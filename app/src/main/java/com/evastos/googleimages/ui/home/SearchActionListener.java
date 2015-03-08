package com.evastos.googleimages.ui.home;

import com.evastos.googleimages.database.model.search.SearchHistoryItem;

import java.util.List;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public interface SearchActionListener {
    void onSearchSubmitted(String searchQuery);

    List<SearchHistoryItem> getSearchHistory();
}
