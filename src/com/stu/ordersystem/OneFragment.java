package com.stu.ordersystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OneFragment extends Fragment {
	public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
    }  
	public View onCreateView(LayoutInflater inflater,  
            ViewGroup container, Bundle savedInstanceState) {  
	View tab01 = inflater.inflate(R.layout.tab01, container, false);//关联布局文件  
		return tab01;
	}  
	
	@Override  
	public void onActivityCreated(Bundle savedInstanceState) {  
	super.onActivityCreated(savedInstanceState);  
	}  
}
