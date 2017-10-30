package com.stu.ordersystem;


import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import com.stu.ordersystem.R;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.StaticLayout;
import android.text.LoginFilter.UsernameFilterGeneric;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener{
	private ViewPager mViewPager;
	private FragmentPagerAdapter pagerAdapter;
	// �ײ����ĸ�tab��ť����������ͣ�����������
	private LinearLayout mTabWaimai;
	private LinearLayout mTabDiancan;
	private LinearLayout mTabOrder;
	private LinearLayout mTabMy;

	private TextView waimaiImg;
	private TextView diancanImg;
	private TextView orderImg;
	private TextView myImg;

	private TextView waimaiText;
	private TextView diancanText;
	private TextView orderText;
	private TextView myText;

	Handler hd;
	// WhichView curr;
	String[] listArray;// �����б�����
	String[] orderDetail;// ������������
	static String realName;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		initView();
		initEvents();
		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		getRealName();
	}

	private void initEvents() {
		// ���ײ���4��LinearLayout��4�������ؼ���ӵ���¼�
		mTabWaimai.setOnClickListener(this);
		mTabDiancan.setOnClickListener(this);
		mTabOrder.setOnClickListener(this);
		mTabMy.setOnClickListener(this);
		// viewpager�����¼�
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {// ��viewpager����ʱ����Ӧ�ĵײ�������ť��ͼƬҪ�仯
				int currentItem = mViewPager.getCurrentItem();
				resetIcon();
				switch (currentItem) {
				case 0:
					waimaiImg.setTextColor(0xff992211);
					waimaiText.setTextColor(0xff992211);
					break;
				case 1:
					diancanImg.setTextColor(0xff992211);
					diancanText.setTextColor(0xff992211);
					break;
				case 2:
					orderImg.setTextColor(0xff992211);
					orderText.setTextColor(0xf992211);
					break;
				case 3:
					myImg.setTextColor(0xff992211);
					myText.setTextColor(0xff992211);
					break;

				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initView() {// ��ʼ�����е�view
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		// tabs
		mTabWaimai = (LinearLayout) findViewById(R.id.id_tab_waimai);
		mTabDiancan = (LinearLayout) findViewById(R.id.id_tab_diancan);
		mTabOrder = (LinearLayout) findViewById(R.id.id_tab_order);
		mTabMy = (LinearLayout) findViewById(R.id.id_tab_my);
		// Icon
		waimaiImg = (TextView) findViewById(R.id.waimai);
		diancanImg = (TextView) findViewById(R.id.diancan);
		orderImg = (TextView) findViewById(R.id.order);
		myImg = (TextView) findViewById(R.id.my);
		// IconText
		waimaiText = (TextView) findViewById(R.id.waimai_text);
		diancanText = (TextView) findViewById(R.id.diancan_text);
		orderText = (TextView) findViewById(R.id.order_text);
		myText = (TextView) findViewById(R.id.my_text);

		Typeface iconfont = Typeface.createFromAsset(getAssets(),
				"font/iconfont.ttf");
		waimaiImg.setTypeface(iconfont);
		diancanImg.setTypeface(iconfont);
		orderImg.setTypeface(iconfont);
		myImg.setTypeface(iconfont);
	}

	// ��ʼ��PagerAdapter
	public class MyPagerAdapter extends FragmentPagerAdapter {
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		// pagerAdapter = new FragmentPagerAdapter(
		// getSupportFragmentManager()) {
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new OneFragment();
				break;
			case 1:
				fragment = new TwoFragment();
				break;
			case 2:
				fragment = new OrderFragment();
				break;
			case 3:
				fragment = new AboutMeFragment();
				break;
			}
			return fragment;
		}

		public int getCount() {
			return 4;
		}

		// }
		//
		// mViewPager.setAdapter(pagerAdapter);
		// Ϊviewpager�����ʱ�������
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		resetIcon();// ����ĸ�tab,��Ӧ����ɫҪ��������ˣ��ڵ�������tab֮ǰ�Ƚ����е�ͼƬ������Ϊδ�����״̬������ɫͼƬ
		switch (v.getId()) {
		case R.id.id_tab_waimai:
			mViewPager.setCurrentItem(0);// ����������΢�ţ��ͽ�viewpager�ĵ�ǰitem����Ϊ0�����л���΢�������viewpager����
			waimaiImg.setTextColor(this.getResources().getColor(
					R.drawable.pressColor));// ������ť��ɫ����
			waimaiText.setTextColor(this.getResources().getColor(
					R.drawable.pressColor));// ��������ɫ����
			break;
		case R.id.id_tab_diancan:
			mViewPager.setCurrentItem(1);
			diancanImg.setTextColor(this.getResources().getColor(
					R.drawable.pressColor));
			diancanText.setTextColor(this.getResources().getColor(
					R.drawable.pressColor));
			break;
		case R.id.id_tab_order:
			mViewPager.setCurrentItem(2);
			orderImg.setTextColor(this.getResources().getColor(
					R.drawable.pressColor));
			orderText.setTextColor(this.getResources().getColor(
					R.drawable.pressColor));
			break;
		case R.id.id_tab_my:
			mViewPager.setCurrentItem(3);
			myImg.setTextColor(this.getResources().getColor(
					R.drawable.pressColor));
			myText.setTextColor(this.getResources().getColor(
					R.drawable.pressColor));
			break;

		default:
			break;
		}

	}

	/*
	 * �����е�ͼƬ����Ϊ��ɫ
	 */
	private void resetIcon() {
		waimaiImg.setTextColor(this.getResources().getColor(
				R.drawable.nomalColor));
		diancanImg.setTextColor(this.getResources().getColor(
				R.drawable.nomalColor));
		orderImg.setTextColor(this.getResources().getColor(
				R.drawable.nomalColor));
		myImg.setTextColor(this.getResources().getColor(R.drawable.nomalColor));

		waimaiText.setTextColor(this.getResources().getColor(
				R.drawable.nomalColor));
		diancanText.setTextColor(this.getResources().getColor(
				R.drawable.nomalColor));
		orderText.setTextColor(this.getResources().getColor(
				R.drawable.nomalColor));
		myText.setTextColor(this.getResources().getColor(R.drawable.nomalColor));
	}
	
	protected void getRealName() {
		final String url ="http://" + Constant.IP_ADDRESS + ":9080/orderSystem/userinfo.jsp";
		String uname = RegAndLog.username;
		final Map<String, String> params = new HashMap<String, String>();
		params.put("param1", uname);
		
		new Thread(){
				public void run() {
					final String mgstr = HttpUploadUtil.postWithoutFile(url, params);
					runOnUiThread(new Runnable() {						
						public void run() {
							String[] info = mgstr.split("\\|");
							realName = info[1];
						}
					});
					
			}
		}.start();
	}
	


}
