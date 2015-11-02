package com.example.tool;

import java.util.ArrayList;

import com.example.healthcheckup.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
@SuppressLint("NewApi")
public class BlueTooth extends Activity{
	private BluetoothAdapter mBluetoothAdapter;
	
	private Handler mHandler;
	private boolean mScanning;
	private TextView tvDevices;
	
    private static final int REQUEST_ENABLE_BT = 1;
	private static final long SCAN_PERIOD = 30000;


    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

	    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.serach);
		mHandler = new Handler();
		
		tvDevices = (TextView) findViewById(R.id.search_bluetooth);

		prepare();
        scanLeDevice(true);
        Toast.makeText(getApplication(), "scaning~", Toast.LENGTH_LONG).show();
	}
    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
    }
	@SuppressLint("NewApi")
	public void prepare(){
		//准备BLE
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();
        // 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
        // 检查设备上是否支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        //打开蓝牙
		if(mBluetoothAdapter==null||!mBluetoothAdapter.isEnabled()){
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
	        }
	}
    // Device scan callback.
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		
		@Override
		public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {
				public void run() {
					Log.e("find",device.getName());
					tvDevices.append(device.getName()+":"+device.getAddress()+"\n");
			        Toast.makeText(getApplication(), "successful~", Toast.LENGTH_LONG).show();
				}
			});
		}
	};
   private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);
          /*.
                        开启扫描操作然后执行回调操作
                     bleAdapter.startLeScan(uuid[], leScanCallback);此处可指定外围设备的uuid并回调
             
*/
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }
}
