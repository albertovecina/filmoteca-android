package com.vsa.filmoteca.dialog;

import com.vsa.filmoteca.R;
import com.vsa.filmoteca.dialog.interfaces.OkCancelDialogListener;
import com.vsa.filmoteca.dialog.interfaces.SimpleDialogListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;

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
