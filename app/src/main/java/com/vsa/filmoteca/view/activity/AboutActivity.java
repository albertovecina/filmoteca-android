package com.vsa.filmoteca.view.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vsa.filmoteca.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.layout_about_us_container)
    LinearLayout layoutAboutUsContainer;
    @BindView(R.id.layout_about_us_dialog)
    LinearLayout layoutAboutUsDialog;
    @BindView(R.id.txt_about_us_build)
    TextView textViewBuild;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        layoutAboutUsContainer.setOnClickListener(this);
        try {
            PackageManager packageManager = this.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            textViewBuild.setText(String.format(getString(R.string.version_code), packageInfo.versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v != layoutAboutUsDialog)
            finish();
    }
}
