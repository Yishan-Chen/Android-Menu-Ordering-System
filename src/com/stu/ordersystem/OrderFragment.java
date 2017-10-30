package com.stu.ordersystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class OrderFragment extends Fragment {
	private ListView list;
	private View orderView;
	private List<Order> data = new ArrayList<Order>();
	private MyBaseAdapter mAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		orderView = inflater.inflate(R.layout.orders, container, false);// 关联布局文件
		gotoOrderList();
		return orderView;
	}


	private class MyBaseAdapter extends BaseAdapter {

		private List<Order> data;

		public MyBaseAdapter(List<Order> data) {
			this.data = data;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			LinearLayout info = (LinearLayout) LayoutInflater.from(
					getActivity()).inflate(R.layout.items, null);
			((TextView) info.findViewById(R.id.index))
					.setText(data.get(arg0).oid);

			((TextView) info.findViewById(R.id.time))
					.setText(data.get(arg0).time);

			((TextView) info.findViewById(R.id.status))
					.setText(data.get(arg0).status);
			return info;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return data.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}
	};

	

	protected void gotoOrderList() {
		String realname = MainActivity.realName;
		list = (ListView) orderView.findViewById(R.id.orderListView01);
		mAdapter = new MyBaseAdapter(data);
		list.setAdapter(mAdapter);
		final String url = "http://" + Constant.IP_ADDRESS
				+ ":9080/orderSystem/orderclient.jsp";// 准备URL
		final Map<String, String> params = new HashMap<String, String>();// 准备参数
		params.put("param1", realname);

		// 开启线程发送消息
		new Thread() {
			public void run() {
				// 子线程
				final String msgStr = HttpUploadUtil.postWithoutFile(url,
						params);
				// 主线程
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						data.clear();
						String[] orders = msgStr.split("-");
						for (String str : orders) {
							Order order = new Order();
							String[] attrs = str.split("\\|");
							order.setOid(attrs[0]);
							order.setTime(attrs[1]);
							order.setStatus(attrs[2]);
							data.add(order);
						}
						mAdapter.notifyDataSetChanged();
					}
				});
			}
		}.start();

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),OrderDetailActivity.class);
				intent.putExtra("arg", Integer.parseInt(data.get(arg2).getOid().trim()));
				getActivity().startActivity(intent);
			}
		});

	}

	

	private class Order {
		private String oid;
		private String time;
		private String status;

		public String getOid() {
			return oid;
		}

		public void setOid(String oid) {
			this.oid = oid;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}

}
