package com.vsa.filmoteca;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.vsa.filmoteca.utils.StringUtils;
import com.vsa.filmoteca.view.ObservableWebView;

import org.apache.http.protocol.HTTP;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailActivity extends ActionBarActivity implements DetailView{

    public static final String EXTRA_DATE="extra_date";
    public static final String EXTRA_TITLE="extra_title";
    public static final String EXTRA_URL="extra_url";

    @InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.webview) ObservableWebView mWebView;
    @InjectView(R.id.detalleTitle) TextView mTitle;

    private ProgressDialog mProgressDialog;


    private DetailPresenter mPresenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.detail);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        mPresenter = new DetailPresenterImpl(this);

        initViews();

        mPresenter.loadContent(getIntent().getStringExtra(EXTRA_URL));
    }


    @Override
    public void initViews() {
        mSwipeRefreshLayout.setOnRefreshListener(mPresenter);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_primary_dark,
                R.color.color_accent,
                R.color.color_primary);
        mWebView.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                mSwipeRefreshLayout.setEnabled(t == 0);
            }
        });
        mProgressDialog = ProgressDialog.show(this, "",
                getString(R.string.loading), true,false);
        mTitle.setText(getIntent().getStringExtra(EXTRA_TITLE)
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
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
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

	public void showInFilmAffinity(){
		String url="";
		String titulo=this.getIntent().getExtras().getString(EXTRA_TITLE);
		titulo=titulo.replace(" ", "+");
		
		//Le quito los acentos
		titulo=StringUtils.removeAccents(titulo);
		url="http://m.filmaffinity.com/es/search.php?stext="+titulo;
		Intent i_filmaffinity = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
		startActivity(i_filmaffinity);
	}

    public void showInBrowser(){
		String url=this.getIntent().getStringExtra(EXTRA_URL);
		Intent i_navegar = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
		startActivity(i_navegar);
	}

	public void showShareDialog(){
		String tituloCmpBtn = getIntent().getStringExtra(EXTRA_TITLE);
		String fechaCmpBtn = getString(R.string.share_date) + ": " + getIntent().getStringExtra(EXTRA_DATE).substring(1);
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
                       finish();
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

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showAboutUs() {
        Intent acercade=new Intent(this,AboutActivity.class);
        startActivity(acercade);
    }
}