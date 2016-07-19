package com.vsa.filmoteca.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.vsa.filmoteca.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.layout_about_us_container)
    LinearLayout mLayoutAboutUsContainer;
    @BindView(R.id.layout_about_us_dialog)
    LinearLayout mLayoutAboutUsDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        mLayoutAboutUsContainer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v != mLayoutAboutUsDialog)
            finish();
    }
}
