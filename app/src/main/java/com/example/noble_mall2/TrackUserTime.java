package com.example.noble_mall2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.noble_mall2.Model.Jerseyitem;

public class TrackUserTime extends Service {
    private static final String TAG = "TrackUserTime";

    private IBinder binder = new LocalBinder();
    private  int seconds = 0;
    private Jerseyitem item;
    private boolean shouldFinish = false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: called");
        trackTime();
        return binder;
    }
    public class LocalBinder extends Binder {
        TrackUserTime getService ()
        {
           return TrackUserTime.this;
        }
    }
    private  void trackTime()
    {
        Log.d(TAG, "trackTime: started");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!shouldFinish)
                {
                    try {
                        Thread.sleep(1000);
                        seconds++;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: called");

        shouldFinish = true;
        Utils utils = new Utils(this);
        int minutes =  Integer.valueOf(seconds/60);
        utils.increaseUserPoint(item,minutes*2);
    }

    public void setItem(Jerseyitem item) {
        this.item = item;
    }
}
