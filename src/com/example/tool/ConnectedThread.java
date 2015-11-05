package com.example.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ConnectedThread extends Thread {
	private final BluetoothSocket mSocket;
	private final InputStream mInStream;
	private final OutputStream mOutStream;
	public ConnectedThread(BluetoothSocket socket) {
		mSocket = socket;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;
		try {
		tmpIn = socket.getInputStream();
		tmpOut = socket.getOutputStream();
		} catch (IOException e) { }
		mInStream = tmpIn;
		mOutStream = tmpOut;
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg){
			super.handleMessage(msg);
		}
	};
	public void run() {
		byte[] buffer = new byte[1024]; 
		int bytes; 
		while (true) {
			Log.d("read","run");
			try {
				bytes = mInStream.read(buffer);
				String str = new String(buffer,"utf-8");
				str = str.substring(0,bytes);
				Log.d("read",str);
				Message msg = new Message();
				msg.obj = str;
				handler.sendMessage(msg);
				} catch (IOException e) {
					Log.d("miss","no data");
					break;
				}
			}
		}
	public void write(byte[] bytes) {
		try {
		mOutStream.write(bytes);
		} catch (IOException e) {
			
		}
	}
	public void cancel() {
		try {
			mSocket.close();
		} catch (IOException e) {
			
		}
		}
	}
