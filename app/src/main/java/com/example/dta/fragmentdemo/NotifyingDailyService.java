package com.example.dta.fragmentdemo;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by DTA on 12/22/2016.
 */

public class NotifyingDailyService extends Service {

    LinearLayout oView;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        oView = new LinearLayout(this);
//        oView.setBackgroundColor(0x88ff0000); // The translucent red color
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//                0 | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);
//        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//        wm.addView(oView, params);
//    }
    @Override
    public int onStartCommand(Intent pIntent, int flags, int startId) {
        // TODO Auto-generated method stub
        Log.w("anhdt", "starting service...");
        Toast.makeText(this, "NotifyingDailyService", Toast.LENGTH_LONG).show();
        return super.onStartCommand(pIntent, flags, startId);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if(oView!=null){
//            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//            wm.removeView(oView);
//        }
//    }
}
