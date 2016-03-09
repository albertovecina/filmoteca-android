package com.vsa.filmoteca.data.domain.dataprovider;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.paperknife.CellDataProvider;
import com.vsa.paperknife.DataSource;

/**
 * Created by albertovecinasanchez on 8/3/16.
 */
public class MovieDataProvider implements CellDataProvider {

    //LIST DATA PROVIDERS
    @DataSource("Title")
    public String getTitle(Movie movie) {
        return movie.getTitle();
    }

    @DataSource("Date")
    public String getDate(Movie movie) {
        return movie.getDate();
    }

}
