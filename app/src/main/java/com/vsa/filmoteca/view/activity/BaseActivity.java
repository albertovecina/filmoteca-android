package com.vsa.filmoteca.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vsa.filmoteca.FilmotecaApplication;
import com.vsa.filmoteca.internal.di.component.ApplicationComponent;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector(getApplicationComponent());
    }

    private ApplicationComponent getApplicationComponent() {
        return ((FilmotecaApplication) getApplication()).getApplicationComponent();
    }

    protected abstract void initializeInjector(ApplicationComponent applicationComponent);

}
