package com.vsa.filmoteca.view.activity;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vsa.filmoteca.R;
import com.vsa.filmoteca.model.Movie;
import com.vsa.filmoteca.presenter.MainPresenterImpl;
import com.vsa.filmoteca.view.adapter.EventsAdapter;
import com.vsa.filmoteca.view.dialog.DialogManager;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;
import com.vsa.filmoteca.view.dialog.interfaces.SimpleDialogListener;
import com.vsa.filmoteca.presenter.MainPresenter;
import com.vsa.filmoteca.presenter.utils.ChangeLog;
import com.vsa.filmoteca.view.MainView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements MainView<EventsAdapter.Event>, AdapterView.OnItemClickListener{
	/** Called when the activity is first created. */

    public static final String EXTRA_MOVIE = "extra_movie";

    private MainPresenter mPresenter;

    @InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.listview_movies) ListView mListView;
    private ProgressDialog mProgressDialog;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mPresenter = new MainPresenterImpl(this);

        initViews();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.onResume(intent);
    }

    @Override
	protected void onResume() {
		super.onResume();
        mPresenter.onResume(getIntent());
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		return mPresenter.onCreateOptionsMenu(getMenuInflater(), menu);
	}

    public void initViews() {
        showTitle(0);
        mSwipeRefreshLayout.setOnRefreshListener(mPresenter);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_primary_dark,
                R.color.color_accent,
                R.color.color_primary);
        mListView.setOnItemClickListener(this);
        mProgressDialog = ProgressDialog.show(this, "",
                getString(R.string.loading), true,false);
    }

    public boolean onOptionsItemSelected(MenuItem item){
		return mPresenter.onOptionsItemSelected(item);
	}


    @Override
    public void showTitle(int moviesCount) {
        if(moviesCount < 1)
            getSupportActionBar().setTitle(R.string.title_activity_main);
        else
            getSupportActionBar().setTitle(getString(R.string.title_activity_main) + " (" + moviesCount + ")");
    }

    @Override
    public void showWifiRequestDialog(OkCancelDialogListener okCancelDialogListener){
        DialogManager.showOkCancelDialog(this, R.string.warning_no_internet_connection, okCancelDialogListener);
    }

    @Override
	public void showTimeOutDialog(){
		DialogManager.showSimpleDialog(this, R.string.timeout_dialog_message,
            new SimpleDialogListener() {

                public void onAccept() {
                    MainActivity.this.finish();
                }

            });
    }

    @Override
    public void showNoEventsDialog() {
        DialogManager.showSimpleDialog(this, R.string.warning_no_films_recived,
            new SimpleDialogListener() {

                public void onAccept() {
                    MainActivity.this.finish();
                }

            });
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showChangeLog() {
        //La clase ChangeLog muestra los cambios en la ultima versión
        ChangeLog cl = new ChangeLog(this);
        if (cl.firstRun())
            cl.getLogDialog().show();
    }

    @Override
    public void navigateToDetail(Movie movie) {
        String url= movie.getUrl();
        String fecha= movie.getDate();
        String titulo= movie.getTitle();

        Intent i=new Intent(this,DetailActivity.class);
        i.putExtra(DetailActivity.EXTRA_URL, url);
        i.putExtra(DetailActivity.EXTRA_TITLE, titulo);
        i.putExtra(DetailActivity.EXTRA_DATE, fecha);
        startActivity(i);
    }


    @Override
    public void showAboutUs() {
        Intent acercade = new Intent(this,AboutActivity.class);
        startActivity(acercade);
    }

    @Override
    public void showWifiSettings() {
        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    }

    @Override
    public void setMovies(List<EventsAdapter.Event> moviesList) {
        EventsAdapter eventsAdapter = new EventsAdapter(this, moviesList);
        mListView.setAdapter(eventsAdapter);
    }

    @Override
    public boolean isListLoaded() {
        return !(mListView.getAdapter() == null);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPresenter.onMovieClicked(position);
    }
}
