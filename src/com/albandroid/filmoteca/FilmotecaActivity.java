package com.albandroid.filmoteca;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.albandroid.dialog.DialogManager;
import com.albandroid.dialog.interfaces.OkCancelDialogListener;
import com.albandroid.dialog.interfaces.SimpleDialogListener;
import com.albandroid.filmoteca.utils.ChangeLog;
import com.albandroid.filmoteca.utils.Constants;
import com.albandroid.filmoteca.utils.NetworkUtils;


public class FilmotecaActivity extends ListActivity{
	/** Called when the activity is first created. */
	ArrayList<HashMap<String, String>> Peliculas;
	GetItemsTask getItemsTask;
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
    	
    	
        // TODO Auto-generated catch block
    }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(getListAdapter()==null){
			if(!NetworkUtils.isNetworkAvailable(this)){
	     		networkRequest();
	     	}else{
	     		//Creo y lanzo un AsyncTask para conseguir los datos y actualizar el ListActivity
		     	getItemsTask=new GetItemsTask(this);
		     	getItemsTask.execute();
		     	//Lanzo este hilo para recibir la respuesta del anterior AsyncTask
		     	new Thread(new Runnable(){

					public void run() {
						try {
							Peliculas=getItemsTask.get();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
		     		
		     	}).start();
	     	}
		}
	}
    public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.acercade, menu);
		return true;
	}
    public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case R.id.acercade:
			Intent acercade=new Intent(this,AcercaDeActivity.class);
			
			startActivity(acercade);
			break;
		}
		return false;
	}
    protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String url=Peliculas.get(position).get(Constants.PARAM_ID_URL);
		String fecha=Peliculas.get(position).get(Constants.PARAM_ID_FECHA);
		String titulo=Peliculas.get(position).get(Constants.PARAM_ID_TITULO);
		
	
		Intent i=new Intent(this,DetalleActivity.class);
		i.putExtra(Constants.PARAM_ID_URL, url);
		i.putExtra(Constants.PARAM_ID_FECHA, fecha);
		i.putExtra(Constants.PARAM_ID_TITULO, titulo);
		startActivity(i);

	}
	private void networkRequest() {

		if(!NetworkUtils.isNetworkAvailable(this)){

			DialogManager.showOkCancelDialog(this, R.string.warning_no_internet_connection, 
				new OkCancelDialogListener(){

					public void onAccept() {
						// TODO Auto-generated method stub
						startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
					}

					public void onCancel() {
						// TODO Auto-generated method stub
						FilmotecaActivity.this.finish();
					}
				
				}
			);
		}
	}
	public void timeOutDialog(){

		DialogManager.showSimpleDialog(this, R.string.warning_no_films_recived,
				new SimpleDialogListener(){

					public void onAccept() {
						// TODO Auto-generated method stub
						FilmotecaActivity.this.finish();
					}
			
		});
    }

    private class GetItemsTask extends AsyncTask< Void, Void, ArrayList<HashMap<String, String>>>{
    	ArrayList<HashMap<String, String>> Items=new ArrayList<HashMap<String, String>>();
    	ProgressDialog dialog;
    	Context context;
    	GetItemsTask(Context c){
    		super();
    		context=c;
    	}
    	protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
    		Log.d("Prueba: ","Entra");
    		if(result==null){
        		dialog.dismiss();
        		timeOutDialog();
        	}else if(result.isEmpty()){
        		dialog.dismiss();
        		timeOutDialog();
        	}else{
        	
        		String[] from=new String[] {Constants.PARAM_ID_TITULO,Constants.PARAM_ID_FECHA};
        		int[] to=new int[]{R.id.titulo,R.id.fecha};
        		SimpleAdapter ListaPeliculas=new SimpleAdapter(context,result,R.layout.pelicula_row,from,to);
        		setListAdapter(ListaPeliculas);
        		dialog.dismiss();
        	}
    		//La clase ChangeLog muestra los cambios en la ultima versión
	    	ChangeLog cl = new ChangeLog(context);
		    if (cl.firstRun())
		        cl.getLogDialog().show();
    	}
    	@Override
    	 protected void onPreExecute() {
    		dialog = ProgressDialog.show(context, "", 
	         		"Cargando...", true,false);
     		dialog.show();
    	 }
		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				Void... params) {
			// TODO Auto-generated method stub
			Items=NetworkUtils.getItems(Constants.TIMEOUT_APP);
			return Items;
		}
    }
}
