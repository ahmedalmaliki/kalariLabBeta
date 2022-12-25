package com.example.kalarilab.Activities;

import android.app.Application;
import android.util.Log;

public class App extends Application {
     final static String TAG = "BaseActivityDebug";
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,  getClass().getSimpleName() );
    }

//    private Activity mCurrentActivity = null;
//    public Activity getCurrentActivity(){
//        return mCurrentActivity;
//    }
//    public void setCurrentActivity(Activity mCurrentActivity){
//        this.mCurrentActivity = mCurrentActivity;
//    }

}
