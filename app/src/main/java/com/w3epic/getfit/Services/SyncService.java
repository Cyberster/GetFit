package com.w3epic.getfit.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SyncService extends Service {
    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    String className = "";

    public SyncService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            //className = (String) intent.getExtras().get("className");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("SyncService", className);

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                //Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();
                Log.e("SyncService", "Service is still running");
                handler.postDelayed(runnable, 10000);
            }
        };

        handler.postDelayed(runnable, 15000);

        // code for sync sqlite with firebase
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
