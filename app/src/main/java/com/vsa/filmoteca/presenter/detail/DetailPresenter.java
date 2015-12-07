package com.vsa.filmoteca.presenter.detail;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by seldon on 13/03/15.
 */
public interface DetailPresenter extends SwipeRefreshLayout.OnRefreshListener {

    void onCreate(String url, String movieTitle);

    void onDestroy();

    void onRefresh();

    void onShareButtonClick();

    void onShowInBrowserButtonClick();

    void onFilmAffinitySearchButtonClick();

    void onAboutUsButtonClick();

    void onFabClick();

}
