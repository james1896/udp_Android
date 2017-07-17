package com.tiffany.udpclient;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by moses on 16/07/2017.
 */

public class Myapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        WifiManager manager = (WifiManager) this
                .getSystemService(Context.WIFI_SERVICE);
        WifiManager.MulticastLock lock= manager.createMulticastLock("test wifi");
    }
}
