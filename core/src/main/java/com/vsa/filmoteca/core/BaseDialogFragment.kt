package com.vsa.filmoteca.core

import androidx.fragment.app.DialogFragment

open class BaseDialogFragment<Binding> : DialogFragment() {

    private var _binding: Binding? = null
    protected var binding: Binding
        get() = _binding!!
        set(value) {
            _binding = value
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}