package com.evastos.googleimages.ui.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evastos.googleimages.R;
import com.evastos.googleimages.api.model.images.ImageResult;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eva Stos on 7.3.2015..
 */
public class ImagesAdapter extends ArrayAdapter<ImageResult> {

    private static final String TAG = ImagesAdapter.class.getName();

    private List<ImageResult> imageResults;
    private ImageLoader imageLoader;

    public ImagesAdapter(Context context, List<ImageResult> imageResults) {
        super(context, R.layout.grid_view_item_image, imageResults);
        this.imageResults = new ArrayList<ImageResult>(imageResults);
        imageLoader = ImageLoader.getInstance();
    }

    public List<ImageResult> getImageResults() {
        return imageResults;
    }

    public void addImageResults(List<ImageResult> imageResults) {
        this.imageResults.addAll(imageResults);
        notifyDataSetChanged();
    }

    @Override
    public ImageResult getItem(int position) {
        if (imageResults == null || imageResults.size() <= position) {
            return null;
        }
        return imageResults.get(position);
    }

    @Override
    public int getCount() {
        if (imageResults == null) {
            return 0;
        }
        return imageResults.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageViewHolder holder;
        if (convertView == null) {
            holder = new ImageViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_view_item_image, parent, false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.grid_view_item_image_view);
            holder.failedToLoadTextView = (TextView) convertView.findViewById(R.id
                    .grid_view_item_image_loading_failed_text_view);
            holder.failedToLoadTextView.setVisibility(View.GONE);
            convertView.setTag(holder);
        } else {
            holder = (ImageViewHolder) convertView.getTag();
        }

        holder.onBind(getItem(position));
        return convertView;
    }

    class ImageViewHolder {
        ImageView imageView;
        TextView failedToLoadTextView;

        void onBind(final ImageResult imageResult) {
            imageLoader.cancelDisplayTask(imageView);
            imageView.setImageBitmap(null);

            if (imageResult == null || imageResult.getTbUrl() == null) {
                failedToLoadTextView.setVisibility(View.VISIBLE);
                return;
            }

            failedToLoadTextView.setVisibility(View.GONE);
            imageLoader.displayImage(imageResult.getTbUrl(), imageView,
                    new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            failedToLoadTextView.setVisibility(View.VISIBLE);
                            Log.e(TAG, "onLoadingFailed - imageUri: " + imageUri);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            Log.i(TAG, "onLoadingComplete - imageUri: " + imageUri);
                            imageView.setImageBitmap(loadedImage);
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {
                            Log.i(TAG, "onLoadingCancelled - imageUri: " + imageUri);
                        }
                    });
        }
    }
}
