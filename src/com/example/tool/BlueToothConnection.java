package com.example.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BlueToothConnection extends Activity {
	private BluetoothAdapter bluetoothAdapter;
	private TextView tvDevices;
	private Button btSearch;
	private BluetoothServerSocket serverSocket;
	private BluetoothSocket clientSocket;
//	private InputStream is;
	private OutputStream os;
	private BluetoothDevice device;
	
	private AcceptThread acceptThread;
	private final String BLUETOOTH_ADDRESS = "24:FD:52:21:85:37";
	private final String BLUETOOTH_NAME = "DESKTOP-RL2Q8QA";
	private final UUID MY_UUID = UUID.fromString("00001104-0000-1000-8000-00805F9B34FB");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.serach);
		initViews();
		
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//得到本地默认的bluetoothadapter
		//提示是否打开蓝牙
		Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(enableIntent, 1);
		Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();//得到已配对的设备
		if(pairedDevices.size()>0){
			for(BluetoothDevice device:pairedDevices){
				tvDevices.append(device.getName()+":"+device.getAddress()+"\n");
			}
		}
		//注册用于已搜索到的蓝牙设备的Receiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);//�ҵ��豸�Ĺ㲥
		this.registerReceiver(receiver, filter);
		//注册搜索完成时的Receiver
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//ȫ��������
		this.registerReceiver(receiver, filter); 
		
//		acceptThread = new AcceptThread();
//		acceptThread.start();
	}
	public void initViews(){
		tvDevices = (TextView) findViewById(R.id.search_bluetooth);
		btSearch = (Button) findViewById(R.id.search);
		btSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clickSearch();
			}
		});
	}
	public void clickSearch(){
		setProgressBarIndeterminateVisibility(true);
		Toast.makeText(getApplication(), "正在搜索~", Toast.LENGTH_SHORT).show();
		if(bluetoothAdapter.isDiscovering()){
			bluetoothAdapter.cancelDiscovery();
		}
		bluetoothAdapter.startDiscovery();
	}
	//广播接收器
	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action)){
				//获得已经搜索到的蓝牙设备
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				//搜索到的设备不是已绑定的蓝牙设备
				if(device.getBondState() != BluetoothDevice.BOND_BONDED){
					//若是指定蓝牙设备
					if(device.getAddress().equals(BLUETOOTH_ADDRESS)){
						if(clientSocket==null){
							try {
								//通过UUID连接蓝牙设备
								clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
								//开始连接蓝牙设备
								clientSocket.connect();
								//获得向服务器发送数据的对象
								os = clientSocket.getOutputStream();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(os != null){
								try {
									os.write("发送信息~".getBytes("utf-8"));
									Toast.makeText(getApplication(), "发送信息成功~", Toast.LENGTH_SHORT).show();
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else{
								Toast.makeText(getApplication(), "发送信息失败~", Toast.LENGTH_SHORT).show();
							}
						}
						tvDevices.append(device.getName()+":"+device.getAddress()+"\n");
					}
				}
			}
			//搜索完成
			else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
				setProgressBarVisibility(false);
				Toast.makeText(getApplication(), "搜索完成~", Toast.LENGTH_SHORT).show();
			}
		}
	};
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
	};
	private class AcceptThread extends Thread{
		private BluetoothServerSocket serverSocket;
		private BluetoothSocket socket;
		private InputStream is;
		public AcceptThread() {
			// TODO Auto-generated constructor stub
			//创建serversocket对象
			try {
				serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(BLUETOOTH_NAME, MY_UUID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void run(){
			try {
				socket = serverSocket.accept();
				is = socket.getInputStream();
				os = socket.getOutputStream();
				while(true){
					byte[] buffer = new byte[128];
					int count = is.read();
					Message msg = new Message();
					msg.obj = new String(buffer, 0, count, "utf-8");
					handler.sendMessage(msg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
