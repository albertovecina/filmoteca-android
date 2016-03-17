package com.vsa.filmoteca.presentation.detail;

import android.support.v4.widget.SwipeRefreshLayout;

import com.vsa.filmoteca.presentation.Presenter;
import com.vsa.filmoteca.presentation.interactor.MainInteractor;
import com.vsa.filmoteca.presentation.utils.StringUtils;
import com.vsa.filmoteca.view.DetailView;

import javax.inject.Inject;

import rx.Observer;

/**
 * Created by seldon on 13/03/15.
 */
public class DetailPresenter implements SwipeRefreshLayout.OnRefreshListener, Presenter<DetailView>, Observer<String> {

    private String mContentUrl;
    private String mTitle;

    private MainInteractor mInteractor;
    private DetailView mView;

    @Inject
    public DetailPresenter(MainInteractor mainInteractor) {
        mInteractor = mainInteractor;
    }

    public void onCreate(String url, String movieTitle) {
        if (!StringUtils.isEmpty(url)) {
            mTitle = movieTitle;
            mContentUrl = url;
            mView.setWebViewContent("<html></html>", url);
            mView.showMovieTitle(mTitle);
            loadContent(url);
        }
    }

    public void onDestroy() {
    }

    @Override
    public void setView(DetailView detailView) {
        mView = detailView;
    }

    public void onShareButtonClick() {
        mView.showShareDialog();
    }

    public void onShowInBrowserButtonClick() {
        mView.launchBrowser(mContentUrl);
    }

    public void onFilmAffinitySearchButtonClick() {
        launchFilmaffinitySearch();
    }

    public void onAboutUsButtonClick() {
        mView.showAboutUs();
    }

    public void onFabClick() {
        mView.navigateToComments(mTitle);
    }

    public void loadContent(String url) {
        mView.stopRefreshing();
        mView.hideContent();
        mView.showProgressDialog();
        mInteractor.movieDetail(url).subscribe(this);
    }

    public void onRefresh() {
        if (mContentUrl != null && !mContentUrl.isEmpty())
            loadContent(mContentUrl);
    }

    private void clearContent() {

    }

    private void launchFilmaffinitySearch() {
        String searchString = mTitle.replace(" ", "+");
        searchString = StringUtils.removeAccents(searchString);
        String url = "http://m.filmaffinity.com/es/search.php?stext=" + searchString + "&stype=title";
        mView.launchBrowser(url);
    }

    public void onCompleted() {

    }

    public void onError(Throwable e) {
        mView.hideProgressDialog();
        mView.showTimeOutDialog();
        //Probably this error comes from an inconsistent widget data. We must to update
        //the widget information to match the entries for the next time.
        mView.updateWidget();
    }

    public void onNext(String html) {
        if (StringUtils.isEmpty(html))
            mView.showTimeOutDialog();
        else
            mView.setWebViewContent(html, mContentUrl);
        mView.showContent();
        mView.hideProgressDialog();
    }

}

