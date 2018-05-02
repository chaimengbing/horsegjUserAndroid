package com.project.mgjandroid.utils.inject;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by yuandi on 2016/11/14.
 */

public class InjectorFragment {

    private final Fragment mFragment;

    private InjectorFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public static InjectorFragment get(Fragment mFragment) {
        return new InjectorFragment(mFragment);
    }

    public void inject(View view) {
        for (Field field : mFragment.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation.annotationType().equals(InjectView.class)) {
                    try {
                        Class<?> fieldType = field.getType();
                        int idValue = InjectView.class.cast(annotation).value();
                        field.setAccessible(true);
                        Object injectedValue = fieldType.cast(view.findViewById(idValue));
                        if (injectedValue == null) {
                            throw new IllegalStateException("findViewById(" + idValue
                                    + ") gave null for " +
                                    field + ", can't inject");
                        }
                        field.set(mFragment, injectedValue);
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }
    }
}
