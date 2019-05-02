package com.vsa.filmoteca.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vsa.filmoteca.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by seldon on 27/03/15.
 */
public class EventsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private EventDataProvider mDataProvider;

    public EventsAdapter(Context context, EventDataProvider dataProvider) {
        mDataProvider = dataProvider;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDataProvider.getSize();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_movie, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewDate.setText(mDataProvider.getDate(position));
        viewHolder.textViewTitle.setText(mDataProvider.getTitle(position));
        return convertView;
    }

    public static class ViewHolder {

        @BindView(R.id.textview_row_movie_title)
        public TextView textViewTitle;
        @BindView(R.id.textview_row_movie_date)
        public TextView textViewDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setTitle(String title) {
            textViewTitle.setText(title);
        }

        public void setDate(String date) {
            textViewDate.setText(date);
        }

    }

}
