package com.vsa.filmoteca.view.dialog

import android.app.ProgressDialog
import android.content.Context

/**
 * Created by albertovecinasanchez on 6/8/15.
 */
object ProgressDialogManager {

    private var progressDialog: ProgressDialog? = null

    private fun showProgressDialog(context: Context, message: String): ProgressDialog? {
        if (progressDialog != null && progressDialog!!.isShowing)
            hideProgressDialog()
        progressDialog = ProgressDialog.show(context, null, message, true, false)
        return progressDialog
    }

    fun showProgressDialog(context: Context, messageId: Int): ProgressDialog? {
        val message = context.getString(messageId)
        return showProgressDialog(context, message)

    }

    fun hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
            progressDialog = null
        }

    }

}
