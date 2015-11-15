package com.example.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.healthcheckup.R;
import com.example.healthcheckup.TestWeightActivity;
import com.example.tool.BlueTooth;
import com.example.tool.Db;

import come.example.domain.Weight;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements OnClickListener{
	private View vHome;
	
	private TextView tvLink;
	private TextView tvWeight;
	private TextView tvBloodpress;
	private TextView tvHeartrate;
	
	private Intent intent;
	private Handler handler;
	private String result = "";
	private String strData = "";
	private String strJson = "";
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) {
		vHome = inflater.inflate(R.layout.home, container, false);
		initView();
		setState();
		return vHome;
	}
	@SuppressLint("NewApi")
	public void initView(){
		tvLink = (TextView) vHome.findViewById(R.id.home_tv_goto);
		tvBloodpress = (TextView) vHome.findViewById(R.id.home_bloodpress);
		tvHeartrate = (TextView) vHome.findViewById(R.id.home_heartrate);
		tvWeight = (TextView) vHome.findViewById(R.id.home_weight);
		tvBloodpress.getBackground().setAlpha(180);
		tvHeartrate.getBackground().setAlpha(180);
		tvWeight.getBackground().setAlpha(180);
	}
	public void setState(){
		tvLink.setOnClickListener(this);
		tvBloodpress.setOnClickListener(this);
		tvHeartrate.setOnClickListener(this);
		tvWeight.setOnClickListener(this);
	}
	public void linkBluetooth(){
		class BlueToothHandler extends Handler{
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle b = msg.getData();
				strData = b.getString("str");
				result += strData;
				Log.d("result", result);
				if(!TextUtils.isEmpty(strData)){
					doData();
				}
			}
		}
		handler = new BlueToothHandler();
		BlueTooth blueTooth = new BlueTooth(getActivity(), handler);
		blueTooth.search();
	}
	public void doData(){
		if(strData.indexOf("Z") != -1){
			strJson = result.substring(result.indexOf("{"), result.indexOf("Z"));
			result = result.substring(result.indexOf("Z")+1, result.length());
			Log.d("~~~~~~~~~~~~~~~~~~~~", strJson);
			Log.d("resultchange", result);
			try {
				JSONObject jsonObject = new JSONObject(strJson);
				if(strJson.contains("BodyWeight")){
					String strWeight = jsonObject.getString("BodyWeight");
					Double dWeight = Double.parseDouble(strWeight);
					Weight weight = new Weight();
					weight.setWeight(dWeight);//get the data
					Db db = new Db(getActivity());
					db.insertWeight(weight);
				}
				//just a little data
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.home_tv_goto:
//			intent = new Intent(getActivity(), BlueToothConnection.class);
//			startActivity(intent);
			linkBluetooth();
			break;
		case R.id.home_bloodpress:
			break;
		case R.id.home_heartrate:
			break;
		case R.id.home_weight:
			intent = new Intent(vHome.getContext(), TestWeightActivity.class);
			startActivity(intent);
			break;
		}
	}
}
