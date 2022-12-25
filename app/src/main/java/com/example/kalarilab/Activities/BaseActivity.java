package com.example.kalarilab.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected App mMyApp;
    final static String TAG = "BaseActivityDebug";
    private int currentActivityOrientation   = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    private int parentActivityOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;

    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyApp = (App)this.getApplicationContext();
        Log.d(TAG,  getClass().getSimpleName() );
        this.cacheOrientations();

    }
    private void cacheOrientations()
    {
        if (this.currentActivityOrientation == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        {
            final Intent parentIntent = this.getParentActivityIntent();

            if (parentIntent != null)
            {
                final ComponentName parentComponentName = parentIntent.getComponent();

                if (parentComponentName != null)
                {
                    this.currentActivityOrientation = this.getConfiguredOrientation(this.getComponentName());
                    this.parentActivityOrientation = this.getConfiguredOrientation(parentComponentName);
                }
            }
        }
    }
    private int getConfiguredOrientation(@NonNull final ComponentName source)
    {
        try
        {
            final PackageManager packageManager = this.getPackageManager();
            final ActivityInfo   activityInfo   = packageManager.getActivityInfo(source, 0);
            return activityInfo.screenOrientation;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }
    @CallSuper
    @Override
    protected void onPause()
    {
        if (this.parentActivityOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        {
            this.setRequestedOrientation(this.parentActivityOrientation);
        }

        super.onPause();
    }
    @CallSuper
    @Override
    protected void onResume()
    {
        super.onResume();

        if (this.currentActivityOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        {
            this.setRequestedOrientation(this.currentActivityOrientation);
        }
    }

}
