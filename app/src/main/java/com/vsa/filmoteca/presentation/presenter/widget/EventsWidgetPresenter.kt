package com.vsa.filmoteca.presentation.presenter.widget

import com.vsa.filmoteca.presentation.view.EventsWidgetView

/**
 * Created by Alberto Vecina Sánchez on 2019-05-15.
 */
interface EventsWidgetPresenter {

    fun setView(view: EventsWidgetView)

    fun onUpdate()

    fun onButtonLeftClick()

    fun onButtonRightClick()

}