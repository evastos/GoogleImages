package com.evastos.googleimages.ui.home;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evastos.googleimages.R;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class SearchHistoryFragment extends Fragment {

    public static final String TAG = SearchHistoryFragment.class.getName();
    private SearchActionListener searchActionListener;

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
        if (activity instanceof SearchActionListener) {
            searchActionListener = (SearchActionListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_search_history, container, false);
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        searchActionListener = null;
    }
}
