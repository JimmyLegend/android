package com.example.clock;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
public class AlarmAlert extends AlarmAlertFullScreen {
    private int mKeyguardRetryCount;
    private final int MAX_KEYGUARD_CHECKS = 5;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleScreenOff((KeyguardManager) msg.obj);
        }
    };
//广播接收器
    private final BroadcastReceiver mScreenOffReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    KeyguardManager km =
                            (KeyguardManager) context.getSystemService(
                            Context.KEYGUARD_SERVICE);
                    handleScreenOff(km);
                }
            };

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //注册接收器
        registerReceiver(mScreenOffReceiver,
                new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }
    //销毁当前闹钟
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScreenOffReceiver);
        mHandler.removeMessages(0);
    }
    //失去焦点
    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean checkRetryCount() {
        if (mKeyguardRetryCount++ >= MAX_KEYGUARD_CHECKS) {
            Log.v("guo", "Tried to read keyguard status too many times, bailing...");
            return false;
        }
        return true;
    }

    private void handleScreenOff(final KeyguardManager km) {
        if (!km.inKeyguardRestrictedInputMode() && checkRetryCount()) {
            if (checkRetryCount()) {
                mHandler.sendMessageDelayed(mHandler.obtainMessage(0, km), 500);
            }
        } else {
            Intent i = new Intent(this, AlarmAlertFullScreen.class);
            i.putExtra(Alarms.ALARM_INTENT_EXTRA, mAlarm);
            i.putExtra(SCREEN_OFF, true);
            startActivity(i);
            finish();
        }
    }
}
