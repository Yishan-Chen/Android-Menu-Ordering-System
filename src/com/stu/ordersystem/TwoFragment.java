package com.stu.ordersystem;



import com.stu.linkedlistview.Waimai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TableRow;


public class TwoFragment extends Fragment {

    private TableRow enter;
      	
	public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
    }  
	public View onCreateView(LayoutInflater inflater,  
            ViewGroup container, Bundle savedInstanceState) {  
		View waimai = inflater.inflate(R.layout.tab02, container, false);//关联布局文件  
		enter = (TableRow)waimai.findViewById(R.id.waimai);
		gotowaimai();
		return waimai;
	}  
	public void gotowaimai(){
		enter.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),Waimai.class);
				getActivity().startActivity(intent);
			}
		});
	}
	@Override  
	public void onActivityCreated(Bundle savedInstanceState) {  
	super.onActivityCreated(savedInstanceState);  
	}  
}
