package com.vsa.filmoteca;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.vsa.filmoteca.utils.Constants;
import com.vsa.filmoteca.utils.StringUtils;

import org.apache.http.protocol.HTTP;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DetailActivity extends Activity implements DetailView{

    @InjectView(R.id.webview) WebView mWebView;
    @InjectView(R.id.detalleTitle) TextView mTitle;

    private ProgressDialog mProgressDialog;


    private DetailPresenter mPresenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.detalle);
        ButterKnife.inject(this);
        initViews();

        mPresenter = new DetailPresenterImpl(this);
        mPresenter.loadContent(getIntent().getStringExtra(Constants.PARAM_ID_URL));

    }


    @Override
    public void initViews() {
        mProgressDialog = ProgressDialog.show(this, "",
                getString(R.string.loading), true,false);
        mTitle.setText(getIntent().getStringExtra(Constants.PARAM_ID_TITULO)
                .substring(1));
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
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		return mPresenter.onCreateOptionsMenu(getMenuInflater(), menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
        return mPresenter.onOptionsItemSelected(item);
	}

    @OnClick({R.id.buscarFA, R.id.buscarFALayout})
	public void showInFilmAffinity(){
		String url="";
		String titulo=this.getIntent().getExtras().getString(Constants.PARAM_ID_TITULO); 
		titulo=titulo.replace(" ", "+");
		
		//Le quito los acentos
		titulo=StringUtils.removeAccents(titulo);
		url="http://m.filmaffinity.com/es/search.php?stext="+titulo;
		Intent i_filmaffinity = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
		startActivity(i_filmaffinity);
	}

    @OnClick({R.id.verNav, R.id.verNavLayout})
    public void showInBrowser(){
		String url=this.getIntent().getStringExtra(Constants.PARAM_ID_URL);
		Intent i_navegar = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
		startActivity(i_navegar);
	}

    @OnClick({R.id.compartir, R.id.compartirLayout})
	public void showShareDialog(){
		String tituloCmpBtn = getIntent().getStringExtra(Constants.PARAM_ID_TITULO);
		String fechaCmpBtn = getString(R.string.share_date) + ": " + getIntent().getStringExtra(Constants.PARAM_ID_FECHA).substring(1);
		String infoCmpBtn = getString(R.string.share_message) + " " +tituloCmpBtn+"\n"+fechaCmpBtn;
		Intent intent=new Intent(Intent.ACTION_SEND);
		intent.setType(HTTP.PLAIN_TEXT_TYPE);
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
		intent.putExtra(Intent.EXTRA_TEXT, infoCmpBtn);
		
		startActivity(Intent.createChooser(intent, getString(R.string.share)));
	}

    @Override
    public void showTimeOutDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.timeout_dialog_message))
               .setCancelable(false)
               .setPositiveButton(R.string.dialog_accept, new DialogInterface.OnClickListener() {
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

    @Override
    public void setWebViewContent(String html) {
        mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8",
                "about:blank");
    }
}