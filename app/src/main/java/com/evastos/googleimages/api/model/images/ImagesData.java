package com.evastos.googleimages.api.model.images;

import java.util.List;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class ImagesData {

    private List<ImageResult> results;

    private ImagesCursor cursor;

    public ImagesData(List<ImageResult> results, ImagesCursor cursor) {
        this.results = results;
        this.cursor = cursor;
    }

    public ImagesCursor getCursor() {
        return cursor;
    }

    public List<ImageResult> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "ImagesData{" +
                "results=" + results +
                ", cursor=" + cursor +
                '}';
    }
}
