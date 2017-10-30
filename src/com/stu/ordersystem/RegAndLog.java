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

enum rWhichView {MAIN_VIEW,//������
    LOGIN_VIEW,//��¼����
 REGISTER_VIEW,//ע�����
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
        	   //���ø��ദ��  
        	   super.handleMessage(msg);
        	   //��ȡ��Ϣ�е�����
        	   Bundle b;
			                    
			   b=msg.getData();
 			   //��ȡ�����ַ���
 			   String msgStr=b.getString("msg");
 			   System.out.println(msgStr);
        	   //������Ϣwhat��ŵĲ�ͬ��ִ�в�ͬ��ҵ���߼�
        		switch(msg.what)
        		{
        		case Constant.GOTOLOGIN:
        			gotoLoginView();
        			break;
        		case Constant.LOGINVIEW:
        			  if(msgStr.equals("��¼�ɹ�"))
        			  {		
        				  Toast.makeText(RegAndLog.this, "��¼�ɹ���", Toast.LENGTH_SHORT).show();
        				  Intent intent = new Intent(RegAndLog.this,MainActivity.class);
        				  RegAndLog.this.startActivity(intent);
        			  }
        			  else
        			  {
        				  Toast.makeText(RegAndLog.this, "��¼ʧ��", Toast.LENGTH_SHORT).show();
        			  }
        			  break;
        		 case Constant.REGISTER:
      			   //��ȡ��Ϣ�е�����
        			 b=msg.getData();
      			   //��ȡ�����ַ���
        			 msgStr=b.getString("msg");
        			 Toast.makeText(RegAndLog.this, msgStr, Toast.LENGTH_SHORT).show();
        			
					   if(msgStr.equals("ע��ɹ�,�������û����������Ե�¼��"))//�����jsp�õ����ַ���Ϊ��¼�ɹ�����ת����ҳ
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
						String msgStr=HttpUploadUtil.postWithoutFile(url, params);//��url�Ͳ�������jsp
						Bundle b=new Bundle();
						//�������ַ����Ž�����Bundle��
						b.putString("msg", msgStr);
						//������Ϣ����
						Message msg=new Message();
						//��������Bundle����Ϣ��
						msg.setData(b);
						//������Ϣ��whatֵ
						msg.what=Constant.LOGINVIEW;
	        			//������Ϣ
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
		
		//�Ƿ�ö���Ϊprivate
		final EditText bUid = (EditText)this.findViewById(R.id.eUserNameText);
		final EditText bRname = (EditText)this.findViewById(R.id.realNameText);
		final EditText bPwd = (EditText)this.findViewById(R.id.pwdText2);
		final EditText bPwd2 = (EditText)this.findViewById(R.id.pwdText3);
		final EditText bPhone = (EditText)this.findViewById(R.id.telText);
		final RadioButton mgender=(RadioButton)findViewById(R.id.male);
		
		//¼�����ݹ���δʵ��
		enter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				final String url = "http://"+ IP_ADDRESS +":9080/orderSystem/regForAndroid.jsp";
				if(bUid.getText().toString().matches(""))
				{
					Toast.makeText(RegAndLog.this,"�������û���", Toast.LENGTH_SHORT).show();
				}
				else if(!(bUid.getText().toString().matches("^[a-zA-Z][a-zA-Z0-9_]{5,9}$")))
				{
					Toast.makeText(RegAndLog.this,"�û�������Ϊ6��10�ֽڣ����������»���", Toast.LENGTH_SHORT).show();
				}
				else if(bPwd.getText().toString().matches(""))
				{
					Toast.makeText(RegAndLog.this,"����������", Toast.LENGTH_SHORT).show();
				}
				
				else if(bPwd.getText().toString().length()<6)
				{
					Toast.makeText(RegAndLog.this,"���볤�Ȳ���С��6", Toast.LENGTH_SHORT).show();
				}
				else if(bPwd2.getText().toString().matches(""))
				{
					Toast.makeText(RegAndLog.this,"���ٴ���������", Toast.LENGTH_SHORT).show();
				}
				else if(!bPwd.getText().toString().trim().equals(bPwd2.getText().toString().trim()))
				{
					Toast.makeText(RegAndLog.this,"�������벻һ�£�����������", Toast.LENGTH_SHORT).show();
				}
				else if(bPhone.getText().toString().matches(""))
				{
					Toast.makeText(RegAndLog.this, "�������ֻ�����", Toast.LENGTH_SHORT).show();
				}
				else if(!bPhone.getText().toString().matches("^1[3,5]{1}[0-9]{1}[0-9]{8}$"))
				{
					Toast.makeText(RegAndLog.this, "�ֻ������ʽ����ȷ", Toast.LENGTH_SHORT).show();
				}
				
				else if(bRname.getText().toString().matches(""))
				{
					Toast.makeText(RegAndLog.this, "��ʵ��������Ϊ��", Toast.LENGTH_SHORT).show();
				}
				else
				{
					final Map<String,String> params = new HashMap<String,String>();
					params.put("yonghuming", bUid.getText().toString());				
					params.put("rpwd", bPwd.getText().toString());
					params.put("telnum", bPhone.getText().toString());
					params.put("realname", bRname.getText().toString());
					String gender = "Ů";
					if(mgender.isChecked()){
						gender = "��";
					}
					params.put("gender", gender);
					
					new Thread()
					{
						public void run()
						{
							String msgStr=HttpUploadUtil.postWithoutFile(url, params);//��url�Ͳ�������jsp
							Bundle b=new Bundle();		        			
		        			b.putString("msg", msgStr);//�������ַ����Ž�����Bundle��		        			
		        			Message msg=new Message();//������Ϣ����		        			
		        			msg.setData(b);//��������Bundle����Ϣ��		        			
		        			msg.what=Constant.REGISTER;//������Ϣ��whatֵ		        			
		        			hd.sendMessage(msg);//������Ϣ
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

