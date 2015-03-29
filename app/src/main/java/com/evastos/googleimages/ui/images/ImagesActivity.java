package com.evastos.googleimages.ui.images;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.evastos.googleimages.R;
import com.evastos.googleimages.api.model.images.ErrorResponse;
import com.evastos.googleimages.api.model.images.ImageResult;
import com.evastos.googleimages.ui.views.LoadingGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class ImagesActivity extends Activity implements ImagesFinder.ImagesFinderListener, LoadingGridView.LoadingListener {

    public static final String BUNDLE_SEARCH_QUERY = "search_query";

    private static final String BUNDLE_IMAGE_RESULTS = "image_results";

    private String searchQueryText;

    private View searchLoadingView;

    private LoadingGridView imagesGridView;

    private View footerLoadingView;

    private TextView searchMessageTextView;

    private ImagesFinder imagesFinder;

    private ImagesAdapter imagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        imagesFinder = ImagesFinder.create(getFragmentManager());

        searchLoadingView = findViewById(R.id.activity_images_search_loading_view);
        imagesGridView = (LoadingGridView) findViewById(R.id.activity_images_grid_view);
        imagesGridView.setLoadingListener(this);
        footerLoadingView = findViewById(R.id.activity_images_loading_view);
        searchMessageTextView = (TextView) findViewById(R.id
                .activity_images_search_message_text_view);

        searchQueryText = savedInstanceState != null ? savedInstanceState.getString
                (BUNDLE_SEARCH_QUERY, "") : "";
        updateSearchQuery(searchQueryText);

        if (savedInstanceState == null) {
            handleIntent(getIntent());
        } else {
            if (savedInstanceState.containsKey(BUNDLE_IMAGE_RESULTS)) {
                List<ImageResult> imageResults = (ArrayList) savedInstanceState
                        .getSerializable
                                (BUNDLE_IMAGE_RESULTS);
                imagesAdapter = new ImagesAdapter(this, imageResults);
                imagesGridView.setAdapter(imagesAdapter);
                if (imageResults.isEmpty()) {
                    displayMessage(getString(R.string.image_search_no_results));
                } else {
                    hideMessage();
                }
            } else {
                displayMessage(getString(R.string.image_search_no_results));
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_images, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_refresh) {
            startSearch();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (imagesAdapter != null) {
            outState.putSerializable(BUNDLE_IMAGE_RESULTS, (ArrayList) imagesAdapter.getImageResults());
        }
        outState.putString(BUNDLE_SEARCH_QUERY, searchQueryText);
        super.onSaveInstanceState(outState);
    }

    private void handleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(BUNDLE_SEARCH_QUERY)) {
            searchQueryText = extras.getString(BUNDLE_SEARCH_QUERY);
            updateSearchQuery(searchQueryText);
        }
        setResult(RESULT_OK);
        startSearch();
    }

    private void updateSearchQuery(final String searchQuery) {
        imagesFinder.setSearchQuery(searchQuery);
        getActionBar().setSubtitle('\"' + searchQuery + '\"');
    }

    private void startSearch() {
        imagesGridView.init();
        imagesAdapter = null;
        imagesFinder.init();
        imagesFinder.find();
        showSearchLoadingView();
        hideMessage();
    }

    @Override
    public void onImagesLoaded(ImagesFinder finder, List<ImageResult> imageResults) {
        if (imagesAdapter == null) {
            imagesAdapter = new ImagesAdapter(this, imageResults);
            imagesGridView.setAdapter(imagesAdapter);
            if (imageResults.isEmpty()) {
                displayMessage(getString(R.string.image_search_no_results));
            } else {
                hideMessage();
            }
            hideSearchLoadingView();
        } else {
            imagesAdapter.addImageResults(imageResults);
            hideFooterLoadingView();
        }
        imagesGridView.setLoading(false);
    }

    @Override
    public void onImagesFailed(ImagesFinder finder, ErrorResponse errorResponse) {
        final boolean isInitialSearch = imagesAdapter == null;
        if (isInitialSearch) {
            displayMessage(errorResponse.getErrorMessage());
            hideSearchLoadingView();
        } else {
            hideFooterLoadingView();
        }
    }

    @Override
    public void onLoadMoreData(LoadingGridView loadingGridView) {
        final boolean hasMoreResults = imagesFinder.hasMoreResults();
        imagesGridView.setCanLoadMore(hasMoreResults);
        if (hasMoreResults) {
            findMoreImages();
        }
    }

    private void findMoreImages() {
        hideMessage();
        showFooterLoadingView();
        imagesGridView.setLoading(true);
        imagesFinder.find();
    }

    private void displayMessage(final String message) {
        searchMessageTextView.setVisibility(View.VISIBLE);
        searchMessageTextView.setText(message);
    }

    private void hideMessage() {
        searchMessageTextView.setVisibility(View.GONE);
    }

    private void showSearchLoadingView() {
        imagesGridView.setVisibility(View.GONE);
        searchLoadingView.setVisibility(View.VISIBLE);
    }

    private void hideSearchLoadingView() {
        imagesGridView.setVisibility(View.VISIBLE);
        searchLoadingView.setVisibility(View.GONE);
    }

    private void showFooterLoadingView() {
        footerLoadingView.setVisibility(View.VISIBLE);
    }

    private void hideFooterLoadingView() {
        footerLoadingView.setVisibility(View.INVISIBLE);
    }

}
