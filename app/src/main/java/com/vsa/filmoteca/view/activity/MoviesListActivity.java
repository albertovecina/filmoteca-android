package com.vsa.filmoteca.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vsa.filmoteca.R;
import com.vsa.filmoteca.internal.di.component.ApplicationComponent;
import com.vsa.filmoteca.internal.di.component.DaggerMoviesListComponent;
import com.vsa.filmoteca.internal.di.module.MoviesListModule;
import com.vsa.filmoteca.presentation.movieslist.MoviesListPresenter;
import com.vsa.filmoteca.presentation.utils.ChangeLog;
import com.vsa.filmoteca.view.MainView;
import com.vsa.filmoteca.view.adapter.EventsAdapter;
import com.vsa.filmoteca.view.dialog.DialogManager;
import com.vsa.filmoteca.view.dialog.ProgressDialogManager;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;
import com.vsa.paperknife.CellDataProvider;
import com.vsa.paperknife.CellElement;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoviesListActivity extends BaseActivity implements MainView, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    /**
     * Called when the activity is first created.
     */

    public static final String EXTRA_MOVIE = "extra_movie";

    @Inject
    MoviesListPresenter mPresenter;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.listview_movies)
    ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        initializePresenter();
        onNewIntent(getIntent());
    }

    @Override
    protected void initializeInjector(ApplicationComponent applicationComponent) {
        DaggerMoviesListComponent.builder()
                .applicationComponent(applicationComponent)
                .moviesListModule(new MoviesListModule())
                .build().inject(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Serializable movieInfo = intent.getSerializableExtra(EXTRA_MOVIE);
        if (movieInfo != null) {
            getIntent().removeExtra(MoviesListActivity.EXTRA_MOVIE);
            mPresenter.onCreate(movieInfo);
        } else {
            mPresenter.onCreate(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_refresh:
                mPresenter.onRefreshMenuButtonClick();
                return true;
            case R.id.menu_item_about_us:
                mPresenter.onAboutUsButtonClick();
                return true;
        }
        return false;
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void showTitle(int moviesCount) {
        if (moviesCount < 1)
            getSupportActionBar().setTitle(R.string.title_activity_main);
        else
            getSupportActionBar().setTitle(getString(R.string.title_activity_main) + " (" + moviesCount + ")");
    }

    @Override
    public void showWifiRequestDialog(OkCancelDialogListener okCancelDialogListener) {
        DialogManager.showOkCancelDialog(this, R.string.warning_no_internet_connection, okCancelDialogListener);
    }

    @Override
    public void showTimeOutDialog() {
        DialogManager.showSimpleDialog(this, R.string.timeout_dialog_message,
                dialog -> finish());
    }

    @Override
    public void showNoEventsDialog() {
        DialogManager.showSimpleDialog(this, R.string.warning_no_films_recived,
                dialog -> finish());
    }

    @Override
    public void showProgressDialog() {
        ProgressDialogManager.showProgressDialog(this, R.string.loading);
    }

    @Override
    public void hideProgressDialog() {
        ProgressDialogManager.hideProgressDialog();
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
    public void navigateToDetail(String url, String title, String date) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(DetailActivity.EXTRA_URL, url);
        i.putExtra(DetailActivity.EXTRA_TITLE, title);
        i.putExtra(DetailActivity.EXTRA_DATE, date);
        startActivity(i);
    }


    @Override
    public void showAboutUs() {
        Intent acercade = new Intent(this, AboutActivity.class);
        startActivity(acercade);
    }

    @Override
    public void showWifiSettings() {
        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    }

    @Override
    public void setMovies(List<? extends CellElement> events, CellDataProvider cellDataProvider) {
        EventsAdapter eventsAdapter = new EventsAdapter(this, events, cellDataProvider);
        mListView.setAdapter(eventsAdapter);
    }

    @Override
    public boolean isListLoaded() {
        return !(mListView.getAdapter() == null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPresenter.onMovieRowClick(position);
    }

    private void initViews() {
        showTitle(0);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_primary_dark,
                R.color.color_accent,
                R.color.color_primary);
        mListView.setOnItemClickListener(this);
    }

    private void initializePresenter() {
        mPresenter.setView(this);
    }

}
