package com.example.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ConnectedThread extends Thread {
	private final BluetoothSocket mSocket;
	private final InputStream mInStream;
	private final OutputStream mOutStream;
	private Handler handler;
	private String str;
	public ConnectedThread(BluetoothSocket socket, Handler handler) {
		mSocket = socket;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;
		try {
			tmpIn = socket.getInputStream();
			tmpOut = socket.getOutputStream();
			Log.d("read","get socket");
		} catch (IOException e) { }
		mInStream = tmpIn;
		mOutStream = tmpOut;
		this.handler = handler;
	}
	public void run() {
		byte[] buffer = new byte[1024]; 
		int bytes; 
		String data = "";
		boolean flag = true;
		while (flag) {
			Log.d("read","begin run");
			try {
					bytes = mInStream.read(buffer);
					str = new String(buffer, "utf-8");
					str = str.substring(0, bytes);
					Log.e("str",str);
				//	data += str;
				//	Log.e("data",data);
				//	Log.d("result", result);
					Message msg = new Message();
					Bundle b = new Bundle();
					b.putString("str", str);
					msg.setData(b);
//					msg.obj = str;
//					msg.what = 1;
					handler.sendMessage(msg);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(!flag){
					Log.d("miss","data fail,close connect");
					cancel();
				}
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
