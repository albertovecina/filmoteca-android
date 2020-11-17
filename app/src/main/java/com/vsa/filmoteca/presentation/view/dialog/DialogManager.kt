package com.vsa.filmoteca.presentation.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.vsa.filmoteca.R
import com.vsa.filmoteca.presentation.view.dialog.interfaces.OkCancelDialogListener

object DialogManager {

    fun showSimpleDialog(context: Context, messageResId: Int, callback: (DialogInterface) -> Unit) {
        val builder = AlertDialog.Builder(context, R.style.DialogStyle)
        builder.setCancelable(false)
        builder.setTitle(R.string.dialog_title)
        builder.setMessage(messageResId)
        builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
            callback.invoke(dialog)
            dialog.dismiss()
        }
        builder.show()
    }

    fun showOkCancelDialog(context: Context, messageResId: Int, okCancelDialogListener: OkCancelDialogListener) {
        val builder = AlertDialog.Builder(context, R.style.DialogStyle)
        builder.setCancelable(false)
        builder.setTitle(R.string.dialog_title)
        builder.setMessage(messageResId)
        builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
            okCancelDialogListener.onAcceptButtonPressed()
            dialog.dismiss()
        }
        builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
            okCancelDialogListener.onCancelButtonPressed()
            dialog.dismiss()
        }
        builder.show()
    }

}
