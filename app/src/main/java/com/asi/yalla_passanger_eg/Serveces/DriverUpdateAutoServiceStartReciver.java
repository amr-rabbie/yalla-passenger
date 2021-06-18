package com.asi.yalla_passanger_eg.Serveces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

public class DriverUpdateAutoServiceStartReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

    }
//    private DriverUpdatesService driverUpdatesService;
//    private Intent mServiceIntent;
//
//    @Override
//    public void onReceive(final Context context, Intent intent) {
//        Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
//        mSensorService = new SensorService(context);
//        mServiceIntent = new Intent(context, driverUpdatesService.getClass());
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    context.startForegroundService(mServiceIntent);
//                    Log.e("ASI-------------------", "======================");
//                    Log.e("ServiceType-->", "New Service Form Broad ");
//                    Log.e("ASI-------------------", "======================");
//                } else  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
//                    context.startService(mServiceIntent);
//                    Log.e("ASI-------------------", "======================");
//                    Log.e("ServiceType-->", "Old Service Form Broad");
//                    Log.e("ASI-------------------", "======================");
//                }
//            }
//        }, 5000);
//
//    }
}