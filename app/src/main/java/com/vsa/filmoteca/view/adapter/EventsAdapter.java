package com.vsa.filmoteca.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vsa.filmoteca.R;

import java.util.List;

/**
 * Created by seldon on 27/03/15.
 */
public class EventsAdapter extends BaseAdapter{

    LayoutInflater mInflater;
    List<Event> mMoviesList;

    public EventsAdapter(Context context, List<Event> moviesList){
        mMoviesList = moviesList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mMoviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMoviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = mInflater.inflate(R.layout.row_movie, null);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if(holder == null) {
            holder = new ViewHolder();
            holder.textViewTitle = (TextView) convertView
                    .findViewById(R.id.textview_row_movie_title);
            holder.textViewDate = (TextView) convertView
                    .findViewById(R.id.textview_row_movie_date);
        }

        Event event = mMoviesList.get(position);

        holder.textViewTitle.setText(event.getTitle());
        holder.textViewDate.setText(event.getDate());
        convertView.setTag(holder);

        return convertView;
    }

    private static class ViewHolder {
        public TextView textViewTitle;
        public TextView textViewDate;
    }

    public interface Event{
        public String getTitle();
        public String getDate();
    }

}
