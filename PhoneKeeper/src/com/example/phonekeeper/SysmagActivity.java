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
	        am.setText("可用剩余内存："+availmemory);
	        TextView tm= (TextView) findViewById(R.id.msg1);
	        tm.setText("总内存："+totalmemory);
	        TextView hw= (TextView) findViewById(R.id.msg2);
	        hw.setText("高度与宽度："+hww);
	        TextView msg= (TextView) findViewById(R.id.msg3);
	        msg.setText(imsg);
	        TextView cpuhz= (TextView) findViewById(R.id.msg4);
	        cpuhz.setText("cpu型号："+cpu1);
	        TextView cpuhu= (TextView) findViewById(R.id.msg5);
	        cpuhu.setText("cpu频率："+cpu2);
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
	  private String getAvailMemory() {// 获取android当前可用内存大小

	        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
	        am.getMemoryInfo(mi);
	        //mi.availMem; 当前系统的可用内存

	        return Formatter.formatFileSize(getBaseContext(), mi.availMem);// 将获取的内存大小规格化
	    }

	    /**
	     * 获得系统总内存
	     */
	    private String getTotalMemory() {
	        String str1 = "/proc/meminfo";// 系统内存信息文件
	        String str2;
	        String[] arrayOfString;
	        long initial_memory = 0;

	        try {
	            FileReader localFileReader = new FileReader(str1);
	            BufferedReader localBufferedReader = new BufferedReader(
	                    localFileReader, 8192);
	            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

	            arrayOfString = str2.split("\\s+");
	            for (String num : arrayOfString) {
	                Log.i(str2, num + "\t");
	            }

	            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
	            localBufferedReader.close();

	        } catch (IOException e) {
	        }
	        return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
	    }

	    /**
	     * 获得手机屏幕宽高
	     * @return
	     */
	    public String getHeightAndWidth(){
	        int width=getWindowManager().getDefaultDisplay().getWidth();
	        int heigth=getWindowManager().getDefaultDisplay().getHeight();
	        String str=width+"*"+heigth+"";
	        return str;
	    }
	    /**
	     * 获取IMEI号，IESI号，手机型号
	     */
	    private String getInfo() {
	        TelephonyManager mTm = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
	        String imei = mTm.getDeviceId();
	        String imsi = mTm.getSubscriberId();
	        String mtype = android.os.Build.MODEL; // 手机型号
	        String mtyb= android.os.Build.BRAND;//手机品牌
	        String numer = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得
	        String str= "手机IMEI号："+imei +"\r\n"+"手机IESI号："+imsi+"\r\n"+"手机型号："+mtype+"\r\n"+"手机品牌："+mtyb+"\r\n"+"手机号码："+numer;
	        return str;

	    }

	    /**
	     * 手机CPU信息
	     */
	    private String[] getCpuInfo() {
	        String str1 = "/proc/cpuinfo";
	        String str2 = "";
	        String[] cpuInfo = {"", ""};  //1-cpu型号  //2-cpu频率
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
