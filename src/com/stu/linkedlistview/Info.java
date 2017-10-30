package com.stu.linkedlistview;

import com.stu.ordersystem.R;
import com.stu.ordersystem.TwoFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Info extends Activity{
	private Button enter;
	private Button message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		gotoInfo();
	}
	
	public void gotoInfo(){
		setContentView(R.layout.orderinfo);
		enter = (Button)findViewById(R.id.shouhuo);
		enter.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				gotoRank();
			}
		});
	}
	
	public void gotoRank(){
		setContentView(R.layout.rank);
		message = (Button)findViewById(R.id.mesgButton);
		message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Info.this,TwoFragment.class);
				startActivity(intent);
			}
		});
	}

}
