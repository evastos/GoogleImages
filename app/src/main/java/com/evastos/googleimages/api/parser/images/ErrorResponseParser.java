package com.evastos.googleimages.api.parser.images;

import com.evastos.googleimages.api.model.images.BaseResponse;
import com.evastos.googleimages.api.model.images.ErrorResponse;
import com.evastos.googleimages.api.parser.Parser;
import com.evastos.googleimages.utils.gson.Gson;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class ErrorResponseParser implements Parser<ErrorResponse> {

    @Override
    public ErrorResponse parse(String response) {
        BaseResponse baseResponse = Gson.getInstance().fromJson(response, BaseResponse.class);
        if (baseResponse != null) {
            String responseDetails = baseResponse.getResponseDetails();
            if (responseDetails != null) {
                return new ErrorResponse(responseDetails);
            }
        }
        return new ErrorResponse("");
    }
}
