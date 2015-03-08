package com.evastos.googleimages.api.model.images;

/**
 * Created by Eva Stos on 7.3.2015..
 */

public class BaseResponse {

    private String responseDetails;

    private int responseStatus;

    public BaseResponse(String responseDetails, int responseStatus) {
        this.responseDetails = responseDetails;
        this.responseStatus = responseStatus;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "responseDetails='" + responseDetails + '\'' +
                ", responseStatus=" + responseStatus +
                '}';
    }
}
