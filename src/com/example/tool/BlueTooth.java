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
		//������
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//�õ�����Ĭ�ϵ�bluetoothadapter
		//��ʾ�Ƿ������
		if(mBluetoothAdapter!=null){
			if(!mBluetoothAdapter.isEnabled()){
//				Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//				startActivityForResult(enableIntent, 1);
				mBluetoothAdapter.enable();
			}
		}
	}
	public void registReceiver(){
		//ע���������������������豸��Receiver
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);//���ֹ㲥
		filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//�豸����״̬�ı�
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//		filter.addAction(android.bluetooth.BluetoothDevice.ACTION_PAIRING_REQUEST);//�Զ�ƥ��
		context.registerReceiver(receiver, filter);		
	}
	//��������
	@SuppressLint("NewApi")
	public void search(){
		//Ѱ���������
		if(mBluetoothAdapter.isDiscovering()){
			mBluetoothAdapter.cancelDiscovery();
		}
		//��ʼ����
		mBluetoothAdapter.startDiscovery();
		Log.d("~~", "��ʼ����");
//		 }
	}
	
	//�㲥���������������պ�����
	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@SuppressLint("NewApi")
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
			if (pairedDevices.size() > 0) {
			     for (BluetoothDevice btdevice : pairedDevices) {
			 		if(btdevice.getUuids().equals(GLOBAL.MY_UUID)){//��ȷ������
			 			mBluetoothAdapter.cancelDiscovery();//ȡ������
//			 			device = btdevice;//��ȡ����
						Toast.makeText(context.getApplicationContext(), "���ӳɹ�", Toast.LENGTH_LONG).show();
						break;
			 		}
			     }
			  }
			if(BluetoothDevice.ACTION_FOUND.equals(action)){
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if(device.getAddress().equals(GLOBAL.BLUETOOTH_ADDRESS)){
					Toast.makeText(context.getApplicationContext(), "�������~", Toast.LENGTH_SHORT).show();
		 			mBluetoothAdapter.cancelDiscovery();//ȡ������
		 			connectDevice();
				}
			}
		}
	};
	public void connectDevice(){
		try {
			btSocket = device.createRfcommSocketToServiceRecord(GLOBAL.MY_UUID);
			Log.d("TAG", "���ӳɹ�");
			Toast.makeText(context.getApplicationContext(), "���ӳɹ�", Toast.LENGTH_LONG).show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Log.d("TAG", "����ʧ��");
		}
		try {
			btSocket.connect();
			Log.e("TAG", "��Գɹ�");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("TAG", "���ʧ��");
		}
		connectedThread = new ConnectedThread(btSocket, handler);
		connectedThread.start();
	
	}
	public static BluetoothDevice getDevice() {
		return device;
	}
//	public abstract void onSuccess(String result);
}
