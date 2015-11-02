package com.example.fragment;

import com.example.healthcheckup.R;
import com.example.tool.BlueTooth;
import com.example.tool.BlueToothConnection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnClickListener{
	private View vHome;
	private TextView tvLink;
	private Intent intent;
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) {
		vHome = inflater.inflate(R.layout.home, container, false);
		initTextView();
		setState();
		return vHome;
	}
	public void initTextView(){
		tvLink = (TextView) vHome.findViewById(R.id.home_tv_goto);
	}
	public void setState(){
		tvLink.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.home_tv_goto:
			intent = new Intent(getActivity(), BlueToothConnection.class);
			startActivity(intent);
			
			break;
		}
	}
}
