package com.vsa.filmoteca.view.adapter

/**
 * Created by Alberto Vecina Sánchez on 2019-05-02.
 */
interface EventDataProvider {

    fun getTitle(index: Int): String

    fun getDate(index: Int): String

    fun getSize(): Int

}