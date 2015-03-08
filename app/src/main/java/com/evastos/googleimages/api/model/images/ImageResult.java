package com.evastos.googleimages.api.model.images;

import java.io.Serializable;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class ImageResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private int width;

    private int height;

    private String imageId;

    private int tbWidth;

    private int tbHeight;

    private String unescapedUrl;

    private String url;

    private String visibleUrl;

    private String title;

    private String titleNoFormatting;

    private String originalContextUrl;

    private String content;

    private String contentNoFormatting;

    private String tbUrl;

    public ImageResult(int width, int height, String imageId, int tbWidth, int tbHeight, String unescapedUrl, String url, String visibleUrl, String title, String titleNoFormatting, String originalContextUrl, String content, String contentNoFormatting, String tbUrl) {
        this.width = width;
        this.height = height;
        this.imageId = imageId;
        this.tbWidth = tbWidth;
        this.tbHeight = tbHeight;
        this.unescapedUrl = unescapedUrl;
        this.url = url;
        this.visibleUrl = visibleUrl;
        this.title = title;
        this.titleNoFormatting = titleNoFormatting;
        this.originalContextUrl = originalContextUrl;
        this.content = content;
        this.contentNoFormatting = contentNoFormatting;
        this.tbUrl = tbUrl;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getImageId() {
        return imageId;
    }

    public int getTbWidth() {
        return tbWidth;
    }

    public int getTbHeight() {
        return tbHeight;
    }

    public String getUnescapedUrl() {
        return unescapedUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getVisibleUrl() {
        return visibleUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleNoFormatting() {
        return titleNoFormatting;
    }

    public String getOriginalContextUrl() {
        return originalContextUrl;
    }

    public String getContent() {
        return content;
    }

    public String getContentNoFormatting() {
        return contentNoFormatting;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    @Override
    public String toString() {
        return "ImageResult{" +
                "width=" + width +
                ", height=" + height +
                ", imageId='" + imageId + '\'' +
                ", tbWidth=" + tbWidth +
                ", tbHeight=" + tbHeight +
                ", unescapedUrl='" + unescapedUrl + '\'' +
                ", url='" + url + '\'' +
                ", visibleUrl='" + visibleUrl + '\'' +
                ", title='" + title + '\'' +
                ", titleNoFormatting='" + titleNoFormatting + '\'' +
                ", originalContextUrl='" + originalContextUrl + '\'' +
                ", content='" + content + '\'' +
                ", contentNoFormatting='" + contentNoFormatting + '\'' +
                ", tbUrl='" + tbUrl + '\'' +
                '}';
    }
}
