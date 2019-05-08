package com.vsa.filmoteca.data.usecase

import com.twitter.sdk.android.core.Session
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import com.vsa.filmoteca.data.repository.TwitterDataRepository
import rx.Observable
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 14/3/16.
 */
class CommentsUseCase @Inject
constructor(private val mRepository: TwitterDataRepository) {

    val activeSession: Session<*>
        get() = mRepository.activeSession

    fun closeActiveSession() {
        mRepository.closeActiveSession()
    }

    fun tweets(session: TwitterSession, hashTag: String): Observable<Search> {
        return mRepository.tweets(session, hashTag)
    }

    fun update(session: Session<*>, hashTag: String, message: String): Observable<Tweet> {
        return mRepository.update(session, hashTag, message)
    }

    fun verifyCredentials(session: Session<*>): Observable<User> {
        return mRepository.verifyCredentials(session)
    }

}
