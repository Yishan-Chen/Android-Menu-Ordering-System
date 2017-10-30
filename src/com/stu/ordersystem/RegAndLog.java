package com.stu.ordersystem;


import static com.stu.ordersystem.Constant.IP_ADDRESS;

import java.util.HashMap;
import java.util.Map;



import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.Toast;

enum rWhichView {MAIN_VIEW,//主界面
    LOGIN_VIEW,//登录界面
 REGISTER_VIEW,//注册斤面
 WELCOME_VIEW,
      }

public class RegAndLog extends Activity {
	Handler hd;
	rWhichView curr;
	public static String username;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		gotoLoginView();
		hd=new Handler()
		{
			public void handleMessage(Message msg)
        	{
        	   //调用父类处理  
        	   super.handleMessage(msg);
        	   //获取消息中的数据
        	   Bundle b;
			                    
			   b=msg.getData();
 			   //获取内容字符串
 			   String msgStr=b.getString("msg");
 			   System.out.println(msgStr);
        	   //根据消息what编号的不同，执行不同的业务逻辑
        		switch(msg.what)
        		{
        		case Constant.GOTOLOGIN:
        			gotoLoginView();
        			break;
        		case Constant.LOGINVIEW:
        			  if(msgStr.equals("登录成功"))
        			  {		
        				  Toast.makeText(RegAndLog.this, "登录成功！", Toast.LENGTH_SHORT).show();
        				  Intent intent = new Intent(RegAndLog.this,MainActivity.class);
        				  RegAndLog.this.startActivity(intent);
        			  }
        			  else
        			  {
        				  Toast.makeText(RegAndLog.this, "登录失败", Toast.LENGTH_SHORT).show();
        			  }
        			  break;
        		 case Constant.REGISTER:
      			   //获取消息中的数据
        			 b=msg.getData();
      			   //获取内容字符串
        			 msgStr=b.getString("msg");
        			 Toast.makeText(RegAndLog.this, msgStr, Toast.LENGTH_SHORT).show();
        			
					   if(msgStr.equals("注册成功,请输入用户名和密码以登录！"))//如果从jsp得到的字符串为登录成功，则转到首页
					   {
						   gotoLoginView();
					   }
					   break;
        		}
        	}
			
		};
	}
		
	public void gotoLoginView()
	{
		setContentView(R.layout.enroll);
		PublicWay.activityList.add(this);
		Button login = (Button)this.findViewById(R.id.loginLog);
//		Button clear = (Button)this.findViewById(R.id.loginClear);
		Button enroll = (Button)this.findViewById(R.id.loginEnroll);
//		Button quit = (Button)this.findViewById(R.id.loginQuit);
			
		final EditText uname = (EditText)this.findViewById(R.id.userNameText);
		final EditText pwd = (EditText)this.findViewById(R.id.pwdText);
		
		login.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				final String url = "http://"+IP_ADDRESS+":9080/orderSystem/receive.jsp";
				final Map<String,String> params = new HashMap<String,String>();
				params.put("params1", uname.getText().toString());
				params.put("params2", pwd.getText().toString());
				username = uname.getText().toString();
				SharedPreferences sp = RegAndLog.this.getSharedPreferences("actm", MODE_PRIVATE);
				String uname = sp.getString(username, null);
				SharedPreferences.Editor editor = sp.edit();
				editor.putString("uname",username);
				editor.commit();	
					
				new Thread()
				{
					public void run()
					{	
						String msgStr=HttpUploadUtil.postWithoutFile(url, params);//将url和参数传给jsp
						Bundle b=new Bundle();
						//将内容字符串放进数据Bundle中
						b.putString("msg", msgStr);
						//创建消息对象
						Message msg=new Message();
						//设置数据Bundle到消息中
						msg.setData(b);
						//设置消息的what值
						msg.what=Constant.LOGINVIEW;
	        			//发送消息
						hd.sendMessage(msg);
						}
					}.start();	
				}
			}
		);
			
//		clear.setOnClickListener(new OnClickListener(){
//				@Override
//				public void onClick(View v) {
//					uname.setText("");
//					pwd.setText("");
//				}
//			}
//		);
		
		enroll.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					gotoRegister();	
				}
			}
		);
