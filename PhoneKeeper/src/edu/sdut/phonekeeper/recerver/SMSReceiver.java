package edu.sdut.phonekeeper.recerver;

import java.sql.Date;

import edu.sdut.phonekeeper.db.dao.BlackNumberDao;
import edu.sdut.phonekeeper.db.dao.SMSFilterDao;
import edu.sdut.phonekeeper.domain.SMSInfo;

import android.R.string;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony.Sms;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
//当广播被接收到时，onReceive将被执行
		Log.i("广播接受","短信广播");
		//
		String action=intent.getAction();
		if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
			Bundle bundle=intent.getExtras();
			if(bundle!=null){
				//
				Object[] pdus=(Object[]) bundle.get("pdus");
				//jiexiduanxin
				SmsMessage[] managers=new SmsMessage[pdus.length];
				for(int i=0;i<managers.length;i++){
					byte[] pdu=(byte[]) pdus[i];
					managers[i]=SmsMessage.createFromPdu(pdu);
				}
				for(SmsMessage msg:managers){
					String content=msg.getMessageBody();
					String number=msg.getOriginatingAddress();
					Date time=new Date(msg.getTimestampMillis());
					BlackNumberDao blackDao=new BlackNumberDao(context);
					if(blackDao.exist(number)){
						//保存到数据库中
						SMSFilterDao smsDao=new SMSFilterDao(context);
						smsDao.add(new SMSInfo(0, number, content, time.getTime()));
						//进行拦截，防止其他的Reciever在接收到系统的短信广播
						abortBroadcast();//android4.4开始，这样是拦截不掉的。能获取，但不能阻止。
					}
				}
				
			}
		}
	}

}
