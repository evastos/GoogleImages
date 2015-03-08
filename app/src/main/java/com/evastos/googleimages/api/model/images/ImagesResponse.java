package com.evastos.googleimages.api.model.images;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class ImagesResponse extends BaseResponse {

    private ImagesData responseData;

    public ImagesResponse(String responseDetails, int responseStatus, ImagesData responseData) {
        super(responseDetails, responseStatus);
        this.responseData = responseData;
    }

    public ImagesData getResponseData() {
        return responseData;
    }

    @Override
    public String toString() {
        return "ImagesResponse{" +
                "responseData=" + responseData +
                '}';
    }
}
