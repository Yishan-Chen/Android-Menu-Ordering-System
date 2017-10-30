package com.stu.ordersystem;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Activity{
	private TextView username;
	private EditText pwd,realname,telnum;
	private RadioButton female;
	
	public static String realName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		username = (TextView)findViewById(R.id.userinfoTextView01);
		pwd = (EditText)findViewById(R.id.userinfoEditText02);
		realname = (EditText)findViewById(R.id.userinfoEditText03);
		telnum = (EditText)findViewById(R.id.userinfoEditText04);
		female = (RadioButton)findViewById(R.id.userinfoRadioButton02);
		gotoSetting();
	}
	

	protected void gotoSetting() {
		String uname = RegAndLog.username;
		final String URL = "http://" + Constant.IP_ADDRESS + ":9080/orderSystem/userinfo.jsp";
		username.setText(uname);
		final Map<String, String>params = new HashMap<String, String>();
		params.put("param1", uname.trim());
		new Thread(){
				public void run() {
					final String mgStr = HttpUploadUtil.postWithoutFile(URL, params);
					Setting.this.runOnUiThread(new Runnable() {
						public void run() {
							String[] info = mgStr.split("\\|");
							System.out.println(info[0]);
							pwd.setText(info[0]);
							realname.setText(info[1]);
							telnum.setText(info[3]);
							if(info[2].equals("女")){
								female.setChecked(true);
							}
							realName = info[1];
						}
					});
				}
		}.start();
		
		Button edit = (Button)findViewById(R.id.userinfoButton01);
		edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final String url = "http://" + Constant.IP_ADDRESS + ":9080/orderSystem/modifyInfo.jsp";
				final Map<String, String> params = new HashMap<String, String>();
				params.put("param1", username.getText().toString());
				params.put("param2", pwd.getText().toString());
				params.put("param3",realname.getText().toString());
				params.put("param5", telnum.getText().toString());
				if(female.isChecked()){
					params.put("param4", "女");
				}
				else{
					params.put("param4", "男");
				}
				new Thread(){
					public void run(){
						final String mgString = HttpUploadUtil.postWithoutFile(url, params);
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									if (mgString.equals("更新成功，请重新登录！")) {
										Toast.makeText(Setting.this, "更新成功，重新登录后可见！",Toast.LENGTH_LONG).show();
									}
									else {
										Toast.makeText(Setting.this, "更新失败，请重试！", Toast.LENGTH_LONG).show();
									}
								}
							});
					}
				}.start();
				
			}
		});
	}
	
}
