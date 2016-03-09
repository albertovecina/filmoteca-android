package com.vsa.filmoteca.view.dialog;

import com.vsa.filmoteca.R;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;
import com.vsa.filmoteca.view.dialog.interfaces.SimpleDialogListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogManager {

	public static void showSimpleDialog(Context context, int messageResId, final SimpleDialogListener simpleDialogListener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(messageResId);
        builder.setPositiveButton(R.string.dialog_accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                simpleDialogListener.onAccept();
                dialog.dismiss();
            }
        });
		builder.show();
	}
	
	public static void showOkCancelDialog(Context context, int messageResId, final OkCancelDialogListener okCancelDialogListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(messageResId);
        builder.setPositiveButton(R.string.dialog_accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                okCancelDialogListener.onAcceptButtonPressed();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                okCancelDialogListener.onCancelButtonPressed();
                dialog.dismiss();
            }
        });
        builder.show();
	}

}