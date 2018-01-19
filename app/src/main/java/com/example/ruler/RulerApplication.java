package com.example.ruler;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/1/17.
 */

public class RulerApplication extends Application {
    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        CommonUtils.init(this);
        sApplication = this;
    }

    public static Context getContext() {
        return sApplication;
    }
}
