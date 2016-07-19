package com.vsa.filmoteca.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vsa.filmoteca.R;
import com.vsa.paperknife.CellDataProvider;
import com.vsa.paperknife.CellElement;
import com.vsa.paperknife.CellViewHolder;
import com.vsa.paperknife.DataTarget;
import com.vsa.paperknife.PaperKnife;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by seldon on 27/03/15.
 */
public class EventsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<? extends CellElement> mEvents;
    private PaperKnife mPaperKnife;

    public EventsAdapter(Context context, List<? extends CellElement> events, CellDataProvider dataProvider) {
        mEvents = events;
        mInflater = LayoutInflater.from(context);
        mPaperKnife = new PaperKnife(dataProvider);
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public CellElement getItem(int position) {
        return mEvents.get(position);
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

        mPaperKnife.bind(mEvents.get(position), viewHolder);

        return convertView;
    }

    public static class ViewHolder implements CellViewHolder {

        @BindView(R.id.textview_row_movie_title)
        public TextView textViewTitle;
        @BindView(R.id.textview_row_movie_date)
        public TextView textViewDate;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @DataTarget("Title")
        public void setTitle(String title) {
            textViewTitle.setText(title);
        }

        @DataTarget("Date")
        public void setDate(String date) {
            textViewDate.setText(date);
        }

    }

}
