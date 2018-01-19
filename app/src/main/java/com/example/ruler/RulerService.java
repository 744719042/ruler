package com.example.ruler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class RulerService extends Service {
    private boolean first = true;
    RulerWindow rulerWindow;
    public RulerService() {
        rulerWindow = new RulerWindow();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (first) {
            rulerWindow.show();
            first = false;
        } else {
            rulerWindow.rotate();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rulerWindow.destroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
