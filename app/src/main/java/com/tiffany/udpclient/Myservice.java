package com.tiffany.udpclient;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by moses on 16/07/2017.
 */

public class Myservice extends Service {

    public static final String TAG = "MyService";

    private UDPClient udphelper;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        WifiManager manager = (WifiManager) this
                .getSystemService(Context.WIFI_SERVICE);
//        udphelper = new UDPClient(manager);

        //传递WifiManager对象，以便在UDPHelper类里面使用MulticastLock
//        udphelper.addObserver(Myservice.this);
//        tReceived = new Thread(udphelper);
//        tReceived.start();
        super.onCreate();

        Log.e("service","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("service","onDestroy");
    }
}
