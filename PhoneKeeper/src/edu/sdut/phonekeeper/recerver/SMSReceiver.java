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
//���㲥�����յ�ʱ��onReceive����ִ��
		Log.i("�㲥����","���Ź㲥");
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
						//���浽���ݿ���
						SMSFilterDao smsDao=new SMSFilterDao(context);
						smsDao.add(new SMSInfo(0, number, content, time.getTime()));
						//�������أ���ֹ������Reciever�ڽ��յ�ϵͳ�Ķ��Ź㲥
						abortBroadcast();//android4.4��ʼ�����������ز����ġ��ܻ�ȡ����������ֹ��
					}
				}
				
			}
		}
	}

}
