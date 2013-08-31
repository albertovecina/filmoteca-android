package com.albandroid.dialog;

import com.albandroid.dialog.interfaces.OkCancelDialogListener;
import com.albandroid.dialog.interfaces.SimpleDialogListener;
import com.albandroid.filmoteca.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DialogManager {

	private static Dialog mDialog=null;
	
	public static void showSimpleDialog(Context context, int messageResId, final SimpleDialogListener simpleDialogListener){
		LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mView=mInflater.inflate(R.layout.dialog_simple, null);
		
		TextView textViewMessage=(TextView)mView.findViewById(R.id.textview_dialog_message);
		textViewMessage.setText(context.getResources().getString(messageResId));

		Button buttonAccept=(Button)mView.findViewById(R.id.button_dialog_confirm);
		buttonAccept.setOnClickListener(new OnClickListener(){

			public void onClick(View view) {
				// TODO Auto-generated method stub
				simpleDialogListener.onAccept();
				dismissCurrentDialog();
			}
			
		});
		
		
		mDialog=new Dialog(context);
		mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		mDialog.setContentView(mView);
		mDialog.show();
	}
	
	public static void showOkCancelDialog(Context context, int messageResId, final OkCancelDialogListener okCancelDialogListener){
		LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mView=mInflater.inflate(R.layout.dialog_ok_cancel, null);
		
		TextView textViewMessage=(TextView)mView.findViewById(R.id.textview_dialog_message);
		textViewMessage.setText(context.getResources().getString(messageResId));

		Button buttonAccept=(Button)mView.findViewById(R.id.button_dialog_confirm);
		buttonAccept.setOnClickListener(new OnClickListener(){

			public void onClick(View view) {
				// TODO Auto-generated method stub
				okCancelDialogListener.onAccept();
				dismissCurrentDialog();
			}
			
		});
		
		Button buttonCancel=(Button)mView.findViewById(R.id.button_dialog_cancel);
		buttonCancel.setOnClickListener(new OnClickListener(){

			public void onClick(View view) {
				// TODO Auto-generated method stub
				okCancelDialogListener.onCancel();
				dismissCurrentDialog();
			}
			
		});
		
		
		mDialog=new Dialog(context);
		mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		mDialog.setContentView(mView);
		mDialog.show();
	}
	
	public static void dismissCurrentDialog(){
		if (mDialog!=null)
			mDialog.dismiss();
	}

}
