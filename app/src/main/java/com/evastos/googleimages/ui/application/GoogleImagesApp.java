package com.evastos.googleimages.ui.application;

import android.app.Application;

import com.evastos.googleimages.utils.AuthImageDownloader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class GoogleImagesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    private void initImageLoader() {
        final DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory
                        (true).resetViewBeforeLoading(false).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
        // Create global configuration and initialize ImageLoader with this config
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .imageDownloader(new AuthImageDownloader(this))
                .defaultDisplayImageOptions(displayImageOptions).build();
        ImageLoader.getInstance().init(config);
    }

}
