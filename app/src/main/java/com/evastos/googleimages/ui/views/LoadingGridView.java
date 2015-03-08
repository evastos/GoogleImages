package com.evastos.googleimages.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.GridView;

/**
 * Created by Eva Stos on 8.3.2015..
 */
public class LoadingGridView extends GridView implements AbsListView.OnScrollListener {

    private boolean isLoading;
    private LoadingListener loadingListener;
    private boolean canLoadMore;

    public LoadingGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(this);
        init();
    }

    public LoadingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
        init();
    }

    public LoadingGridView(Context context) {
        super(context);
        this.setOnScrollListener(this);
        init();
    }

    public void setLoadingListener(LoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }

    public void init() {
        canLoadMore = true;
        isLoading = false;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (getAdapter() == null)
            return;

        if (getAdapter().getCount() == 0)
            return;

        if (canLoadMore) {
            int l = visibleItemCount + firstVisibleItem;
            if (l >= totalItemCount && !isLoading) {
                if (loadingListener != null) {
                    loadingListener.onLoadMoreData(this);
                }
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    public interface LoadingListener {
        public void onLoadMoreData(LoadingGridView loadingGridView);
    }
}
