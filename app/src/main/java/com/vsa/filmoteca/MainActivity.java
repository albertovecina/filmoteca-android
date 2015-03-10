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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.vsa.filmoteca.dialog.DialogManager;
import com.vsa.filmoteca.dialog.interfaces.OkCancelDialogListener;
import com.vsa.filmoteca.dialog.interfaces.SimpleDialogListener;
import com.vsa.filmoteca.utils.ChangeLog;
import com.vsa.filmoteca.utils.Constants;
import com.vsa.filmoteca.utils.NetworkUtils;


public class MainActivity extends ListActivity implements MainView{
	/** Called when the activity is first created. */

    private MainPresenter mPresenter;

    private ProgressDialog mProgressDialog;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	//Esto quita la barra de titulo.
    	//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
    	//Esto debe ir antes del setContentview
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	setContentView(R.layout.main);
       	//El setFeatureInt debe ir despues del setContentView
    	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.main_title);
    	initViews();
        mPresenter = new MainPresentImpl(this);

    }
	@Override
	protected void onResume() {
		super.onResume();
        mPresenter.onResume();
	}
    public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		return mPresenter.onCreateOptionsMenu(getMenuInflater(), menu);
	}

    @Override
    public void initViews() {
        mProgressDialog = ProgressDialog.show(this, "",
                getString(R.string.loading), true,false);
    }

    public boolean onOptionsItemSelected(MenuItem item){
		return mPresenter.onOptionsItemSelected(item);
	}
    protected void onListItemClick(ListView l, View v, int position, long id) {
        mPresenter.onMovieClicked(position);
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
        i.putExtra(Constants.PARAM_ID_URL, url);
        i.putExtra(Constants.PARAM_ID_FECHA, fecha);
        i.putExtra(Constants.PARAM_ID_TITULO, titulo);
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
        setListAdapter(ListaPeliculas);
    }

    @Override
    public boolean isListLoaded() {
        return !(getListAdapter() == null);
    }

    @Override
    public Context getContext() {
        return this;
    }

}
