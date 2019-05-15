package com.vsa.filmoteca.presentation.widget

import com.vsa.filmoteca.presentation.Presenter
import com.vsa.filmoteca.view.EventsWidgetView

/**
 * Created by Alberto Vecina Sánchez on 2019-05-15.
 */
abstract class EventsWidgetPresenter : Presenter<EventsWidgetView>() {

    abstract fun onUpdate()

    abstract fun onButtonLeftClick()

    abstract fun onButtonRightClick()

}