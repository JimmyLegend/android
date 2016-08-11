package com.cn.daming.deskclock;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.util.Log;

public class AlarmInitReceiver extends BroadcastReceiver {

//	 ���ܿ���������ɵĹ㲥��
//  �������ӣ���ʱ���ı�Ҳ����
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.v("wangxianming", "AlarmInitReceiver" + action);
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Alarms.saveSnoozeAlert(context, -1, -1);
        }

        Alarms.disableExpiredAlarms(context);
        Alarms.setNextAlert(context);
    }
}
