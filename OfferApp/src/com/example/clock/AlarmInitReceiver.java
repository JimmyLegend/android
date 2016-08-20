package com.example.clock;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.util.Log;

public class AlarmInitReceiver extends BroadcastReceiver {

//	 接受开机启动完成的广播，
//  设置闹钟，当时区改变也设置
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.v("guo", "AlarmInitReceiver" + action);
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Alarms.saveSnoozeAlert(context, -1, -1);
        }

        Alarms.disableExpiredAlarms(context);
        Alarms.setNextAlert(context);
    }
}
