package com.evastos.googleimages.api.parser;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public interface Parser<T> {
    T parse(String response);
}
