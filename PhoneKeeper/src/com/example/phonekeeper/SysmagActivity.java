package com.example.phonekeeper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SysmagActivity extends Activity {
    private String availmemory,totalmemory,hww,imsg,cpu1,cpu2;
    private String[] cpuIF;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sysmag);
		 availmemory=getAvailMemory();
	        totalmemory=getTotalMemory();
	        hww=getHeightAndWidth();
	        cpuIF=getCpuInfo();
	        imsg=getInfo();
	        cpuIF=getCpuInfo();
	        cpu1=cpuIF[0];
	        cpu2=cpuIF[1];

	        TextView am= (TextView) findViewById(R.id.msg0);
	        am.setText("����ʣ���ڴ棺"+availmemory);
	        TextView tm= (TextView) findViewById(R.id.msg1);
	        tm.setText("���ڴ棺"+totalmemory);
	        TextView hw= (TextView) findViewById(R.id.msg2);
	        hw.setText("�߶����ȣ�"+hww);
	        TextView msg= (TextView) findViewById(R.id.msg3);
	        msg.setText(imsg);
	        TextView cpuhz= (TextView) findViewById(R.id.msg4);
	        cpuhz.setText("cpu�ͺţ�"+cpu1);
	        TextView cpuhu= (TextView) findViewById(R.id.msg5);
	        cpuhu.setText("cpuƵ�ʣ�"+cpu2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sysmag, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	  private String getAvailMemory() {// ��ȡandroid��ǰ�����ڴ��С

	        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
	        am.getMemoryInfo(mi);
	        //mi.availMem; ��ǰϵͳ�Ŀ����ڴ�

	        return Formatter.formatFileSize(getBaseContext(), mi.availMem);// ����ȡ���ڴ��С���
	    }

	    /**
	     * ���ϵͳ���ڴ�
	     */
	    private String getTotalMemory() {
	        String str1 = "/proc/meminfo";// ϵͳ�ڴ���Ϣ�ļ�
	        String str2;
	        String[] arrayOfString;
	        long initial_memory = 0;

	        try {
	            FileReader localFileReader = new FileReader(str1);
	            BufferedReader localBufferedReader = new BufferedReader(
	                    localFileReader, 8192);
	            str2 = localBufferedReader.readLine();// ��ȡmeminfo��һ�У�ϵͳ���ڴ��С

	            arrayOfString = str2.split("\\s+");
	            for (String num : arrayOfString) {
	                Log.i(str2, num + "\t");
	            }

	            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// ���ϵͳ���ڴ棬��λ��KB������1024ת��ΪByte
	            localBufferedReader.close();

	        } catch (IOException e) {
	        }
	        return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byteת��ΪKB����MB���ڴ��С���
	    }

	    /**
	     * ����ֻ���Ļ���
	     * @return
	     */
	    public String getHeightAndWidth(){
	        int width=getWindowManager().getDefaultDisplay().getWidth();
	        int heigth=getWindowManager().getDefaultDisplay().getHeight();
	        String str=width+"*"+heigth+"";
	        return str;
	    }
	    /**
	     * ��ȡIMEI�ţ�IESI�ţ��ֻ��ͺ�
	     */
	    private String getInfo() {
	        TelephonyManager mTm = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
	        String imei = mTm.getDeviceId();
	        String imsi = mTm.getSubscriberId();
	        String mtype = android.os.Build.MODEL; // �ֻ��ͺ�
	        String mtyb= android.os.Build.BRAND;//�ֻ�Ʒ��
	        String numer = mTm.getLine1Number(); // �ֻ����룬�еĿɵã��еĲ��ɵ�
	        String str= "�ֻ�IMEI�ţ�"+imei +"\r\n"+"�ֻ�IESI�ţ�"+imsi+"\r\n"+"�ֻ��ͺţ�"+mtype+"\r\n"+"�ֻ�Ʒ�ƣ�"+mtyb+"\r\n"+"�ֻ����룺"+numer;
	        return str;

	    }

	    /**
	     * �ֻ�CPU��Ϣ
	     */
	    private String[] getCpuInfo() {
	        String str1 = "/proc/cpuinfo";
	        String str2 = "";
	        String[] cpuInfo = {"", ""};  //1-cpu�ͺ�  //2-cpuƵ��
	        String[] arrayOfString;
	        try {
	            FileReader fr = new FileReader(str1);
	            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
	            str2 = localBufferedReader.readLine();
	            arrayOfString = str2.split("\\s+");
	            for (int i = 2; i < arrayOfString.length; i++) {
	                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
	            }
	            str2 = localBufferedReader.readLine();
	            arrayOfString = str2.split("\\s+");
	            cpuInfo[1] += arrayOfString[2];
	            localBufferedReader.close();
	        } catch (IOException e) {
	        }
	        Log.i("text", "cpuinfo:" + cpuInfo[0] + " " + cpuInfo[1]);
	        return cpuInfo;
	    }
}
