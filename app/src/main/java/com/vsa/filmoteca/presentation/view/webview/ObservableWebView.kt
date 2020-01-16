package com.vsa.filmoteca.view.webview

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

/**
 * Created by seldon on 14/03/15.
 */
class ObservableWebView : WebView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    var onScrollChangedCallback: ((left: Int, top: Int, oldLeft: Int, oldTop: Int) -> Any)? = null
    var onOverScrollListener: (() -> Any)? = null

    private var startedOverScrollingY: Boolean = false

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (t < oldt)
            startedOverScrollingY = false
        onScrollChangedCallback?.invoke(l, t, oldl, oldt)
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        if (scrollY > 0 && clampedY && !startedOverScrollingY) {
            startedOverScrollingY = true
            onOverScrollListener?.invoke()
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }

}
