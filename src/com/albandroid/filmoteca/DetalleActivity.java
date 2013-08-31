package com.albandroid.filmoteca;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.albandroid.filmoteca.utils.NetworkUtils;
import com.albandroid.filmoteca.utils.Constants;
import com.albandroid.filmoteca.utils.StringUtils;

public class DetalleActivity extends Activity{
/** Called when the activity is first created. */
	//String info;
	Dialog dialog;
	WebView webview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Esto debe ir antes del setContentview
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	setContentView(R.layout.detalle);
    	//El setFeatureInt debe ir despues del setContentView
    	//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.detalle_title);
    	TextView tituloDetalle=(TextView) findViewById(R.id.detalleTitle);
    	tituloDetalle.setText(
    			this.getIntent().getExtras().getString(Constants.PARAM_ID_TITULO).substring(1));
       
    	webview =(WebView)findViewById(R.id.webview);
        GetHTMLTask getHTMLTask=new GetHTMLTask(this);
        getHTMLTask.execute();

    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sharewith, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent intent = new Intent(Intent.ACTION_SEND);
		String titulo=this.getIntent().getExtras().getString(Constants.PARAM_ID_TITULO);
		String fecha="Fecha: "+this.getIntent().getExtras().getString(Constants.PARAM_ID_FECHA);
		String info= "Voy a ir a la Filmoteca a ver:\nTítulo: "+titulo+"\n"+fecha;
		String url;
		switch(item.getItemId()){
		case R.id.compartir:
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_SUBJECT, "Filmoteca de Albacete");
			intent.putExtra(Intent.EXTRA_TEXT, info);
			
			startActivity(Intent.createChooser(intent, getString(R.string.share)));
			break;
		case R.id.navegar:
			url=this.getIntent().getExtras().getString(Constants.PARAM_ID_URL);
			Intent i_navegar = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
			startActivity(i_navegar);
			break;
		case R.id.filmaffinity:
			url="";
			titulo=titulo.replace(" ", "+");
			
			//Le quito los acentos
			titulo=StringUtils.removeAccents(titulo);
			url="http://m.filmaffinity.com/es/search.php?stext="+titulo;
			Intent i_filmaffinity = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
			startActivity(i_filmaffinity);
			break;
		}
		return false;
	}
	private String parseHTML(){
		String pagina,res;
		pagina=NetworkUtils.DownloadText(this.getIntent().getExtras().getString(Constants.PARAM_ID_URL),Constants.TIMEOUT_APP);
		if(pagina==null){
    		return pagina;
    	}
        //Style
		String style="<style type=\"text/css\">img{ max-width:100%!important; height:auto!important;} strong{font-size:13px;} " +
				"*{background-color:#f3f3f3!important;}"+
				"a{font-size:15px!important;}"+
				"p{text-align:center;}"+
				".documentDescription{font-weight:bold;color:#000000; text-align:center;}"+
				".tablaeventos table{ width:100%!important;}"+
				".tablaeventos td{width:50%!important;}"+
				"a {color:#000000; font-weight:bold;}"+
				".vevent{font-size:15px!important; color:#5f5c5c;}"+
				"td{ font-size:15px!important;line-height:18px; vertical-align:top;}"+
				"table{border:1px solid #848484;}"+
				"th{float:left!important;font-size:13px!important;}</style>";
		
        //Parseando Info 
		pagina=pagina.substring(pagina.indexOf("<div class=\"vevent\">"));
		res=style+pagina.substring(0,pagina.indexOf("<div class=\"relatedItems\">"));
		res=res.replaceAll("\\<th", "<td");
		res=res.replaceAll("<\\/th", "</td");
		return res;
	}
	public void buscarFA(View v){
		String url="";
		String titulo=this.getIntent().getExtras().getString(Constants.PARAM_ID_TITULO); 
		titulo=titulo.replace(" ", "+");
		
		//Le quito los acentos
		titulo=StringUtils.removeAccents(titulo);
		url="http://m.filmaffinity.com/es/search.php?stext="+titulo;
		Intent i_filmaffinity = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
		startActivity(i_filmaffinity);
	}
	public void verEnNavegador(View v){
		String url=this.getIntent().getExtras().getString(Constants.PARAM_ID_URL);
		Intent i_navegar = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
		startActivity(i_navegar);
	}
	public void compartir(View v){
		String tituloCmpBtn=this.getIntent().getExtras().getString(Constants.PARAM_ID_TITULO);
		String fechaCmpBtn="Fecha: "+this.getIntent().getExtras().getString(Constants.PARAM_ID_FECHA);
		String infoCmpBtn= "Voy a ir a la Filmoteca a ver:\nTítulo: "+tituloCmpBtn+"\n"+fechaCmpBtn;
		Intent intent=new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Filmoteca de Albacete");
		intent.putExtra(Intent.EXTRA_TEXT, infoCmpBtn);
		
		startActivity(Intent.createChooser(intent, getString(R.string.share)));
	}

	public void timeOutDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No ha sido posible obtener la información, inténtelo de nuevo mas tarde.")
               .setCancelable(false)
               .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        try {
							finish();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                   }
               });
        AlertDialog alert = builder.create();
        alert.show();
    }
		private class GetHTMLTask extends AsyncTask< Void, Void, String>{
	    	String html;
			ProgressDialog dialog;
	    	Context context;
	    	GetHTMLTask(Context c){
	    		super();
	    		context=c;
	    	}
	    	protected void onPostExecute(String result) {
	    		if(result==null){
	    			dialog.dismiss();
	    			timeOutDialog();
	    		}else{
					webview.loadDataWithBaseURL (null, result, "text/html", "utf-8", 
							"about:blank"); 
					//Quitamos la ventana de cargando
					dialog.dismiss();
	    		}
	    	}
	    	@Override
	    	 protected void onPreExecute() {
	    		dialog = ProgressDialog.show(context, "", 
		         		"Cargando...", true,false);
	     		dialog.show();
	    	 }
			@Override
			protected String doInBackground(
					Void... params) {
				// TODO Auto-generated method stub
				html=parseHTML();
				if(html!=null)
					html=StringUtils.unescapeHTML(parseHTML());
				return html;
			}
	    }
}