//		quit.setOnClickListener(new OnClickListener(){
//					@Override
//				public void onClick(View v) {
//					for(int i=0;i<PublicWay.activityList.size();i++){
//						if (null != PublicWay.activityList.get(i)) {
//							PublicWay.activityList.get(i).finish();
//							}
//						}
//					}
//				}
//		);
		curr=rWhichView.LOGIN_VIEW;
	}
		
	public void gotoRegister()
	{
		setContentView(R.layout.register);
		final Button enter = (Button)this.findViewById(R.id.enter);
		final Button reset = (Button)this.findViewById(R.id.reset);
		final Button back = (Button)this.findViewById(R.id.back);
		
		//是否该定义为private
		final EditText bUid = (EditText)this.findViewById(R.id.eUserNameText);
		final EditText bRname = (EditText)this.findViewById(R.id.realNameText);
		final EditText bPwd = (EditText)this.findViewById(R.id.pwdText2);
		final EditText bPwd2 = (EditText)this.findViewById(R.id.pwdText3);
		final EditText bPhone = (EditText)this.findViewById(R.id.telText);
		final RadioButton mgender=(RadioButton)findViewById(R.id.male);
		
		//录入数据功能未实现
		enter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				final String url = "http://"+ IP_ADDRESS +":9080/orderSystem/regForAndroid.jsp";
				if(bUid.getText().toString().matches(""))
				{
					Toast.makeText(RegAndLog.this,"请输入用户名", Toast.LENGTH_SHORT).show();
				}
				else if(!(bUid.getText().toString().matches("^[a-zA-Z][a-zA-Z0-9_]{5,9}$")))
				{
					Toast.makeText(RegAndLog.this,"用户名长度为6到10字节，允许数字下划线", Toast.LENGTH_SHORT).show();
				}
				else if(bPwd.getText().toString().matches(""))
				{
					Toast.makeText(RegAndLog.this,"请输入密码", Toast.LENGTH_SHORT).show();
				}
				
				else if(bPwd.getText().toString().length()<6)
				{
					Toast.makeText(RegAndLog.this,"密码长度不能小于6", Toast.LENGTH_SHORT).show();
				}
				else if(bPwd2.getText().toString().matches(""))
				{
					Toast.makeText(RegAndLog.this,"请再次输入密码", Toast.LENGTH_SHORT).show();
				}
				else if(!bPwd.getText().toString().trim().equals(bPwd2.getText().toString().trim()))
				{
					Toast.makeText(RegAndLog.this,"密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
				}
				else if(bPhone.getText().toString().matches(""))
				{
					Toast.makeText(RegAndLog.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
				}
				else if(!bPhone.getText().toString().matches("^1[3,5]{1}[0-9]{1}[0-9]{8}$"))
				{
					Toast.makeText(RegAndLog.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
				}
				
				else if(bRname.getText().toString().matches(""))
				{
					Toast.makeText(RegAndLog.this, "真实姓名不能为空", Toast.LENGTH_SHORT).show();
				}
				else
				{
					final Map<String,String> params = new HashMap<String,String>();
					params.put("yonghuming", bUid.getText().toString());				
					params.put("rpwd", bPwd.getText().toString());
					params.put("telnum", bPhone.getText().toString());
					params.put("realname", bRname.getText().toString());
					String gender = "女";
					if(mgender.isChecked()){
						gender = "男";
					}
					params.put("gender", gender);
					
					new Thread()
					{
						public void run()
						{
							String msgStr=HttpUploadUtil.postWithoutFile(url, params);//将url和参数传给jsp
							Bundle b=new Bundle();		        			
		        			b.putString("msg", msgStr);//将内容字符串放进数据Bundle中		        			
		        			Message msg=new Message();//创建消息对象		        			
		        			msg.setData(b);//设置数据Bundle到消息中		        			
		        			msg.what=Constant.REGISTER;//设置消息的what值		        			
		        			hd.sendMessage(msg);//发送消息
						}
					}.start();
				}
				}
			}
		);
		reset.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					bRname.setText("");
					bUid.setText("");
					bPwd.setText("");
					bPhone.setText("");
				}
			}
		);
			
		back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				gotoLoginView();
				}
			}
		);	
		curr=rWhichView.REGISTER_VIEW;
	}
}

