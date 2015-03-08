package com.evastos.googleimages.utils.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class Gson {

    private static com.google.gson.Gson instance = null;

    /**
     * Returns instance of Gson with field naming policy = IDENTITY
     *
     * @return Gson instance
     */
    public static com.google.gson.Gson getInstance() {
        if (instance == null) {
            instance = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        }
        return instance;
    }
}
