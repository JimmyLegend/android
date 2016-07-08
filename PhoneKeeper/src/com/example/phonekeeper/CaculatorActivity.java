package com.example.phonekeeper;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CaculatorActivity extends Activity implements OnClickListener{
	private Button button_1;
	private Button button_2;
	private Button button_3;
	private Button button_4;
	private Button button_5;
	private Button button_6;
	private Button button_7;
	private Button button_8;
	private Button button_9;
	private Button button_0;
	private Button button_clear;
	private Button button_del;
	private Button button_point;
	private Button button_plus;
	private Button button_minus;
	private Button button_multiply;
	private Button button_divide;
	private Button button_equals;
	private EditText et_input;
	private boolean clear_flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_caculator);
		button_1=(Button) findViewById(R.id.button_1);
        button_2=(Button) findViewById(R.id.button_2);
        button_3=(Button) findViewById(R.id.button_3);
        button_4=(Button) findViewById(R.id.button_4);
        button_5=(Button) findViewById(R.id.button_5);
        button_6=(Button) findViewById(R.id.button_6);
        button_7=(Button) findViewById(R.id.button_7);
        button_8=(Button) findViewById(R.id.button_8);
        button_9=(Button) findViewById(R.id.button_9);
        button_0=(Button) findViewById(R.id.button_0);
        button_clear=(Button) findViewById(R.id.button_clear);
        button_del=(Button) findViewById(R.id.button_del);
        button_point=(Button) findViewById(R.id.button_point);
        button_divide=(Button) findViewById(R.id.button_divide);
        button_multiply=(Button) findViewById(R.id.button_multiply);
        button_plus=(Button) findViewById(R.id.button_plus);
        button_minus=(Button) findViewById(R.id.button_minus);
        button_equals=(Button) findViewById(R.id.button_equals);
        et_input=(EditText) findViewById(R.id.et_input);
        
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        button_9.setOnClickListener(this);
        button_0.setOnClickListener(this);
        button_point.setOnClickListener(this);
        button_divide.setOnClickListener(this);
        button_del.setOnClickListener(this);
        button_clear.setOnClickListener(this);
        button_plus.setOnClickListener(this);
        button_multiply.setOnClickListener(this);
        button_minus.setOnClickListener(this);
        button_equals.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.caculator, menu);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	String str=et_input.getText().toString();
	switch (v.getId()) {
	case R.id.button_1:
	case R.id.button_2:
	case R.id.button_3:
	case R.id.button_4:
	case R.id.button_5:
	case R.id.button_6:
	case R.id.button_7:
	case R.id.button_8:
	case R.id.button_9:
	case R.id.button_0:
		if(clear_flag){
			if(str.contains(" ")){
				et_input.setText(str+((Button)v).getText());
			}
			else{
			str="";
			et_input.setText("");
			}
			clear_flag=false;
		}
		et_input.setText(str+((Button)v).getText());
		break;
	case R.id.button_point:
		if(clear_flag==false){
		if(!str.equals("")&&str!=null){
			if(!str.contains(" ")&&!str.contains(".")){
				et_input.setText(str+((Button)v).getText());
			}
			else if(str.contains(" ")&&!(str.substring(str.lastIndexOf(" ")+1,str.length())).equals("")&&(str.substring(str.lastIndexOf(" ")+1,str.length()))!=null&&!(str.substring(str.lastIndexOf(" ")+1,str.length())).contains(".")){
				
				et_input.setText(str+((Button)v).getText());
			}
		}
		}
		break;
	case R.id.button_multiply:
	case R.id.button_divide:
	case R.id.button_plus:
	case R.id.button_minus:
		if(!str.contains(" ")){
		et_input.setText(str+" "+((Button)v).getText()+" ");
		}
		break;
	case R.id.button_clear:
		clear_flag=false;
		str="";
		et_input.setText("");
		break;
	case R.id.button_del:
		if(clear_flag){
			clear_flag=false;
			str="";
			et_input.setText("");
		}else if(!str.equals("")&&str!=null){
			if(str.substring(str.length()-1,str.length()).equals(" ")){
				et_input.setText(str.substring(0,str.length()-3));
			}else{
			et_input.setText(str.substring(0,str.length()-1));
			}
		}
		break;
	case R.id.button_equals:
		getResult();
		break;
	default:
		break;
	}
}
private void getResult(){
	String exp=et_input.getText().toString();
	if(exp.equals("")||exp==null){
		return;
	}
	if(!exp.contains(" ")){
		return;
	}
	if(clear_flag){
		clear_flag=false;
		return;
	}
	clear_flag=true;
	double result=0;
	String s1=exp.substring(0,exp.indexOf(" "));
	String op=exp.substring(exp.indexOf(" ")+1,exp.indexOf(" ")+2);
	String s2=exp.substring(exp.indexOf(" ")+3);
	if(!s1.equals("")&&!s2.equals("")){
		double d1=Double.parseDouble(s1);
		double d2=Double.parseDouble(s2);
		if(op.equals("£«")){
			result=d1+d2;
		}
		else if(op.equals("£­")){
			result=d1-d2;
		}
		else if(op.equals("¡Á")){
			result=d1*d2;
		}
		else if(op.equals("¡Â")){
			if(d2==0){
				result=0;
			}else{
			result=d1/d2;
			}
		}
		if(!s1.contains(".")&&!s2.contains(".")&&!op.equals("¡Â")){
			int r=(int)result;
			et_input.setText(r+"");
		}
		else if(!s1.contains(".")&&!s2.contains(".")&&op.equals("¡Â")){
			int i1=(int)d1;
			int i2=(int)d2;
			if(i2==0){
				et_input.setText("0");	
			}
			else if(i2!=0&&i1%i2==0){
				et_input.setText(i1/i2+"");
			}
			else{
				et_input.setText(result+"");
			}
		}
		else{
			et_input.setText(result+"");
		}
	}
	else if(!s1.equals("")&&s2.equals("")){
		et_input.setText(exp);
	}
	else if(s1.equals("")&&!s2.equals("")){
		double d2=Double.parseDouble(s2);
		if(op.equals("£«")){
			result=0+d2;
		}
		else if(op.equals("£­")){
			result=0-d2;
		}
		else if(op.equals("¡Á")){
			result=0*d2;
		}
		else if(op.equals("¡Â")){
				result=0;
		}
		if(!s2.contains(".")){
			int r=(int)result;
			et_input.setText(r+"");
		}
		else{
			et_input.setText(result+"");
		}
	}
	else{
		et_input.setText("");
	}
}
}
