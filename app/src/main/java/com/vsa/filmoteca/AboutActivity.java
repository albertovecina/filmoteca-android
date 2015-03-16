package com.vsa.filmoteca;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AboutActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.layout_about_us_container) LinearLayout mLayoutAboutUsContainer;
    @InjectView(R.id.layout_about_us_dialog) LinearLayout mLayoutAboutUsDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
        ButterKnife.inject(this);
        mLayoutAboutUsContainer.setOnClickListener(this);
	}

    @Override
    public void onClick(View v) {
        if(v != mLayoutAboutUsDialog)
            finish();
    }
}
