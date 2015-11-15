package com.example.tool;

import java.io.IOException;
import java.util.Set;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
/**
 * 
 * @author nie2ni
 * @param context
 *
 */
public  class BlueTooth {
	private Context context;
	private BluetoothAdapter mBluetoothAdapter;
	private static BluetoothDevice device;
	private BluetoothSocket btSocket;
	private ConnectedThread connectedThread;
	private Handler handler;
	private String result = null;

	public BlueTooth(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
		openBluetooth();
		registReceiver();
		search();
	}
	public void openBluetooth(){
		//打开蓝牙
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//得到本地默认的bluetoothadapter
		//提示是否打开蓝牙
		if(mBluetoothAdapter!=null){
			if(!mBluetoothAdapter.isEnabled()){
//				Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//				startActivityForResult(enableIntent, 1);
				mBluetoothAdapter.enable();
			}
		}
	}
	public void registReceiver(){
		//注册用于已搜索到的蓝牙设备的Receiver
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);//发现广播
		filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//设备连接状态改变
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//		filter.addAction(android.bluetooth.BluetoothDevice.ACTION_PAIRING_REQUEST);//自动匹配
		context.registerReceiver(receiver, filter);		
	}
	//搜索蓝牙
	@SuppressLint("NewApi")
	public void search(){
		//寻找配对蓝牙
		if(mBluetoothAdapter.isDiscovering()){
			mBluetoothAdapter.cancelDiscovery();
		}
		//开始搜索
		mBluetoothAdapter.startDiscovery();
		Log.d("~~", "开始搜索");
//		 }
	}
	
	//广播接收器（搜索接收函数）
	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@SuppressLint("NewApi")
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
			if (pairedDevices.size() > 0) {
			     for (BluetoothDevice btdevice : pairedDevices) {
			 		if(btdevice.getUuids().equals(GLOBAL.MY_UUID)){//是确定蓝牙
			 			mBluetoothAdapter.cancelDiscovery();//取消搜索
//			 			device = btdevice;//获取蓝牙
						Toast.makeText(context.getApplicationContext(), "连接成功", Toast.LENGTH_LONG).show();
						break;
			 		}
			     }
			  }
			if(BluetoothDevice.ACTION_FOUND.equals(action)){
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if(device.getAddress().equals(GLOBAL.BLUETOOTH_ADDRESS)){
					Toast.makeText(context.getApplicationContext(), "搜索完成~", Toast.LENGTH_SHORT).show();
		 			mBluetoothAdapter.cancelDiscovery();//取消搜索
		 			connectDevice();
				}
			}
		}
	};
	public void connectDevice(){
		try {
			btSocket = device.createRfcommSocketToServiceRecord(GLOBAL.MY_UUID);
			Log.d("TAG", "连接成功");
			Toast.makeText(context.getApplicationContext(), "连接成功", Toast.LENGTH_LONG).show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Log.d("TAG", "连接失败");
		}
		try {
			btSocket.connect();
			Log.e("TAG", "配对成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("TAG", "配对失败");
		}
		connectedThread = new ConnectedThread(btSocket, handler);
		connectedThread.start();
	
	}
	public static BluetoothDevice getDevice() {
		return device;
	}
//	public abstract void onSuccess(String result);
}
