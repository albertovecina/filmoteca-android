package com.vsa.filmoteca;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.vsa.filmoteca.dialog.DialogManager;
import com.vsa.filmoteca.dialog.interfaces.OkCancelDialogListener;
import com.vsa.filmoteca.dialog.interfaces.SimpleDialogListener;
import com.vsa.filmoteca.utils.ChangeLog;
import com.vsa.filmoteca.utils.Constants;
import com.vsa.filmoteca.utils.NetworkUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements MainView, AdapterView.OnItemClickListener{
	/** Called when the activity is first created. */

    public static final String EXTRA_MOVIE = "extra_movie";

    private MainPresenter mPresenter;

    @InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.listview_movies) ListView mListView;
    private ProgressDialog mProgressDialog;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
        ButterKnife.inject(this);
        mPresenter = new MainPresentImpl(this);

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

    @Override
    public void initViews() {
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
    public void showWifiRequestDialog(OkCancelDialogListener okCancelDialogListener){
        DialogManager.showOkCancelDialog(this, R.string.warning_no_internet_connection, okCancelDialogListener);
    }

    @Override
	public void showTimeOutDialog(){
		DialogManager.showSimpleDialog(this, R.string.warning_no_films_recived,
				new SimpleDialogListener(){

					public void onAccept() {
						// TODO Auto-generated method stub
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
    public void showDetail(HashMap<String, String> movie) {
        String url= movie.get(Constants.PARAM_ID_URL);
        String fecha= movie.get(Constants.PARAM_ID_FECHA);
        String titulo= movie.get(Constants.PARAM_ID_TITULO);

        Intent i=new Intent(this,DetailActivity.class);
        i.putExtra(DetailActivity.EXTRA_URL, url);
        i.putExtra(DetailActivity.EXTRA_TITLE, titulo);
        i.putExtra(DetailActivity.EXTRA_DATE, fecha);
        startActivity(i);
    }


    @Override
    public void showAboutUs() {
        Intent acercade=new Intent(this,AboutActivity.class);
        startActivity(acercade);
    }

    @Override
    public void showWifiSettings() {
        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    }

    @Override
    public void setMovies(List<HashMap<String, String>> moviesList) {
        String[] from=new String[] {Constants.PARAM_ID_TITULO,Constants.PARAM_ID_FECHA};
        int[] to=new int[]{R.id.titulo,R.id.fecha};
        SimpleAdapter ListaPeliculas=new SimpleAdapter(this, moviesList,R.layout.pelicula_row, from, to);
        mListView.setAdapter(ListaPeliculas);
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
