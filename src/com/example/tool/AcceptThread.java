package com.example.tool;

import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class AcceptThread extends Thread{
	private BluetoothServerSocket serverSocket;
	private BluetoothSocket socket;
	private InputStream is;
	private OutputStream os;
	public AcceptThread() {
	}
}
