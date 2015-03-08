package com.evastos.googleimages.ui.images;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;

import com.evastos.googleimages.R;
import com.evastos.googleimages.api.client.HttpClient;
import com.evastos.googleimages.api.client.images.GoogleImagesClient;
import com.evastos.googleimages.api.model.images.ErrorResponse;
import com.evastos.googleimages.api.model.images.ImageResult;
import com.evastos.googleimages.api.model.images.ImagesCursor;
import com.evastos.googleimages.api.model.images.ImagesData;
import com.evastos.googleimages.api.model.images.ImagesResponse;
import com.evastos.googleimages.api.model.images.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eva Stos on 8.3.2015..
 */
public class ImagesFinder extends Fragment implements GoogleImagesClient
        .GoogleImagesResultListener {

    private static final String TAG = ImagesFinder.class.getName();
    private static final int REQUEST_ITEM_COUNT = 8;
    private int requestItemCount = REQUEST_ITEM_COUNT;
    private static final int REQUEST_PAGE_COUNT = 3;
    /* Count of pages to request before returning image results */
    private int requestPageCount = REQUEST_PAGE_COUNT;
    private static final int FIRST_PAGE_INDEX = 0;
    private int pageIndex = FIRST_PAGE_INDEX;
    private static final int FIRST_START_INDEX = 0;
    private int startIndex = FIRST_START_INDEX;
    private ImagesFinderListener imagesListener;
    private GoogleImagesClient googleImagesClient;
    private Page[] pages;
    private String searchQuery = "";
    private List<ImageResult> imageResults;

    public static ImagesFinder create(final FragmentManager fragmentManager) {
        ImagesFinder imagesFinder = (ImagesFinder) fragmentManager.findFragmentByTag(TAG);
        if (imagesFinder == null) {
            imagesFinder = new ImagesFinder();
            imagesFinder.setRetainInstance(true);
            fragmentManager.beginTransaction().add(imagesFinder, TAG).commit();
        }
        return imagesFinder;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ImagesFinderListener) {
            imagesListener = (ImagesFinderListener) activity;
        }
    }

    /**
     * Sets new search query
     *
     * @param searchQuery search query
     */
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    /**
     * Requests new set of pages with image results
     */
    public void find() {
        if (googleImagesClient == null) {
            googleImagesClient = new GoogleImagesClient();
            googleImagesClient.setClientResultListener(this);
        }
        googleImagesClient.run(searchQuery, startIndex, requestItemCount);
    }

    /**
     * Returns false if there are no pages of results to be returned,
     * true if find() will return more results
     *
     * @return boolean hasMoreResults
     */
    public boolean hasMoreResults() {
        return pages != null ? pageIndex < pages.length : false;
    }

    /**
     * Initialize search to start from first page
     */
    public void init() {
        imageResults = new ArrayList<ImageResult>();
        pages = null;
        pageIndex = FIRST_PAGE_INDEX;
        startIndex = FIRST_START_INDEX;
    }

    @Override
    public void onSuccess(HttpClient httpClient, ImagesResponse imagesResponse) {
        final ImagesData imagesData = imagesResponse.getResponseData();
        if (imagesData != null) {
            if (imagesData.getCursor() != null) {
                ImagesCursor cursor = imagesData.getCursor();
                pages = cursor.getPages();
            }
            if (imagesData.getResults() != null) {
                imageResults.addAll(imagesData.getResults());
            }

            pageIndex++;

            if (hasMoreResults()) {
                Page page = pages[pageIndex];
                startIndex = page.getStart();
                if (imageResults.size() < requestPageCount * requestItemCount) {
                    find(); // Not enough image results, request new page
                    return;
                }
            }
        } else {
            Log.e(TAG, "onSuccess: Received invalid image data");
        }
        onImagesLoaded();
    }

    @Override
    public void onError(HttpClient httpClient, ErrorResponse errorResponse) {
        onImagesFailed(errorResponse);
    }

    private void onImagesLoaded() {
        if (imagesListener != null) {
            Log.d("onImagesLoaded size", String.valueOf(imageResults.size()) + " pageIndex " +
                    pageIndex + " startIndex " + startIndex);
            imagesListener.onImagesLoaded(this, imageResults);
        }
        imageResults.clear();
    }

    private void onImagesFailed(ErrorResponse errorResponse) {
        if (errorResponse.getErrorMessage().isEmpty()) {
            errorResponse.setErrorMessage(getString(R.string.default_client_error));
        }
        if (imagesListener != null) {
            imagesListener.onImagesFailed(this, errorResponse);
        }
        imageResults.clear();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        imagesListener = null;
    }

    public interface ImagesFinderListener {
        void onImagesLoaded(ImagesFinder finder, List<ImageResult> imageResults);

        void onImagesFailed(ImagesFinder finder, ErrorResponse errorResponse);
    }
}
