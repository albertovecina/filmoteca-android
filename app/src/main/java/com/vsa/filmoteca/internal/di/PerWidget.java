package com.vsa.filmoteca.internal.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerWidget {
}
