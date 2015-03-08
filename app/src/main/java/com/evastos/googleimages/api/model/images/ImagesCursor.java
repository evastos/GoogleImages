package com.evastos.googleimages.api.model.images;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class ImagesCursor {

    private int estimatedResultCount;

    private int currentPageIndex;

    private String moreResultsUrl;

    private Page[] pages;

    public int getEstimatedResultCount() {
        return estimatedResultCount;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public String getMoreResultsUrl() {
        return moreResultsUrl;
    }

    public Page[] getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "ImagesCursor{" +
                "estimatedResultCount=" + estimatedResultCount +
                ", currentPageIndex=" + currentPageIndex +
                ", moreResultsUrl='" + moreResultsUrl + '\'' +
                ", pages=" + pages +
                '}';
    }
}
