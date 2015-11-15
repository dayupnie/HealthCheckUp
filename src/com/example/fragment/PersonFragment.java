package com.example.fragment;

import com.example.healthcheckup.R;
import com.example.tool.BlueToothConnection;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class PersonFragment extends Fragment implements OnClickListener{
	private View vPerson;
	private TextView tvUsername;
	private View vAlterInfo;
	private View vAlterPassword;
	private View vFeedBack;
	private View vUpdate;
	private View vAboutUs;
	private View vExit;
	
	private Intent intent;
	private Image imgPortrait;
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		vPerson = inflater.inflate(R.layout.person, container, false);
		initView();
		setState();
		return vPerson;
	}
	public void initView(){
		vAlterInfo = vPerson.findViewById(R.id.person_aleterInfo);
		vAlterPassword = vPerson.findViewById(R.id.person_aleterPassword);
		vFeedBack = vPerson.findViewById(R.id.person_feedBack);
		vUpdate = vPerson.findViewById(R.id.person_updateVersion);
		vAboutUs = vPerson.findViewById(R.id.person_aboutUs);
		vExit = vPerson.findViewById(R.id.person_exit);
	}
	public void setState(){
		vAlterInfo.setOnClickListener(this);
		vAlterPassword.setOnClickListener(this);
		vFeedBack.setOnClickListener(this);
		vUpdate.setOnClickListener(this);
		vAboutUs.setOnClickListener(this);
		vExit.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.person_aleterInfo:
			intent = new Intent(getActivity(), BlueToothConnection.class);
			startActivity(intent);
			break;
		case R.id.person_aboutUs:
			intent = new Intent(getActivity(), BlueToothConnection.class);
			startActivity(intent);
			break;
		case R.id.person_aleterPassword:
			intent = new Intent(getActivity(), BlueToothConnection.class);
			startActivity(intent);
			break;
		case R.id.person_updateVersion:
			intent = new Intent(getActivity(), BlueToothConnection.class);
			startActivity(intent);
			break;
		case R.id.person_feedBack:
			intent = new Intent(getActivity(), BlueToothConnection.class);
			startActivity(intent);
			break;
		case R.id.person_exit:
			intent = new Intent(getActivity(), BlueToothConnection.class);
			startActivity(intent);
			break;
		}
	}

}
