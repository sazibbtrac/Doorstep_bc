package com.btracsolution.deliverysystem.Depedency.di.myAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by mahmudul.hasan on 12/31/2017.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface HomeContext {
}
