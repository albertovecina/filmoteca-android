package com.vsa.filmoteca.view.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.vsa.filmoteca.FilmotecaApplication;
import com.vsa.filmoteca.R;
import com.vsa.filmoteca.presentation.detail.DetailPresenter;
import com.vsa.filmoteca.view.DetailView;
import com.vsa.filmoteca.view.dialog.DialogManager;
import com.vsa.filmoteca.view.dialog.ProgressDialogManager;
import com.vsa.filmoteca.view.dialog.interfaces.SimpleDialogListener;
import com.vsa.filmoteca.view.webview.ObservableWebView;
import com.vsa.filmoteca.view.widget.EventsWidget;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailActivity extends AppCompatActivity implements DetailView, View.OnClickListener {

    public static final String EXTRA_DATE = "extra_date";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_URL = "extra_url";

    @InjectView(R.id.wrapper_detail_content)
    ViewGroup mWrapperContent;
    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.webview)
    ObservableWebView mWebView;
    @InjectView(R.id.detalleTitle)
    TextView mTitle;
    @InjectView(R.id.fab_comments)
    FloatingActionButton mFabComments;

    @Inject
    DetailPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        initViews();
        initializeInjector();
        initializePresenter();
        mPresenter.onCreate(getIntent().getStringExtra(EXTRA_URL),
                getIntent().getStringExtra(EXTRA_TITLE));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.onCreate(intent.getStringExtra(EXTRA_URL),
                intent.getStringExtra(EXTRA_TITLE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                mPresenter.onShareButtonClick();
                return true;
            case R.id.menu_item_browser:
                mPresenter.onShowInBrowserButtonClick();
                return true;
            case R.id.menu_item_filmaffinity:
                mPresenter.onFilmAffinitySearchButtonClick();
                return true;
            case R.id.menu_item_refresh:
                mPresenter.onRefresh();
                return true;
            case R.id.menu_item_about_us:
                mPresenter.onAboutUsButtonClick();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    public void initViews() {
        mFabComments.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(mPresenter);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_primary_dark,
                R.color.color_accent,
                R.color.color_primary);
        mWebView.setOnScrollChangedCallback((l, t, oldl, oldt) -> {
            mSwipeRefreshLayout.setEnabled(t == 0);
            if (t < oldt && !mFabComments.isVisible())
                mFabComments.show(true);
        });
        mWebView.setOnOverScollListener(() -> mFabComments.hide(true));
        showMovieTitle(getIntent().getStringExtra(EXTRA_TITLE));

    }

    @Override
    public void showContent() {
        mWrapperContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        mWrapperContent.setVisibility(View.GONE);
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
    public void showMovieTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void updateWidget() {
        Intent intent = new Intent(this, EventsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = {R.xml.appwidget_info};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void launchBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void showShareDialog() {
        String tituloCmpBtn = getIntent().getStringExtra(EXTRA_TITLE);
        String fechaCmpBtn = getString(R.string.share_date) + ": " + getIntent().getStringExtra(EXTRA_DATE).substring(1);
        String infoCmpBtn = getString(R.string.share_message) + " " + tituloCmpBtn + "\n" + fechaCmpBtn;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        intent.putExtra(Intent.EXTRA_TEXT, infoCmpBtn);

        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }

    @Override
    public void showTimeOutDialog() {
        DialogManager.showSimpleDialog(this, R.string.timeout_dialog_message, new SimpleDialogListener() {
            @Override
            public void onAccept() {
                finish();
            }
        });
    }

    @Override
    public void setWebViewContent(String html, String baseUrl) {
        mWebView.loadDataWithBaseURL(baseUrl, html, "text/html", "utf-8",
                "about:blank");
    }

    @Override
    public void navigateToComments(String title) {
        Intent intent = new Intent(this, CommentsActivity.class);
        intent.putExtra(CommentsActivity.EXTRA_TITLE, title);
        startActivity(intent);
    }

    @Override
    public void showAboutUs() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == mFabComments)
            mPresenter.onFabClick();
    }

    private void initializeInjector() {
        FilmotecaApplication.getInstance().getMainComponent().inject(this);
    }

    private void initializePresenter() {
        mPresenter.setView(this);
    }
}
