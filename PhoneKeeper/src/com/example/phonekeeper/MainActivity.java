package com.example.phonekeeper;

import edu.sdut.phonekeeper.service.APPLockService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

	private GridView gridView;
	private int[] icon={
			R.drawable.yygl,
			R.drawable.srlj,
			R.drawable.jcgl,
			R.drawable.sjjs,
			R.drawable.sjbf,
			R.drawable.ysbh
	};

	//要显示的文字
	private String[] iconName = { "应用管理", "骚扰拦截", "进程管理", "手机加速", "手机备份",
			"隐私保护" };
	    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在家在布局文件之后获取布局文件中的控件
        gridView=(GridView) findViewById(R.id.gridView);
        
        GridAdapter adapter=new GridAdapter();
        gridView.setAdapter(adapter);
        //为GridView添加子项点击事件
        gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//相应指向点击事件
				//通过arg2参数确定用户点击的是哪一个子项
				switch (arg2) {
				case 0://应用管理
				{
					Intent intent=new Intent(MainActivity.this,AppManageActivity.class);
					startActivity(intent);
					break;
				}
                case 1://骚扰拦截
                {	
                	Intent intent=new Intent(MainActivity.this,FilterActivity.class);
					startActivity(intent);
					break;
                }
                case 2://进程管理
                {
                	Intent intent=new Intent(MainActivity.this,ProcessManageActivity.class);
					startActivity(intent);
	                break;
                }
                case 3://手机加速
                {
                	Intent intent=new Intent(MainActivity.this,SpeedActivity.class);
					startActivity(intent);
	                break;
                }
                case 4://手机备份
                {
                	Intent intent=new Intent(MainActivity.this,BackupActivity.class);
					startActivity(intent);
	                break;
                }
                case 5://隐私保护
                {
                	Intent intent=new Intent(MainActivity.this,SecretActivity.class);
					startActivity(intent);
	                break;
                }
				default:
					break;
				}
			}
        	
		});
        Intent intent=new Intent(MainActivity.this,APPLockService.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    class GridAdapter extends BaseAdapter{

    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		//这个方法的返回值，决定在GridView
    		return iconName.length;
    	}

    	@Override
    	public Object getItem(int arg0) {
    		// TODO Auto-generated method stub
    		return null;
    	}

    	@Override
    	public long getItemId(int position) {
    		// TODO Auto-generated method stub
    		return position;
    	}

    	@Override
    	public View getView(int arg0, View arg1, ViewGroup arg2) {
    		// TODO Auto-generated method stub
    		View view=View.inflate(MainActivity.this,R.layout.item_gridview,null);
    		//获取布局文件中的，图片和文本对象。
    		ImageView iv_Item=(ImageView) view.findViewById(R.id.imageView);
    		TextView tv_Item=(TextView) view.findViewById(R.id.textView);
    		
    		iv_Item.setImageResource(icon[arg0]);
    		tv_Item.setText(iconName[arg0]);
    		
    		return view;
    	}}

}
