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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.vsa.filmoteca.MainActivity;
import com.vsa.filmoteca.R;
import com.vsa.filmoteca.utils.Constants;
import com.vsa.filmoteca.utils.MyDataBase;
import com.vsa.filmoteca.utils.NetworkUtils;

public class MyWidgetProvider extends AppWidgetProvider {
	int index=0;
	int size=0;

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

	        //Lanzando el proceso de actualización
	        GetItemsTask task=new GetItemsTask(context);
	        task.execute();
	        
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context,intent);
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	    ComponentName thisAppWidget = new ComponentName(context.getPackageName(), MyWidgetProvider.class.getName());
	    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
		
	    
	    if(intent.getAction().equals(Constants.ACTION_WIDGET_LEFT) || intent.getAction().equals(Constants.ACTION_WIDGET_RIGHT)){
	    	//Obtenemos el indice actual y el tamaño de la base de datos
    		SharedPreferences mySharedPreferences=context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME,Context.MODE_PRIVATE);
    		index=mySharedPreferences.getInt(Constants.SHAREDPREFERENCES_CURRENT, 0);
    		size=mySharedPreferences.getInt(Constants.SHAREDPREFERENCES_SIZE, 0);
	    	if(size!=0){
	    		if (intent.getAction().equals(Constants.ACTION_WIDGET_LEFT)) {
	    			if (index>0){
	    	        	index--;
	    	        	//Actualizamos el valor del indice
	    	        	SharedPreferences.Editor mySharedPreferencesEditor = mySharedPreferences.edit();
	        	        mySharedPreferencesEditor.putInt(Constants.SHAREDPREFERENCES_CURRENT, index);
	        	        mySharedPreferencesEditor.commit();
	    			}
	    		} else if (intent.getAction().equals(Constants.ACTION_WIDGET_RIGHT)) {
	    			if (index<(size-1)){
	        	       	index++;
	        	        //Actualizamos el valor del indice
	        	       	SharedPreferences.Editor mySharedPreferencesEditor = mySharedPreferences.edit();
	        	        mySharedPreferencesEditor.putInt(Constants.SHAREDPREFERENCES_CURRENT, index);
	        	        mySharedPreferencesEditor.commit();
	    			}
	    		}
	    		//Abrimos la base de datos
	    		MyDataBase peliculasDB=new MyDataBase(context, Constants.DB_NAME, null, 1);
    	        SQLiteDatabase  myReadableDB = peliculasDB.getReadableDatabase();
	    		Cursor myCursor=myReadableDB.rawQuery("SELECT "+
	    				Constants.PARAM_ID_TITULO+","+Constants.PARAM_ID_FECHA+" FROM Peliculas WHERE cod="+index, null);
    	        myCursor.moveToFirst();
	    		//Preparamos la vista
    	        Intent active = new Intent(context, MyWidgetProvider.class);
    	        active.setAction(Constants.ACTION_WIDGET_LEFT);
    	        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
    	        views.setOnClickPendingIntent(R.id.left, actionPendingIntent);
    	        
    	        active.setAction(Constants.ACTION_WIDGET_RIGHT);
    	        actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
    	        views.setOnClickPendingIntent(R.id.right, actionPendingIntent);
    	        
    	        active = new Intent(context, MainActivity.class);
	        	actionPendingIntent = PendingIntent.getActivity(context, 0, active, 0);
	        	views.setOnClickPendingIntent(R.id.widgetInfoLayout, actionPendingIntent);
	        	Log.d("Prueba: ","Actualiza la vista");
    	       	views.setTextViewText(R.id.widgetTitleText, myCursor.getString(0));
    	       	views.setTextViewText(R.id.widgetDateText, myCursor.getString(1));
    	       	views.setTextViewText(R.id.widgetPageText, Integer.toString(index+1)+" / "+size);
    	       	
    	       	appWidgetManager.updateAppWidget(appWidgetIds, views);
    	       	//Cerramos el cursor y la base de datos
    	    	myCursor.close();
    	       	peliculasDB.close();
	    	}
	    }else{ 
	    	if (intent.getAction().equals(Constants.ACTION_WIDGET_UPDATE)) {
	    
				GetItemsTask task=new GetItemsTask(context);
		        task.execute();
	    	}
	    }	
	}

private class GetItemsTask extends AsyncTask<String, Void, String> {
		Context context;
		Intent active;
		RemoteViews views;
		PendingIntent actionPendingIntent;
		AppWidgetManager appWidgetManager;
		int[] appWidgetIds;
		ArrayList<HashMap<String,String>> Peliculas;
		
