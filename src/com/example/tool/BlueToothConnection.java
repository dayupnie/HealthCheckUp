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
	private BluetoothDevice device;
	private BluetoothDevice remoteDevice;
	private BluetoothSocket btSocket;
	private final String BLUETOOTH_ADDRESS = "24:FD:52:21:85:37";
//	private final String BLUETOOTH_ADDRESS = "30:15:01:27:06:61";
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
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);//�ҵ��豸�Ĺ㲥
		this.registerReceiver(receiver, filter);
		//注册搜索完成时的Receiver
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//ȫ��������
		this.registerReceiver(receiver, filter); 
	}
	public void initViews(){
		tvDevices = (TextView) findViewById(R.id.search_bluetooth);
		btSearch = (Button) findViewById(R.id.search);
		btSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clickSearch();
				setProgressBarVisibility(true);				
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
					Toast.makeText(getApplication(), "搜索完成~", Toast.LENGTH_SHORT).show();
				}
				ConnectThread connectThread = new ConnectThread(device);
				connectThread.run();
			}
//			//搜索完成
//			else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
//				setProgressBarVisibility(false);
//				Toast.makeText(getApplication(), "搜索完成~", Toast.LENGTH_SHORT).show();
//				ConnectThread connectThread = new ConnectThread(device);
//				connectThread.run();
//			}
		}
	};
	public class ConnectThread extends Thread {
		private final BluetoothSocket mSocket;
		private final BluetoothDevice mDevice;
		public ConnectThread(BluetoothDevice device) {
		// Use a temporary object that is later assigned to mmSocket,
		// because mmSocket is final
		BluetoothSocket tmp = null;
		mDevice = device;
		// Get a BluetoothSocket to connect with the given BluetoothDevice
		try {
			tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
		} catch (IOException e) { }
			mSocket = tmp;
		}
		public void run() {
			//取消搜索
			mBluetoothAdapter.cancelDiscovery();
			connectDevice();
		try {
			mSocket.connect();
//			ConnectedThread ced = new ConnectedThread(mSocket);
//			ced.start();
		}
		catch (Exception e) {
			Log.e("connect0e",e.toString());
		//t1.append("\r\n 连接失败001" );
		// Unable to connect; close the socket and get out
		try {
			mSocket.close();
		}
		catch (Exception e1)
		{
		Log.e("close",e1.toString());
		}
		}
		// Do work to manage the connection (in a separate thread)
		// manageConnectedSocket(mmSocket);
		}
		public void coDevice(){
//			new Thread(){
//				public void run(){
			if(device.getBondState() == BluetoothDevice.BOND_NONE){
				try {
					Method method = device.getClass().getMethod("createBond");
					Log.d("BlueToothTestActivity", "开始配对"); 
					boolean flag = (boolean) method.invoke(device);
					if(flag){
						Toast.makeText(getApplication(), "配对了？？~", Toast.LENGTH_SHORT).show();
					}
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//				}
//			}
		}
	}
	public void connectDevice(){
				if(device.getBondState() == BluetoothDevice.BOND_NONE){
					try {
						Method method = device.getClass().getMethod("createBond");
						Log.d("TAG", "开始配对"); 
						boolean flag = (boolean) method.invoke(device);
						if(flag){
							Toast.makeText(getApplication(), "配对了？？~", Toast.LENGTH_SHORT).show();
						}
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
}
