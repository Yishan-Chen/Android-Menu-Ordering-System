package com.stu.linkedlistview;


import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.stu.javabean.Goods;
import com.stu.ordersystem.Constant;
import com.stu.ordersystem.HttpUploadUtil;
import com.stu.ordersystem.R;
import com.stu.ordersystem.RegAndLog;
import com.stu.ordersystem.Setting;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckOut extends Activity {
	private TextView realname;
	private TextView totalprice;
	private EditText telnum;
	private EditText address;
	private EditText descri;
	private Button orderButton;
	private ListView item;
	private String gender;

	private List<Goods> goodsList;
	private MyBaseAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);
		initView();
		initData();
		addListener();
	}

	private void addListener() {
		orderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final String url = "http://" + Constant.IP_ADDRESS + ":9080/orderSystem/addOrder.jsp";
				final Map<String, String> params = new HashMap<String, String>();
				params.put("param1", realname.getText().toString());
				params.put("param2", telnum.getText().toString());
				params.put("param3", address.getText().toString());
				params.put("param4", descri.getText().toString());
				params.put("param6",  String.valueOf(Waimai.totalprice));
				StringBuilder  orders =  new StringBuilder ();
				for(Goods item : goodsList){
					orders.append(item.getGood_name());
					orders.append(" ");
					orders.append("×");
					orders.append(String.valueOf(item.getCount()));
					orders.append(",");
				}
				params.put("param5", orders.toString());
				new Thread(){
					public void run(){
						final String mgstr = HttpUploadUtil.postWithoutFile(url, params);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Log.i("msg", mgstr);
								if (mgstr.equals("successful")) {
									Intent intent = new Intent(CheckOut.this,Info.class);
									startActivity(intent);
									Toast.makeText(CheckOut.this, "订单下订成功",Toast.LENGTH_LONG).show();
								}
								else {
									Toast.makeText(CheckOut.this, "订单下订失败", Toast.LENGTH_LONG).show();
								}
							}
						});
					}
				}.start();
				
			}
		});
	}


	protected void initView() {
		realname = (TextView) findViewById(R.id.nameText);
		totalprice = (TextView) findViewById(R.id.totalprice);

		telnum = (EditText) findViewById(R.id.teleEditText);
		address = (EditText) findViewById(R.id.addressEditText);
		descri = (EditText)findViewById(R.id.descriEditText);

		item = (ListView) findViewById(R.id.itemlist);
		orderButton = (Button) findViewById(R.id.orderbutton);
	}

	protected void initData() {
		Intent intent = getIntent();
		goodsList = intent.getExtras().getParcelableArrayList("goodslist");
		Iterator<Goods> check = goodsList.iterator();
		while(check.hasNext()){
			Goods item = check.next();
			if(item.getCount() < 1){
				check.remove();
			}
		}
		
		String username = RegAndLog.username;
		mAdapter = new MyBaseAdapter(goodsList);
		item.setAdapter(mAdapter);
		final String url = "http://" + Constant.IP_ADDRESS
				+ ":9080/orderSystem/userinfo.jsp";
		final Map<String, String> params = new HashMap<String, String>();
		params.put("param1", username);
		String mgStr = HttpUploadUtil.postWithoutFile(url, params);
		String[] data = mgStr.split("\\|");
		telnum.setText(data[3].trim());

		gender = data[2].trim();
		realname.setText(data[1].trim());
		totalprice.setText("总价:" + Waimai.totalprice + "¥");

		mAdapter.notifyDataSetChanged();
	}

	private class MyBaseAdapter extends BaseAdapter {
		private List<Goods> data;
		
		public MyBaseAdapter(List<Goods> data) {
			
			this.data = data;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
//			Log.i("count", String.valueOf(data.size()));
			return data.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return data.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			LinearLayout info = (LinearLayout) getLayoutInflater().inflate(
					R.layout.order_item_list, null);
			((TextView) info.findViewById(R.id.itemName)).setText(data
					.get(arg0).getGood_name());
			((TextView) info.findViewById(R.id.itemCount)).setText("×" + data.get(
					arg0).getCount());
			((TextView) info.findViewById(R.id.itemPrice)).setText("¥" + String.valueOf(
					data.get(arg0).getPrice()));
			return info;
		}
	}

}
