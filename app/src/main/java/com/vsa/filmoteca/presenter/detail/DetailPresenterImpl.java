package com.vsa.filmoteca.presenter.detail;

import com.vsa.filmoteca.interactor.FilmotecaInteractor;
import com.vsa.filmoteca.interactor.FilmotecaInteractorImpl;
import com.vsa.filmoteca.model.event.BUS;
import com.vsa.filmoteca.presenter.utils.StringUtils;
import com.vsa.filmoteca.view.DetailView;

import rx.Observer;

/**
 * Created by seldon on 13/03/15.
 */
public class DetailPresenterImpl implements DetailPresenter, Observer<String> {

    private String mContentUrl;
    private String mTitle;

    private FilmotecaInteractor mInteractor = new FilmotecaInteractorImpl();
    private DetailView mView;

    public DetailPresenterImpl(DetailView detailView) {
        mView = detailView;
    }

    @Override
    public void onCreate(String url, String movieTitle) {
        BUS.getInstance().register(this);
        if (!StringUtils.isEmpty(url)) {
            mTitle = movieTitle;
            mContentUrl = url;
            mView.setWebViewContent("<html></html>", url);
            mView.showMovieTitle(mTitle);
            loadContent(url);
        }
    }

    @Override
    public void onDestroy() {
        BUS.getInstance().unregister(this);
    }

    @Override
    public void onShareButtonClick() {
        mView.showShareDialog();
    }

    @Override
    public void onShowInBrowserButtonClick() {
        mView.launchBrowser(mContentUrl);
    }

    @Override
    public void onFilmAffinitySearchButtonClick() {
        launchFilmaffinitySearch();
    }

    @Override
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

    @Override
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

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        mView.showTimeOutDialog();
        //Probably this error comes from an inconsistent widget data. We must to update
        //the widget information to match the entries for the next time.
        mView.updateWidget();
    }

    @Override
    public void onNext(String html) {
        if (StringUtils.isEmpty(html))
            mView.showTimeOutDialog();
        else
            mView.setWebViewContent(html, mContentUrl);
        mView.showContent();
        mView.hideProgressDialog();
    }
}

