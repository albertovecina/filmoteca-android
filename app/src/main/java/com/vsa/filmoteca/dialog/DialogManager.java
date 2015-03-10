package com.vsa.filmoteca.dialog;

import com.vsa.filmoteca.R;
import com.vsa.filmoteca.dialog.interfaces.OkCancelDialogListener;
import com.vsa.filmoteca.dialog.interfaces.SimpleDialogListener;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;

public class DialogManager {

	private static Dialog mDialog=null;

	public static void showSimpleDialog(Context context, int messageResId, final SimpleDialogListener simpleDialogListener){
		LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view=mInflater.inflate(R.layout.dialog_simple, null);
		
		TextView textViewMessage= ButterKnife.findById(view, R.id.textview_dialog_message);
		textViewMessage.setText(context.getResources().getString(messageResId));

		Button buttonAccept= ButterKnife.findById(view, R.id.button_dialog_confirm);
		buttonAccept.setOnClickListener(new OnClickListener(){

			public void onClick(View view) {
				// TODO Auto-generated method stub
				simpleDialogListener.onAccept();
				dismissCurrentDialog();
			}
			
		});
		
		
		mDialog=new Dialog(context);
		mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		mDialog.setContentView(view);
		mDialog.show();
	}
	
	public static void showOkCancelDialog(Context context, int messageResId, final OkCancelDialogListener okCancelDialogListener){
		LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=mInflater.inflate(R.layout.dialog_ok_cancel, null);
		
		TextView textViewMessage = ButterKnife.findById(view, R.id.textview_dialog_message);
		textViewMessage.setText(context.getResources().getString(messageResId));

		Button buttonAccept = ButterKnife.findById(view, R.id.button_dialog_confirm);
		buttonAccept.setOnClickListener(new OnClickListener(){

			public void onClick(View view) {
				// TODO Auto-generated method stub
				okCancelDialogListener.onAccept();
				dismissCurrentDialog();
			}
			
		});
		
		Button buttonCancel = ButterKnife.findById(view, R.id.button_dialog_cancel);
		buttonCancel.setOnClickListener(new OnClickListener(){

			public void onClick(View view) {
				// TODO Auto-generated method stub
				okCancelDialogListener.onCancel();
				dismissCurrentDialog();
			}
			
		});
		
		
		mDialog=new Dialog(context);
		mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		mDialog.setContentView(view);
		mDialog.show();
	}
	
	public static void dismissCurrentDialog(){
		if (mDialog!=null)
			mDialog.dismiss();
	}

}
