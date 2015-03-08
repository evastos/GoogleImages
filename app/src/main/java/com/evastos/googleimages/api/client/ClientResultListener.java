package com.evastos.googleimages.api.client;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public interface ClientResultListener<T, S> {
    void onSuccess(HttpClient httpClient, T responseObj);

    void onError(HttpClient httpClient, S errorObj);
}