		GetItemsTask(Context c){
			super();
			context=c;
			active = new Intent(context, MyWidgetProvider.class);
			views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			appWidgetManager = AppWidgetManager.getInstance(context);
		    ComponentName thisAppWidget = new ComponentName(context.getPackageName(), MyWidgetProvider.class.getName());
		    appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//Configurando los botones
	        active.setAction(Constants.ACTION_WIDGET_LEFT);
	        actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
	        views.setOnClickPendingIntent(R.id.left, actionPendingIntent);
	        
	        active.setAction(Constants.ACTION_WIDGET_RIGHT);
	        actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
	        views.setOnClickPendingIntent(R.id.right, actionPendingIntent);
	        
	        //Muestro el cargando
	        views.setViewVisibility(R.id.widgetUpdateButton, View.GONE);
		    views.setViewVisibility(R.id.widgetInfoLayout, View.GONE);
		    views.setViewVisibility(R.id.widgetPageLayout, View.GONE);
		    views.setViewVisibility(R.id.widgetProgressBar, View.VISIBLE);
			
		    //Actualizando widget
		    
		    appWidgetManager.updateAppWidget(appWidgetIds, views);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
		    
		    //Solicitando datos
		    if(NetworkUtils.isNetworkAvailable((context))){
				index=0;
			    Peliculas=NetworkUtils.getItems(Constants.TIMEOUT_WIDGET);
		    }else{
		    	Peliculas=null;
		    }
		    return null;
		    
		}
		    @Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (Peliculas==null){
			    	views.setViewVisibility(R.id.widgetUpdateButton, View.GONE);
				    views.setViewVisibility(R.id.widgetInfoLayout, View.GONE);
		        	views.setViewVisibility(R.id.widgetProgressBar, View.GONE);
		        	views.setViewVisibility(R.id.widgetUpdateButton, View.VISIBLE);
		        	active = new Intent(context, MyWidgetProvider.class);
		        	active.setAction(Constants.ACTION_WIDGET_UPDATE);
			        actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
			        views.setOnClickPendingIntent(R.id.widgetUpdateButton, actionPendingIntent);
			    }else{
			    	 if(Peliculas.size()!=0){
			    		 	//Actualizando base de datos
			    		 	MyDataBase peliculasDB=new MyDataBase(context, Constants.DB_NAME, null, 1);
			    	        SQLiteDatabase  myWritableDB = peliculasDB.getWritableDatabase();
			    	        myWritableDB.execSQL("DELETE FROM Peliculas");
			    	        for(int x=0;x<Peliculas.size();x++){
			    	        	
			    	        	myWritableDB.execSQL("INSERT INTO Peliculas VALUES ("+Integer.toString(x)+",'"+
			    	        			Peliculas.get(x).get(Constants.PARAM_ID_TITULO)+"','Subtitulo','"+
			    	        			//Peliculas.get(x).get(Constants.PARAM_ID_SUBTITULO)+"','"+
			    	        			Peliculas.get(x).get(Constants.PARAM_ID_DESCRIPCION)+"','"+
			    	        			Peliculas.get(x).get(Constants.PARAM_ID_FECHA)+"','"+
			    	        			Peliculas.get(x).get(Constants.PARAM_ID_URL)+"')");
			    	        }
			    	        myWritableDB.close();
			    	        //Estableciendo elemento actual y tamaño de la base de datos
			    	        SharedPreferences mySharedPreferences=context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME,Context.MODE_PRIVATE);
			    	        SharedPreferences.Editor mySharedPreferencesEditor = mySharedPreferences.edit();
			    	        mySharedPreferencesEditor.putInt(Constants.SHAREDPREFERENCES_CURRENT, 0);
			    	        mySharedPreferencesEditor.putInt(Constants.SHAREDPREFERENCES_SIZE, Peliculas.size());
			    	        mySharedPreferencesEditor.commit();
			    	        		    		 
			    	        //Configurando la vista
			    		 	active = new Intent(context, MyWidgetProvider.class);
			    	        active.setAction(Constants.ACTION_WIDGET_LEFT);
			    	        actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
			    	        views.setOnClickPendingIntent(R.id.left, actionPendingIntent);
			    	        
			    	        active.setAction(Constants.ACTION_WIDGET_RIGHT);
			    	        actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
			    	        views.setOnClickPendingIntent(R.id.right, actionPendingIntent); 
			    	        
			    	        active = new Intent(context, MainActivity.class);
				        	actionPendingIntent = PendingIntent.getActivity(context, 0, active, 0);
				        	views.setOnClickPendingIntent(R.id.widgetInfoLayout, actionPendingIntent);
			    	        
					        views.setViewVisibility(R.id.widgetProgressBar, View.GONE);
					        views.setViewVisibility(R.id.widgetUpdateButton, View.GONE);
					        views.setViewVisibility(R.id.widgetInfoLayout, View.VISIBLE);
					        views.setViewVisibility(R.id.widgetPageLayout, View.VISIBLE); 
				        	views.setTextViewText(R.id.widgetTitleText, Peliculas.get(index).get(Constants.PARAM_ID_TITULO));
				        	views.setTextViewText(R.id.widgetDateText, Peliculas.get(index).get(Constants.PARAM_ID_FECHA));
				        	views.setTextViewText(R.id.widgetPageText, Integer.toString(index+1)+" / "+Peliculas.size());
			        	
			    	 }
						 
				    }
				//Actualiza la vista del widget
				appWidgetManager.updateAppWidget(appWidgetIds, views);
		   
		}
		
	}
}
