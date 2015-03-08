package com.evastos.googleimages.ui.home;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.evastos.googleimages.R;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class SearchFragment extends Fragment {

    public static final String TAG = SearchFragment.class.getName();
    private SearchView imagesSearchView;
    private SearchActionListener searchActionListener;

    public static SearchFragment getInstance(final FragmentManager fragmentManager) {
        SearchFragment searchFragment = (SearchFragment) fragmentManager.findFragmentByTag
                (SearchFragment.TAG);
        if (searchFragment == null) {
            searchFragment = new SearchFragment();
        }
        return searchFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_home_search, container, false);
        imagesSearchView = (SearchView) rootView.findViewById(R.id
                .fragment_home_images_search_view);
        imagesSearchView.setIconifiedByDefault(false);
        imagesSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (searchActionListener != null) {
                    searchActionListener.onSearchSubmitted(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        searchActionListener = null;
    }

}
