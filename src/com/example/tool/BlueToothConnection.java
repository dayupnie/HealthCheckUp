package com.example.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Set;
import java.util.UUID;

import com.example.healthcheckup.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BlueToothConnection extends Activity {
	private BluetoothAdapter mBluetoothAdapter;
	private TextView tvDevices;
	private Button btSearch;
	private Button btLink;
	private BluetoothDevice device;
	private BluetoothDevice remoteDevice;
	private BluetoothSocket btSocket;
	private ConnectedThread connectedThread;
//	private final String BLUETOOTH_ADDRESS = "24:FD:52:21:85:37";
	private final String BLUETOOTH_ADDRESS = "00:11:35:89:80:40";
//	private final String BLUETOOTH_NAME = "HC-05";
	private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 请求显示进度条
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);	
		setContentView(R.layout.serach);
		initViews();
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//得到本地默认的bluetoothadapter
		//提示是否打开蓝牙
		if(mBluetoothAdapter!=null){
			if(!mBluetoothAdapter.isEnabled()){
				Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableIntent, 1);
			}
		}

		//注册用于已搜索到的蓝牙设备的Receiver
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);//发现广播
		filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//设备连接状态改变
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//		filter.addAction(android.bluetooth.BluetoothDevice.ACTION_PAIRING_REQUEST);//自动匹配
		this.registerReceiver(receiver, filter);

	}
	public void initViews(){
		tvDevices = (TextView) findViewById(R.id.search_bluetooth);
		btSearch = (Button) findViewById(R.id.search);
		btLink = (Button) findViewById(R.id.link);
		btSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clickSearch();
				setProgressBarVisibility(true);				
			}
		});
		//连接之后
		btLink.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplication(), "点击成功", Toast.LENGTH_SHORT).show();
					try {
						connectDevice();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}
	public void clickSearch(){
		setProgressBarIndeterminateVisibility(true);
		Toast.makeText(getApplication(), "正在搜索~", Toast.LENGTH_SHORT).show();
		if(mBluetoothAdapter.isDiscovering()){
			mBluetoothAdapter.cancelDiscovery();
		}
		//开始搜索
		mBluetoothAdapter.startDiscovery();
	}
	//广播接收器（搜索接收函数）
	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action)){
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if(device.getAddress().equals(BLUETOOTH_ADDRESS)){
					tvDevices.append(device.getName()+":"+device.getAddress()+"\n");
					remoteDevice = device;
					Toast.makeText(getApplication(), "搜索完成~", Toast.LENGTH_SHORT).show();
					if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){
						switch(device.getBondState()){
						case BluetoothDevice.BOND_BONDING:
							Log.d("BlueToothTestActivity", "正在配对...");
							break;
						case BluetoothDevice.BOND_BONDED:
							Log.d("BlueToothTestActivity", "完成配对");
							break;
						case BluetoothDevice.BOND_NONE:
							Log.d("BlueToothTestActivity", "取消配对");
							break;
						}
					}else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
						mBluetoothAdapter.cancelDiscovery();//取消搜索
					}
				}
			}
		}
	};
	/**
	 * 建立连接通信
	 */
	public void connectDevice(){
		try {
			btSocket = remoteDevice.createRfcommSocketToServiceRecord(MY_UUID);
			Log.d("TAG", "连接成功");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Log.d("TAG", "连接失败");
		}
		if(remoteDevice.getBondState() != BluetoothDevice.BOND_BONDED){
			Toast.makeText(getApplication(), "not~~", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplication(), "connecting...", Toast.LENGTH_SHORT).show();
				Method method = null;
				try {
					method = BluetoothDevice.class.getMethod("createBond");
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Log.e("TAG", "开始配对");
				try {
					method.invoke(remoteDevice);
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					btSocket.connect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} 
		if(remoteDevice.getBondState() == BluetoothDevice.BOND_BONDED){
			connectedThread = new ConnectedThread(btSocket);
			connectedThread.start();
			Log.e("TAG", "send message");
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiver);
		super.onDestroy();
	}
}
