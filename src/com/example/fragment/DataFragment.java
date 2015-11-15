package com.example.fragment;

import com.example.healthcheckup.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DataFragment extends Fragment implements OnClickListener{
	private View vData;
	private TextView tvWeight;
	private TextView tvHeartRate;
	private TextView tvBloodPress;
	private TextView tvPluse;
	private TextView tvImpedance;
	private TextView tvFat;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		vData = inflater.inflate(R.layout.data, container, false);
		initView();
		setState();
		return vData;
	}
	public void initView(){
		tvBloodPress = (TextView) vData.findViewById(R.id.data_bloodPress);
		tvFat = (TextView) vData.findViewById(R.id.data_fat);
		tvHeartRate = (TextView) vData.findViewById(R.id.data_heartRate);
		tvImpedance = (TextView) vData.findViewById(R.id.data_impedance);
		tvPluse = (TextView) vData.findViewById(R.id.data_pulse);
		tvWeight = (TextView) vData.findViewById(R.id.data_weight);
		
		tvBloodPress.getBackground().setAlpha(110);
		tvFat.getBackground().setAlpha(110);
		tvHeartRate.getBackground().setAlpha(110);
		tvImpedance.getBackground().setAlpha(110);
		tvPluse.getBackground().setAlpha(110);
		tvWeight.getBackground().setAlpha(110);
	}
	public void setState(){
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
