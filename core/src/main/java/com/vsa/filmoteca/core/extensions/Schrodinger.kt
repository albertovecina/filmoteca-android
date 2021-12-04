package com.vsa.filmoteca.core.extensions

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class Schrodinger<T>(strongReference: T) {

    private val weakReference = WeakReference<T>(strongReference)

    operator fun getValue(any: Any, property: KProperty<*>): T? = weakReference.get()

}

fun <T> weak(any: T): Schrodinger<T> = Schrodinger(any)