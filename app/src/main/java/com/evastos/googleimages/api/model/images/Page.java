package com.evastos.googleimages.api.model.images;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class Page {

    private String label;

    private int start;

    public Page(String label, int start) {
        this.label = label;
        this.start = start;
    }

    public String getLabel() {
        return label;
    }

    public int getStart() {
        return start;
    }

    @Override
    public String toString() {
        return "Page{" +
                "label='" + label + '\'' +
                ", start=" + start +
                '}';
    }
}
