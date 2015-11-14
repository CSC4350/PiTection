package com.example.pitectors.pitection;
import android.app.Application;
import android.content.Context;

/**
 * Created by rnice01 on 11/9/2015.
 * This class is for static methods/classes to gain access to the application's context
 */
public class ContextClass extends Application{
    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
