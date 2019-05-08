package com.vsa.filmoteca.presentation

/**
 * Created by albertovecinasanchez on 9/3/16.
 */
abstract class Presenter<T : Any> {

    internal lateinit var view: T

}
