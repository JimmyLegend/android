package edu.sdut.phonekeeper.config;

import android.content.Context;
import android.content.SharedPreferences;

public class APPLockConfig {
private SharedPreferences sp;
private SharedPreferences.Editor editor;
private final String CONFIG_FILE="PhoneKeeper.preferences";
public APPLockConfig(Context context){
	sp=context.getSharedPreferences(CONFIG_FILE,0);
	editor=sp.edit();
}
//±£¥Ê√‹¬Î
public void putPassWord(String value){
	editor=sp.edit();
	editor.putString("PassWord", value);
	editor.commit();
}
public String getPassWord(){
	return sp.getString("PassWord","111111");
}
}
