package com.example.dta.fragmentdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by DTA on 12/22/2016.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.w("anhdt", "arg1 = " + intent.getAction());
        Intent i = new Intent(context, UnlockedScreenActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

       // context.startService(new Intent(context, NotifyingDailyService.class));


//        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)
//                || intent.getAction().equals(Intent.ACTION_SCREEN_ON)
//                || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
//            start_lockscreen(context);
//        }

    }

    // Display lock screen
    private void start_lockscreen(Context context) {
        Intent mIntent = new Intent(context, LockScreenActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
    }

}
