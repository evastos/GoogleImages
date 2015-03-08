package com.evastos.googleimages.api.parser.images;

import com.evastos.googleimages.api.model.images.ImagesResponse;
import com.evastos.googleimages.api.parser.Parser;
import com.evastos.googleimages.utils.gson.Gson;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class ImagesResponseParser implements Parser<ImagesResponse> {

    @Override
    public ImagesResponse parse(String response) {
        return Gson.getInstance().fromJson(response, ImagesResponse.class);
    }
}
