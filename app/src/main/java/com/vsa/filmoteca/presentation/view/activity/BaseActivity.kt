package com.vsa.filmoteca.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import com.vsa.filmoteca.R
import com.vsa.filmoteca.presentation.view.dialog.progress.LoadingDialog

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

abstract class BaseActivity : AppCompatActivity() {

    private var loadingDialog: LoadingDialog? = null

    fun showLoading() {
        loadingDialog?.dismiss()
        loadingDialog = LoadingDialog(this)
        loadingDialog?.show()
    }

    fun hideLoading() {
        loadingDialog?.dismissWithAnimation()
    }

    fun hideLoadingWithError(message: String) {
        loadingDialog?.showError("", message, getString(R.string.dialog_accept)) {}
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog?.dismiss()
    }
    
}
