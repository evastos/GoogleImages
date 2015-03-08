package com.evastos.googleimages.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evastos.googleimages.R;
import com.evastos.googleimages.database.model.search.SearchHistoryItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Eva Stos on 8.3.2015..
 */
public class SearchHistoryAdapter extends BaseExpandableListAdapter {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy");

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    private List<String> headers;

    private Map<String, List<SearchHistoryItem>> searchHistoryMap;

    private Context context;

    public SearchHistoryAdapter(Context context, List<SearchHistoryItem> searchHistoryItems) {
        super();
        this.context = context;
        setMapAndHeaders(searchHistoryItems);
    }

    private static String getFormattedTime(long time, SimpleDateFormat simpleDateFormat) {
        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(time);
        calendar.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    private void setMapAndHeaders(List<SearchHistoryItem> searchHistoryItems) {
        searchHistoryMap = new LinkedHashMap<String, List<SearchHistoryItem>>();

        String header = null;
        List<SearchHistoryItem> items = new ArrayList<SearchHistoryItem>();
        for (SearchHistoryItem searchHistoryItem : searchHistoryItems) {
            final String formattedDate = getFormattedTime(searchHistoryItem.getTime(), DATE_FORMAT);
            if (header != null && !header.equals(formattedDate)) {
                searchHistoryMap.put(header, new ArrayList<SearchHistoryItem>(items));
                items.clear();
            }
            items.add(searchHistoryItem);
            header = formattedDate;
        }
        if (header != null) {
            searchHistoryMap.put(header, new ArrayList<SearchHistoryItem>(items));
            items.clear();
        }
        headers = new ArrayList<String>(searchHistoryMap.keySet());
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition < headers.size()) {
            String header = headers.get(groupPosition);
            if (searchHistoryMap.containsKey(header)) {
                List<SearchHistoryItem> items = searchHistoryMap.get(header);
                return items.size();
            }
        }
        return 0;
    }

    @Override
    public String getGroup(int groupPosition) {
        if (groupPosition < headers.size()) {
            return headers.get(groupPosition);
        }
        return null;
    }

    @Override
    public SearchHistoryItem getChild(int groupPosition, int childPosition) {
        if (groupPosition < headers.size()) {
            String header = headers.get(groupPosition);
            if (searchHistoryMap.containsKey(header)) {
                List<SearchHistoryItem> items = searchHistoryMap.get(header);
                if (childPosition < items.size()) {
                    return items.get(childPosition);
                }
            }
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_item_search_history_header, parent, false);
            holder.dateTextView = (TextView) convertView.findViewById(R.id
                    .list_view_item_search_history_header_date_text_view);
            holder.headerIconImageView = (ImageView) convertView.findViewById(R.id
                    .list_view_item_search_history_header_icon);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        holder.onBind(getGroup(groupPosition), isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SearchItemViewHolder holder;
        if (convertView == null) {
            holder = new SearchItemViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_item_search_history, parent, false);
            holder.searchQueryTextView = (TextView) convertView.findViewById(R.id.list_view_item_search_history_query_text_view);
            holder.timeTextView = (TextView) convertView.findViewById(R.id
                    .list_view_item_search_history_time_text_view);
            convertView.setTag(holder);
        } else {
            holder = (SearchItemViewHolder) convertView.getTag();
        }

        holder.onBind(getChild(groupPosition, childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    class SearchItemViewHolder {
        TextView searchQueryTextView;
        TextView timeTextView;

        void onBind(final SearchHistoryItem searchHistoryItem) {
            if (searchHistoryItem == null) {
                return;
            }
            searchQueryTextView.setText(searchHistoryItem.getSearchQuery());
            timeTextView.setText(getFormattedTime(searchHistoryItem.getTime(), TIME_FORMAT));
        }
    }

    class HeaderViewHolder {
        TextView dateTextView;
        ImageView headerIconImageView;

        void onBind(final String header, boolean isExpanded) {
            dateTextView.setText(header);
            headerIconImageView.setRotation(isExpanded ? 0 : -90);
        }
    }

}
