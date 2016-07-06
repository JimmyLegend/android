package com.example.phonekeeper;

import java.util.List;

import com.example.phonekeeper.AppManageActivity.ViewHolder;

import edu.sdut.phonekeeper.db.dao.BlackNumberDao;
import edu.sdut.phonekeeper.db.dao.SMSFilterDao;
import edu.sdut.phonekeeper.domain.SMSInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FilterActivity extends Activity {
	private Button btnBlackList;
	private ListView lvSMSFilter;
	private List<SMSInfo> arrSMS;
	private SMSFilterDao smsFilterDao;
	private SMSAdapter smsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		btnBlackList=(Button) findViewById(R.id.btn_blacklist);
		btnBlackList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(FilterActivity.this,BlackListActivity.class);
				startActivity(intent);
			}
		});
		lvSMSFilter=(ListView) findViewById(R.id.lv_smsFilter);
		smsFilterDao=new SMSFilterDao(FilterActivity.this);
		
		/*
		BlackNumberDao blackDao=new BlackNumberDao(FilterActivity.this);
		blackDao.add("10001");
		smsFilterDao.add(new SMSInfo(-1,"111111", "这是一条被拦截的数据",11111111111L));
		*/
		
		arrSMS=smsFilterDao.findAll();
		smsAdapter=new SMSAdapter();
		lvSMSFilter.setAdapter(smsAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
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
	class SMSAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrSMS.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arrSMS.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			SMSInfo sms=(SMSInfo) getItem(arg0);
			ViewHolder viewHolder=null;
			if(arg1==null){
				arg1=View.inflate(FilterActivity.this,R.layout.item_smsfilter,null);
				viewHolder=new ViewHolder();
				viewHolder.tvNum=(TextView) arg1.findViewById(R.id.tv_num);
				viewHolder.tvTime=(TextView) arg1.findViewById(R.id.tv_time);
				viewHolder.tvContext=(TextView) arg1.findViewById(R.id.tv_context);
				arg1.setTag(viewHolder);
			}
			else {
				viewHolder=(ViewHolder) arg1.getTag();
			}
			viewHolder.tvContext.setText(sms.getContext());
			viewHolder.tvNum.setText(sms.getNum());
			viewHolder.tvTime.setText(sms.getTimeString());
			return arg1;
		}
		
	}
	class ViewHolder{
		TextView tvNum;
		TextView tvTime;
		TextView tvContext;
	}
}
