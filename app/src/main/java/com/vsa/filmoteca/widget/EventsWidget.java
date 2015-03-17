package com.vsa.filmoteca.widget;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RemoteViews;

import com.vsa.filmoteca.MainActivity;
import com.vsa.filmoteca.R;
import com.vsa.filmoteca.database.WidgetDataSource;
import com.vsa.filmoteca.utils.Constants;
import com.vsa.filmoteca.utils.NetworkUtils;

public class EventsWidget extends AppWidgetProvider implements EventsWidgetView{

    private int mCurrentMovieIndex = 0;
    private int mMoviesListSize = 0;

    private RemoteViews mViews;
    private Intent mIntent;
    private PendingIntent mPendingIntent;
    private AppWidgetManager mAppWidgetManager;
    private int[] mAppWidgetIds;
    private ArrayList<HashMap<String,String>> mMovies;

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            initWidget(context);
	        //Lanzando el proceso de actualización
	        GetItemsTask task=new GetItemsTask(context);
	        task.execute();
	        
	}


    @Override
    public void initWidget(Context context) {
        mIntent = new Intent(context, EventsWidget.class);
        mViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        mAppWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), EventsWidget.class.getName());
        mAppWidgetIds = mAppWidgetManager.getAppWidgetIds(thisAppWidget);
    }
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context,intent);
		mViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	    ComponentName thisAppWidget = new ComponentName(context.getPackageName(), EventsWidget.class.getName());
	    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
		
	    
	    if(intent.getAction().equals(Constants.ACTION_WIDGET_LEFT) || intent.getAction().equals(Constants.ACTION_WIDGET_RIGHT)){
	    	//Obtenemos el indice actual y el tamaño de la base de datos
    		SharedPreferences mySharedPreferences=context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME,Context.MODE_PRIVATE);
    		mCurrentMovieIndex =mySharedPreferences.getInt(Constants.SHAREDPREFERENCES_CURRENT, 0);
    		mMoviesListSize =mySharedPreferences.getInt(Constants.SHAREDPREFERENCES_SIZE, 0);
	    	if(mMoviesListSize !=0){
	    		if (intent.getAction().equals(Constants.ACTION_WIDGET_LEFT)) {
	    			if (mCurrentMovieIndex >0){
	    	        	mCurrentMovieIndex--;
	    	        	//Actualizamos el valor del indice
	    	        	SharedPreferences.Editor mySharedPreferencesEditor = mySharedPreferences.edit();
	        	        mySharedPreferencesEditor.putInt(Constants.SHAREDPREFERENCES_CURRENT, mCurrentMovieIndex);
	        	        mySharedPreferencesEditor.commit();
	    			}
	    		} else if (intent.getAction().equals(Constants.ACTION_WIDGET_RIGHT)) {
	    			if (mCurrentMovieIndex <(mMoviesListSize -1)){
	        	       	mCurrentMovieIndex++;
	        	        //Actualizamos el valor del indice
	        	       	SharedPreferences.Editor mySharedPreferencesEditor = mySharedPreferences.edit();
	        	        mySharedPreferencesEditor.putInt(Constants.SHAREDPREFERENCES_CURRENT, mCurrentMovieIndex);
	        	        mySharedPreferencesEditor.commit();
	    			}
	    		}
	    		//Get the movie from database
	    		WidgetDataSource widgetDataSource = new WidgetDataSource(context);
                widgetDataSource.open();
                HashMap<String, String> movie = widgetDataSource.getMovie(mCurrentMovieIndex);
                widgetDataSource.close();
	    		//Preparamos la vista
    	        Intent active = new Intent(context, EventsWidget.class);
    	        active.setAction(Constants.ACTION_WIDGET_LEFT);
    	        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
    	        mViews.setOnClickPendingIntent(R.id.left, actionPendingIntent);
    	        
    	        active.setAction(Constants.ACTION_WIDGET_RIGHT);
    	        actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
    	        mViews.setOnClickPendingIntent(R.id.right, actionPendingIntent);

    	        active = new Intent(context, MainActivity.class);
                active.putExtra(MainActivity.EXTRA_MOVIE, movie);
	        	actionPendingIntent = PendingIntent.getActivity(context, 0, active, PendingIntent.FLAG_UPDATE_CURRENT);
	        	mViews.setOnClickPendingIntent(R.id.widgetInfoLayout, actionPendingIntent);
    	       	mViews.setTextViewText(R.id.widgetTitleText, movie.get(Constants.PARAM_ID_TITULO));
    	       	mViews.setTextViewText(R.id.widgetDateText, movie.get(Constants.PARAM_ID_FECHA));
    	       	mViews.setTextViewText(R.id.widgetPageText, Integer.toString(mCurrentMovieIndex + 1) + " / " + mMoviesListSize);
    	       	
    	       	appWidgetManager.updateAppWidget(appWidgetIds, mViews);
	    	}
	    } 
	}


    @Override
    public void showProgress() {
        mViews.setViewVisibility(R.id.widgetUpdateButton, View.GONE);
        mViews.setViewVisibility(R.id.widgetInfoLayout, View.GONE);
        mViews.setViewVisibility(R.id.widgetProgressBar, View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mViews.setViewVisibility(R.id.widgetProgressBar, View.GONE);
        mViews.setViewVisibility(R.id.widgetUpdateButton, View.GONE);
        mViews.setViewVisibility(R.id.widgetInfoLayout, View.VISIBLE);

    }

    @Override
    public void showRefreshButton() {
        mViews.setViewVisibility(R.id.widgetInfoLayout, View.GONE);
        mViews.setViewVisibility(R.id.widgetProgressBar, View.GONE);
        mViews.setViewVisibility(R.id.widgetUpdateButton, View.VISIBLE);
    }

    private class GetItemsTask extends AsyncTask<String, Void, String> {
		Context context;

		
		GetItemsTask(Context c){
			super();
			context = c;

		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//Configurando los botones
	        mIntent.setAction(Constants.ACTION_WIDGET_LEFT);
	        mPendingIntent = PendingIntent.getBroadcast(context, 0, mIntent, 0);
	        mViews.setOnClickPendingIntent(R.id.left, mPendingIntent);

	        mIntent.setAction(Constants.ACTION_WIDGET_RIGHT);
	        mPendingIntent = PendingIntent.getBroadcast(context, 0, mIntent, 0);
	        mViews.setOnClickPendingIntent(R.id.right, mPendingIntent);
	        
	        //Muestro el cargando
            showProgress();
			
		    //Actualizando widget

		    mAppWidgetManager.updateAppWidget(mAppWidgetIds, mViews);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
		    
		    //Solicitando datos
		    if(NetworkUtils.isNetworkAvailable((context))){
				mCurrentMovieIndex = 0;
			    mMovies = NetworkUtils.getItems(Constants.TIMEOUT_WIDGET);
		    }else{
		    	mMovies = null;
		    }
		    return null;
		    
		}
		    @Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (mMovies == null){
			    	showRefreshButton();
                    mIntent = new Intent(context, EventsWidget.class);
                    mIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                    int[] ids = {R.xml.appwidget_info};
                    mIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
			        mPendingIntent = PendingIntent.getBroadcast(context, 0, mIntent, 0);
			        mViews.setOnClickPendingIntent(R.id.widgetUpdateButton, mPendingIntent);
			    }else{
			    	 if(mMovies.size()!=0){
			    		 	//Actualizando base de datos
                            WidgetDataSource widgetDataSource = new WidgetDataSource(context);
                            widgetDataSource.open();
                            widgetDataSource.clearMovies();
                            for(int x=0;x< mMovies.size();x++)
                                widgetDataSource.insertMovie(x, mMovies.get(x));
                            widgetDataSource.close();

			    	        //Estableciendo elemento actual y tamaño de la base de datos
			    	        SharedPreferences mySharedPreferences=context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME,Context.MODE_PRIVATE);
			    	        SharedPreferences.Editor mySharedPreferencesEditor = mySharedPreferences.edit();
			    	        mySharedPreferencesEditor.putInt(Constants.SHAREDPREFERENCES_CURRENT, 0);
			    	        mySharedPreferencesEditor.putInt(Constants.SHAREDPREFERENCES_SIZE, mMovies.size());
			    	        mySharedPreferencesEditor.commit();
			    	        		    		 
			    	        //Configurando la vista
			    		 	mIntent = new Intent(context, EventsWidget.class);
			    	        mIntent.setAction(Constants.ACTION_WIDGET_LEFT);
			    	        mPendingIntent = PendingIntent.getBroadcast(context, 0, mIntent, 0);
			    	        mViews.setOnClickPendingIntent(R.id.left, mPendingIntent);
			    	        
			    	        mIntent.setAction(Constants.ACTION_WIDGET_RIGHT);
			    	        mPendingIntent = PendingIntent.getBroadcast(context, 0, mIntent, 0);
			    	        mViews.setOnClickPendingIntent(R.id.right, mPendingIntent);
			    	        
			    	        mIntent = new Intent(context, MainActivity.class);
                            if(mMovies != null && mMovies.size()>0)
                                mIntent.putExtra(MainActivity.EXTRA_MOVIE, mMovies.get(mCurrentMovieIndex));
				        	mPendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				        	mViews.setOnClickPendingIntent(R.id.widgetInfoLayout, mPendingIntent);
                            hideProgress();
				        	mViews.setTextViewText(R.id.widgetTitleText, mMovies.get(mCurrentMovieIndex).get(Constants.PARAM_ID_TITULO));
				        	mViews.setTextViewText(R.id.widgetDateText, mMovies.get(mCurrentMovieIndex).get(Constants.PARAM_ID_FECHA));
				        	mViews.setTextViewText(R.id.widgetPageText, Integer.toString(mCurrentMovieIndex + 1) + " / " + mMovies.size());
			        	
			    	 }
						 
				    }
				//Actualiza la vista del widget
				mAppWidgetManager.updateAppWidget(mAppWidgetIds, mViews);
		   
		}
		
	}
}
