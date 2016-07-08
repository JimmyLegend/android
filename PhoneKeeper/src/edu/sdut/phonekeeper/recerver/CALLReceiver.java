package edu.sdut.phonekeeper.recerver;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;

import com.android.internal.telephony.ITelephony;

import edu.sdut.phonekeeper.db.dao.BlackNumberDao;
import edu.sdut.phonekeeper.db.dao.SMSFilterDao;
import edu.sdut.phonekeeper.domain.SMSInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.drm.DrmStore.Action;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CALLReceiver extends BroadcastReceiver {

	TelephonyManager telMgr;

	@Override
	public void onReceive(Context context, Intent intent) {

		// 获取广播的动作类型
		String action = intent.getAction();
		if (action.equals("android.intent.action.PHONE_STATE")) {
				
			
				telMgr = (TelephonyManager) context
						.getSystemService(Service.TELEPHONY_SERVICE);
				String number = intent
						.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
				long time=System.currentTimeMillis();

				Log.i("广播接收", "电话广播");
				BlackNumberDao blackDao = new BlackNumberDao(context);
				if (blackDao.exist(number)) {
					abortBroadcast();
					if(telMgr.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
						SMSFilterDao smsDao = new SMSFilterDao(context);
						smsDao.add(new SMSInfo(0, number, "电话拦截", time));
					}
					endCall();
				
				
				
			}
			
		}
	}

	private void endCall() {
		// TODO Auto-generated method stub
		Class<TelephonyManager> c = TelephonyManager.class;
		try {
			Method getITelephonyMethod = c.getDeclaredMethod("getITelephony",
					(Class[]) null);
			getITelephonyMethod.setAccessible(true);
			ITelephony iTelephony = null;
			Log.e("电话拦截", "End call.");
			iTelephony = (ITelephony) getITelephonyMethod.invoke(telMgr,
					(Object[]) null);
			iTelephony.endCall();
		} catch (Exception e) {
			Log.e("广播", "Fail to answer ring call.", e);
		}

	}

}
