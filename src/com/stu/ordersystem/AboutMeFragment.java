package com.stu.ordersystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableRow;

public class AboutMeFragment extends Fragment {
	private TableRow setting, help, log_out;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View aboutMe = inflater.inflate(R.layout.my, container, false);// 关联布局文件
		setting = (TableRow) aboutMe.findViewById(R.id.my_setting);
		help = (TableRow) aboutMe.findViewById(R.id.help);
		log_out = (TableRow) aboutMe.findViewById(R.id.logout);
		gotoSetting();
		return aboutMe;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void gotoSetting() {
		setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), Setting.class);
				getActivity().startActivity(intent);
			}
		});
		
		help.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),Help.class);
				getActivity().startActivity(intent);
			}
		});
		log_out.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),RegAndLog.class);
				getActivity().startActivity(intent);
			}
		});
	}
}
