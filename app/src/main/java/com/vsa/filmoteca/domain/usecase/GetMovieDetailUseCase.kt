package com.vsa.filmoteca.domain.usecase

import com.vsa.filmoteca.data.source.repository.MoviesDataRepository
import com.vsa.filmoteca.data.source.repository.SystemDataRepository
import rx.Observable
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

class GetMovieDetailUseCase @Inject constructor(private val systemDataRepository: SystemDataRepository, private val repository: MoviesDataRepository) {

    fun movieDetail(url: String): Observable<String> {
        return repository.movieDetail(url)
    }

    fun isInternetAvailable(): Boolean = systemDataRepository.isInternetAvailable()

}
