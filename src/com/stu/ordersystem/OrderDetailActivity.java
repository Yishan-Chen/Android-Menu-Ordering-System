package com.stu.ordersystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailActivity extends Activity {
	private LinearLayout orderDetailView;
	private int id;
	private OrderFragment orderFragment;

	public void setId(int id) {
		this.id = id;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderdetail);
		orderDetailView = (LinearLayout) findViewById(R.id.orderLayout);
		Intent intent = getIntent();
		id = intent.getIntExtra("arg", 0);
		orderFragment = new OrderFragment();
		gotoOrderDetail(id);
	}

	protected void gotoOrderDetail(final Integer oid1) {

		final String url = "http://" + Constant.IP_ADDRESS
				+ ":9080/orderSystem/orderDetail.jsp";
		// 准备参数
		final Map<String, String> params = new HashMap<String, String>();
		String oid = oid1.toString();
		params.put("param1", oid);
		final Executor threadPool = Executors.newCachedThreadPool();
		threadPool.execute(new Runnable() {
			public void run() {
				final String msg = HttpUploadUtil.postWithoutFile(url, params);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String[] detail = msg.split("\\|");
						
						((TextView) orderDetailView
								.findViewById(R.id.orderdetailTextView01))
								.setText(detail[0].trim());

						((TextView) orderDetailView
								.findViewById(R.id.orderdetailTextView02))
								.setText(detail[1].trim() + "元");

						((TextView) orderDetailView
								.findViewById(R.id.orderdetailTextView03))
								.setText(detail[2].trim());

						((TextView) orderDetailView
								.findViewById(R.id.orderdetailTextView04))
								.setText(detail[3].trim());

						((TextView) orderDetailView
								.findViewById(R.id.orderdetailTextView05))
								.setText(detail[4].trim());

						((TextView) orderDetailView
								.findViewById(R.id.orderdetailTextView06))
								.setText(detail[5].trim());
					}
				});
			}

		});
		Button delete = (Button)findViewById(R.id.orderdetailDelete);
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				threadPool.execute(new Runnable() {
					@Override
					public void run() {
						String url = "http://" + Constant.IP_ADDRESS + ":9080/orderSystem/deleteOrder.jsp";
						final String mgStr = HttpUploadUtil.postWithoutFile(url, params);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(mgStr.equals("订单删除成功!")){
									
									orderFragment.gotoOrderList();
									Toast.makeText(OrderDetailActivity.this, "订单删除成功!", Toast.LENGTH_LONG).show();
								}
								else{
									Toast.makeText(OrderDetailActivity.this, "订单删除失败!", Toast.LENGTH_LONG).show();
								}	
							}
						});
					}
				});
				
			}
		});
	}
	
}
