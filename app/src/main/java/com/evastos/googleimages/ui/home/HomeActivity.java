package com.evastos.googleimages.ui.home;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.evastos.googleimages.R;
import com.evastos.googleimages.database.dao.search.SearchHistoryDao;
import com.evastos.googleimages.database.model.search.SearchHistoryItem;
import com.evastos.googleimages.ui.images.ImagesActivity;

import java.util.List;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class HomeActivity extends Activity implements SearchActionListener {

    public static final String TAG = HomeActivity.class.getName();

    private static final String BUNDLE_SELECTED_TAB = "selected_tab";

    private static final int REQUEST_CODE_UPDATE_SEARCH_HISTORY = 1001;

    private static final int SEARCH_TAB = 0;

    private static final int SEARCH_HISTORY_TAB = 1;
    private SearchFragment searchFragment;
    private SearchHistoryFragment searchHistoryFragment;
    private View tabLayout;
    private SearchHistoryDao searchHistoryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = findViewById(R.id.activity_home_tab_layout);
        searchFragment = SearchFragment.getInstance(getFragmentManager());
        searchHistoryFragment = SearchHistoryFragment.getInstance(getFragmentManager());

        searchHistoryDao = new SearchHistoryDao(this);

        setupActionBar(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_SELECTED_TAB, getActionBar().getSelectedTab().getPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchSubmitted(final String searchQuery) {
        searchHistoryDao.open();
        searchHistoryDao.createSearchHistoryItem(searchQuery);
        searchHistoryDao.close();
        startImagesActivity(searchQuery);
    }

    @Override
    public List<SearchHistoryItem> getSearchHistory() {
        searchHistoryDao.open();
        final List<SearchHistoryItem> searchHistoryItems = searchHistoryDao.getSearchHistory();
        searchHistoryDao.close();
        return searchHistoryItems;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HomeActivity.REQUEST_CODE_UPDATE_SEARCH_HISTORY) {
            if (resultCode == Activity.RESULT_OK) {
                searchHistoryFragment.updateSearchHistory(getSearchHistory());
            }
        }
    }

    private void setupActionBar(Bundle savedInstanceState) {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        final ActionBar.Tab searchTab = actionBar.newTab().setText(R.string.activity_home_search);
        searchTab.setTabListener(new HomeTabListener(searchFragment));
        actionBar.addTab(searchTab, SEARCH_TAB);
        final ActionBar.Tab searchHistoryTab = actionBar.newTab().setText(R.string
                .activity_home_history);
        searchHistoryTab.setTabListener(new HomeTabListener(searchHistoryFragment));
        actionBar.addTab(searchHistoryTab, SEARCH_HISTORY_TAB);

        if (savedInstanceState != null) {
            ActionBar.Tab selectedTab = actionBar.getTabAt(savedInstanceState.getInt
                    (BUNDLE_SELECTED_TAB, SEARCH_TAB));
            actionBar.selectTab(selectedTab);
        }
    }

    private void startImagesActivity(final String searchQuery) {
        final Intent imagesIntent = new Intent(this, ImagesActivity.class);
        imagesIntent.putExtra(ImagesActivity.BUNDLE_SEARCH_QUERY, searchQuery);
        startActivityForResult(imagesIntent, REQUEST_CODE_UPDATE_SEARCH_HISTORY);
    }

    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            final InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    class HomeTabListener implements ActionBar.TabListener {

        private Fragment fragment;

        public HomeTabListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            fragmentTransaction.replace(tabLayout.getId(), fragment);
            hideSoftKeyboard();
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            fragmentTransaction.remove(fragment);
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        }
    }

}
