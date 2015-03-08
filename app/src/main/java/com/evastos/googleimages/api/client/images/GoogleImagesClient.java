package com.evastos.googleimages.api.client.images;

import com.evastos.googleimages.api.client.ClientResultListener;
import com.evastos.googleimages.api.client.Constants;
import com.evastos.googleimages.api.client.HttpClient;
import com.evastos.googleimages.api.client.HttpMethod;
import com.evastos.googleimages.api.model.images.ErrorResponse;
import com.evastos.googleimages.api.model.images.ImagesResponse;
import com.evastos.googleimages.api.parser.images.ErrorResponseParser;
import com.evastos.googleimages.api.parser.images.ImagesResponseParser;
import com.evastos.googleimages.utils.DeviceUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class GoogleImagesClient extends HttpClient<ImagesResponse, ErrorResponse> {

    private static final int MIN_RSZ = 1;
    private static final int MAX_RSZ = 8;
    private static final int MIN_START = 0;

    public GoogleImagesClient() {
        super(new ImagesResponseParser(), new ErrorResponseParser(), HttpMethod.GET);
    }

    /**
     * Executes HTTP Get request to Google Image Search API
     *
     * @param searchExpression  search expression
     * @param start             start index
     * @param resultsPerRequest count of results per request
     */
    public void run(String searchExpression, int start, int resultsPerRequest) {
        runClient(buildUrl(searchExpression, start, resultsPerRequest));
    }

    private String buildUrl(String searchExpression, int start, int resultsPerPage) {
        StringBuilder urlBuilder = new StringBuilder(Constants.GoogleImages.API_URL);
        urlBuilder.append("?");
        urlBuilder.append("v=").append(Constants.GoogleImages.PROTOCOL_VERSION_NUMBER);
        try {
            urlBuilder.append("&q=");
            urlBuilder.append(URLEncoder.encode(searchExpression, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (resultsPerPage >= MIN_RSZ && resultsPerPage <= MAX_RSZ) {
            urlBuilder.append("&rsz=").append(resultsPerPage);
        }
        if (start >= MIN_START) {
            urlBuilder.append("&start=").append(start);
        }
        String ipAddress = DeviceUtils.getIPAddress(true);
        if (ipAddress != null && !ipAddress.isEmpty()) {
            urlBuilder.append("&userip=").append(ipAddress);
        }
        return urlBuilder.toString();
    }

    public interface GoogleImagesResultListener extends ClientResultListener<ImagesResponse,
            ErrorResponse> {
    }

}
