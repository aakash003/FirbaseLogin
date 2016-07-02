package com.ash.application;

import com.firebase.client.Firebase;

public class CustomApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}