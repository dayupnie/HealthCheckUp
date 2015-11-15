package com.example.healthcheckup;

import java.util.ArrayList;
import java.util.List;

import com.example.fragment.DataFragment;
import com.example.fragment.HomeFragment;
import com.example.fragment.PersonFragment;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.BoringLayout.Metrics;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements  OnClickListener{
	private TextView tvHome;
	private TextView tvData;
	private TextView tvPerson;
	private ImageView imgCursor;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private List<Fragment> fragments;
	private int currentIndex = 0;//当前页面编号
	private int cursorwidth;//横条图片的宽度
	private int offSet;//图片的偏移量
	final private int ATB_COUNT = 2;//fragment的数量
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initTextView();
//		initImage();
		initViews();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.options_menu, menu);
		return true;
	}
	public void initTextView(){
		tvHome = (TextView) findViewById(R.id.tv_home);
		tvData = (TextView) findViewById(R.id.tv_data);
		tvPerson = (TextView) findViewById(R.id.tv_person);
		tvHome.setOnClickListener(new TvOnClickListener(0));
		tvData.setOnClickListener(new TvOnClickListener(1));
		tvPerson.setOnClickListener(new TvOnClickListener(2));
	}
	public class TvOnClickListener implements View.OnClickListener{
		private int index = 0;
		public TvOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(index);
		}
		
	}
	public void initImage(){
		imgCursor = (ImageView) findViewById(R.id.img_cursor);
		cursorwidth = BitmapFactory.decodeResource(getResources(), R.drawable.cursor).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offSet = (int) ((screenW/(float)ATB_COUNT - cursorwidth)/2);
		//平移
		Matrix matrix = new Matrix();
		matrix.postTranslate(offSet, 0);
		imgCursor.setImageMatrix(matrix);
	}
	public void initViews(){
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		fragments = new ArrayList<Fragment>();
		fragments.add(new HomeFragment());
		fragments.add(new DataFragment());
		fragments.add(new PersonFragment());
		
		pagerAdapter = new com.example.view.PagerAdapter(getSupportFragmentManager(), (ArrayList<Fragment>) fragments);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(0);//当前显示第一页
		tvHome.setTextColor(getResources().getColor(R.color.white));
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//			int single = offSet*2+cursorwidth;
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
//				Animation animation = new TranslateAnimation(single*currentIndex, single*arg0, 0, 0);
//				animation.setFillAfter(true);
//				animation.setDuration(300);
//				imgCursor.startAnimation(animation);
				tvHome.setTextColor(getResources().getColor(R.color.simple_font));
				tvData.setTextColor(getResources().getColor(R.color.simple_font));
				tvPerson.setTextColor(getResources().getColor(R.color.simple_font));
				switch(arg0){
				case 0:
					tvHome.setTextColor(getResources().getColor(R.color.white));
					break;
				case 1:
					tvData.setTextColor(getResources().getColor(R.color.white));
					break;
				case 2:
					tvPerson.setTextColor(getResources().getColor(R.color.white));
					break;
				}
				currentIndex = arg0;
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
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		}
	}
}
