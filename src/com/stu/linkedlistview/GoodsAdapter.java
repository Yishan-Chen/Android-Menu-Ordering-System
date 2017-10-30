package com.stu.linkedlistview;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.stu.linkedlistview.Waimai;
import com.stu.ordersystem.R;
import com.stu.javabean.Goods;

import java.util.List;

/**
 * Created by bobge on 15/7/31.
 */
public class GoodsAdapter extends BaseAdapter {

	private List<Goods> list;
	private Context context;
	private CatograyAdapter catograyAdapter;
//	private Waimai wm = new Waimai();

	public GoodsAdapter(Context context, List<Goods> list1) {
		this.context = context;
		this.list = list1;
	}
	public List<Goods> getList(){
		return list;
	}
	public GoodsAdapter(Context context, List<Goods> list2,
			CatograyAdapter catograyAdapter) {
		this.context = context;
		this.list = list2;
		this.catograyAdapter = catograyAdapter;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final Viewholder viewholder;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.goods_item_list, null);
			viewholder = new Viewholder();
			viewholder.name = (TextView) view.findViewById(R.id.beefNoodleText);
			viewholder.desc = (TextView) view.findViewById(R.id.beefNoodleDesc);
			viewholder.price = (TextView) view
					.findViewById(R.id.beefNoodlePrice);
			viewholder.img = (ImageView) view.findViewById(R.id.beefNoodleView);
			viewholder.add = (ImageView) view.findViewById(R.id.AddbeefNoodle);
			viewholder.remove = (ImageView) view
					.findViewById(R.id.beefNoodle_remove);
			viewholder.count = (EditText) view
					.findViewById(R.id.beefnoodle_count);
			view.setTag(viewholder);
		} else
			viewholder = (Viewholder) view.getTag();
		viewholder.name.setText(list.get(position).getGood_name());
		viewholder.desc.setText(list.get(position).getDescrible());
		Double price = Double.valueOf(list.get(position).getPrice());
		viewholder.price.setText(price.toString()+ "¥");
		String imgName = list.get(position).getImgName();
		Resources res = view.getResources();
		int id = res.getIdentifier(imgName, "drawable",
				"com.stu.ordersystem");
		viewholder.img.setImageResource(id);

		viewholder.remove.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int count = list.get(position).getCount();
				if(count >= 1){
					if(Waimai.totalprice>0){
						Waimai.totalprice -= Double.valueOf((list.get(position).getPrice()));
					}
					else{
						Waimai.totalprice =0;
					}
				}
				if (count >= 1) {
					count--;
				}
				list.get(position).setCount(count);
				viewholder.count.setText(list.get(position).getCount() + "");
				if(Waimai.buyNum >= 1){
					Waimai.buyNum--;
				}
				catograyAdapter.notifyDataSetChanged();
				
				if(count == 0){
					viewholder.remove.setVisibility(viewholder.remove.INVISIBLE);
					viewholder.count.setVisibility(viewholder.remove.INVISIBLE);
				}
				int[] startLocation = new int[2];
				ImageView ball = new ImageView(context);
				ball.setImageResource(R.drawable.drop);
				((Waimai) context).setAnim(ball, startLocation);
			}
		});

		viewholder.add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Waimai.buyNum ++;
				Waimai.totalprice += list.get(position).getPrice();
				int count = list.get(position).getCount();
				count++;
				list.get(position).setCount(count);
				viewholder.count.setVisibility(View.VISIBLE);
				viewholder.remove.setVisibility(View.VISIBLE);
				viewholder.count.setText(list.get(position).getCount() + "");
				catograyAdapter.notifyDataSetChanged();

				int[] startLocation = new int[2];
				v.getLocationInWindow(startLocation);
				ImageView ball = new ImageView(context);
				ball.setImageResource(R.drawable.smileface2);
				((Waimai) context).setAnim(ball, startLocation);
			}
		});
//		 Log.i("test",list.get(position).getCount()+"==");
		if (list.get(position).getCount() <= 0) {
			viewholder.count.setVisibility(View.INVISIBLE);
			viewholder.remove.setVisibility(View.INVISIBLE);
		} else {
			viewholder.count.setVisibility(View.VISIBLE);
			viewholder.remove.setVisibility(View.VISIBLE);
		}
		return view;
	}

	class Viewholder {
		TextView name;
		TextView desc;
		TextView price;
		ImageView add;
		ImageView remove;
		ImageView img;
		EditText count;
	}

}
