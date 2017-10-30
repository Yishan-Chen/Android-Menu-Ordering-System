package com.stu.linkedlistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.stu.linkedlistview.BadgeView;
import com.stu.linkedlistview.CatograyAdapter;
import com.stu.linkedlistview.GoodsAdapter;
import com.stu.ordersystem.Constant;
import com.stu.ordersystem.HttpUploadUtil;
import com.stu.ordersystem.R;
import com.stu.javabean.Catogray;
import com.stu.javabean.Goods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Waimai extends Activity{
	private ListView listView1,listView2;

    private List<Catogray> list;
    private List<Goods> list1;
    private List<Goods> list2;
    private List<Goods> list3;
    private List<Goods> list4;
    private List<Goods> allList;
    
    private CatograyAdapter catograyAdapter;
    private GoodsAdapter goodsAdapter;

    private ImageView shopCart;
    private ViewGroup anim_mask_layout;
    private ImageView ball;
    private Button checkoutButton;
    public static int buyNum =0;
    public static double totalprice = 0;
    
    private BadgeView buyNumView;
    private BadgeView totalPriceView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.waimai);
        initView();
        initListData();
        initList1();
        addListener();
        	
    }

    private void addListener() {
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list2.clear();
                list2.addAll(list.get(position).getList());
                goodsAdapter.notifyDataSetChanged();
            }
        });
        
        checkoutButton.setVisibility(checkoutButton.INVISIBLE);
        
        checkoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Waimai.this,CheckOut.class);
				Bundle extra = new Bundle();  
				extra.putParcelableArrayList("goodslist",(ArrayList<? extends Parcelable>) allList);
				intent.putExtras(extra);
				startActivity(intent);
			}
		});
    }

    private void initList1() {
        catograyAdapter=new CatograyAdapter(this,list);
        listView1.setAdapter(catograyAdapter);

        list2=new ArrayList<Goods>();
        list2.addAll(list.get(0).getList());
        ImageView shopCat= (ImageView) findViewById(R.id.iv_add_cart);
        goodsAdapter=new GoodsAdapter(this,list2,catograyAdapter);
        listView2.setAdapter(goodsAdapter);
    }

    private void initListData() {
    	list=new ArrayList<Catogray>();
    	final String URL = "http://" + Constant.IP_ADDRESS + ":9080/orderSystem/getAllProduct.jsp";
        final Map<String,String> params = new HashMap<String, String>();
        params.put("param1", "1");
        final String mgStr = HttpUploadUtil.postWithoutFile(URL, params);
        int tag=0;
		String[] totalinfo = mgStr.split("-");
		Catogray catograyone=new Catogray();
		catograyone.setKind("美食");
		list1= new ArrayList<Goods>();
		for(String str :totalinfo){
			String[] proinfo = str.split("\\|");
			Goods good = new Goods();
			good.setImgName(proinfo[0].trim());
			good.setGood_name(proinfo[1].trim());
			good.setPrice(Double.valueOf(proinfo[2].trim()));
			good.setDescrible(proinfo[3].trim());		
			list1.add(good);
			tag++;
			if(tag>2){break;}
		}
		
		tag = 0;
		Catogray catograytwo=new Catogray();
		catograytwo.setKind("饮料");
		list3= new ArrayList<Goods>();
		for(String str : totalinfo){
			if(tag > 2 && tag <7){
				String[] proinfo = str.split("\\|");
				Goods good = new Goods();
				good.setImgName(proinfo[0].trim());
				good.setGood_name(proinfo[1].trim());
				good.setPrice(Double.valueOf(proinfo[2].trim()));
				good.setDescrible(proinfo[3].trim());
				list3.add(good);
	        }
			tag++;
		}
		
		Catogray catograyAll = new Catogray();
		catograyAll.setKind("全部");
		allList = new ArrayList<Goods>();
		
		for(Goods item : list1){
			allList.add(item);
		}
		for(Goods item: list3){
			allList.add(item);
		}
		catograyAll.setList(allList);
		catograyone.setList(list1);
		catograytwo.setList(list3);
		list.add(catograyAll);
		list.add(catograyone);
		list.add(catograytwo);       
    }


    private void initView() {
        
        listView1= (ListView) findViewById(R.id.listview_1);
        listView2= (ListView) findViewById(R.id.listview_2);
        
        checkoutButton = (Button)findViewById(R.id.checkout);
        shopCart= (ImageView) findViewById(R.id.iv_add_cart);
        buyNumView= (BadgeView) findViewById(R.id.tv_count);
        totalPriceView = (BadgeView) findViewById(R.id.tv_count_price);
        
        buyNumView.hide();
        totalPriceView.hide();
    }


    /**
     * @Description:      @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }
    public void setAnim2(){
    	
    }
    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                startLocation);
        int[] endLocation = new int[2];
        shopCart.getLocationInWindow(endLocation);


        int endX = 0 - startLocation[0] + 40;
        int endY = endLocation[1] - startLocation[1];
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);  
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);
        view.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
                       @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

                      @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
//                Log.i("test",String.valueOf(buyNum));
                buyNumView.setText(buyNum + "");
                if(totalprice >= 12){
                	checkoutButton.setVisibility(checkoutButton.VISIBLE);
                }
                else {
                	checkoutButton.setVisibility(checkoutButton.INVISIBLE);
				}
           
                totalPriceView.setText("总价:" + totalprice + "¥");
                
               
                buyNumView.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
                totalPriceView.setBadgePosition(BadgeView.POSITION_CENTER);
                if (buyNum > 0) {
                	buyNumView.show();
				}
                else {
					buyNumView.hide();
				}
                if(totalprice > 0){
                	totalPriceView.show();
                }
            }
        });

    }
}
