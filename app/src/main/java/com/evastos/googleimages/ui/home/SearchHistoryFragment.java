package com.evastos.googleimages.ui.home;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.evastos.googleimages.R;
import com.evastos.googleimages.database.model.search.SearchHistoryItem;

import java.util.List;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class SearchHistoryFragment extends Fragment implements ExpandableListView.OnChildClickListener {

    public static final String TAG = SearchHistoryFragment.class.getName();

    private SearchActionListener searchActionListener;

    private SearchHistoryAdapter searchHistoryAdapter;

    private ExpandableListView searchHistoryListView;

    public static SearchHistoryFragment getInstance(final FragmentManager fragmentManager) {
        SearchHistoryFragment searchHistoryFragment = (SearchHistoryFragment) fragmentManager.findFragmentByTag
                (SearchHistoryFragment.TAG);
        if (searchHistoryFragment == null) {
            searchHistoryFragment = new SearchHistoryFragment();
        }
        return searchHistoryFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof SearchActionListener)) {
            throw new RuntimeException(HomeActivity.class.getSimpleName() + " must implement " +
                    SearchActionListener.class.getSimpleName());
        }
        searchActionListener = (SearchActionListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_search_history, container, false);
        searchHistoryListView = (ExpandableListView) rootView.findViewById(R.id
                .fragment_home_search_history_list_view);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateSearchHistory(searchActionListener.getSearchHistory());
    }

    public void updateSearchHistory(List<SearchHistoryItem> searchHistoryItems) {
        if (isAdded()) {
            searchHistoryAdapter = new SearchHistoryAdapter(getActivity(),
                    searchHistoryItems);
            searchHistoryListView.setOnChildClickListener(this);
            searchHistoryListView.setAdapter(searchHistoryAdapter);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        searchActionListener = null;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view,
                                int groupPosition, int childPosition, long l) {
        if (expandableListView.getId() == searchHistoryListView.getId()) {
            SearchHistoryItem searchHistoryItem = searchHistoryAdapter.getChild
                    (groupPosition, childPosition);
            if (searchHistoryItem != null) {
                searchActionListener.onSearchSubmitted(searchHistoryItem.getSearchQuery());
            }
        }
        return false;
    }
}
