package com.evastos.googleimages.ui.home;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.evastos.googleimages.R;
import com.evastos.googleimages.ui.images.ImagesActivity;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class HomeActivity extends Activity implements SearchActionListener {

    private SearchFragment searchFragment;
    private SearchHistoryFragment historyFragment;

    private View tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = findViewById(R.id.activity_home_tab_layout);
        searchFragment = SearchFragment.getInstance(getFragmentManager());
        historyFragment = SearchHistoryFragment.getInstance(getFragmentManager());

        setupActionBar();
    }

    private void setupActionBar() {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        final ActionBar.Tab searchTab = actionBar.newTab().setText(R.string.activity_home_search);
        searchTab.setTabListener(new HomeTabListener(searchFragment));
        actionBar.addTab(searchTab, 0);
        final ActionBar.Tab historyTab = actionBar.newTab().setText(R.string.activity_home_history);
        historyTab.setTabListener(new HomeTabListener(historyFragment));
        actionBar.addTab(historyTab, 1);
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
    public void onSearchSubmitted(String searchQuery) {
        ImagesActivity.startImagesActivity(this, searchQuery);
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
