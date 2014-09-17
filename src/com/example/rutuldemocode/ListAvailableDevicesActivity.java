package com.example.rutuldemocode;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This activity shows a list of available devices.
 * @author Rutul Kotak
 *
 */
public class ListAvailableDevicesActivity extends Activity {

	private BluetoothAdapter mBluetoothAdapter;

	private TextView mTitle;
	private ListView mListAvailableDevices;
	private View mEmpty;
	
	private ArrayAdapter<String> mListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth_devices);
		initUI();
		searchAvailableDevices();
	}
	
	@Override
	public void onContentChanged() {
	    super.onContentChanged();
	    
	    mListAvailableDevices = (ListView) findViewById(R.id.listPairedDevices);
	    mEmpty = findViewById(R.id.empty);
	    mListAvailableDevices.setEmptyView(mEmpty);
	}
	
	/**
	 * Unregister BroadcastReceiver.
	 */
	@Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

	/**
	 * Initialize UI element.
	 */
	private void initUI() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mBluetoothAdapter.startLeScan(new LeScanCallback() {
			
			@Override
			public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
				// TODO Auto-generated method stub
				System.out.println(device.getAddress());
			}
		});
		
		
		mListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		mListAvailableDevices.setAdapter(mListAdapter);

		mTitle = (TextView) findViewById(R.id.textTitle);
		mTitle.setText(R.string.lable_list_available_devices);
	}

	/**
	 * Registered BroadcastReceiver for the ACTION_FOUND Intent in order to receive information about each device discovered.
	 * 
	 */
	private void searchAvailableDevices() {
		registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
		mBluetoothAdapter.startDiscovery();
	}

	/**
	 * Class to receive Broadcast for device found in device's range. 
	 */
	final BroadcastReceiver mReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();

			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				// add the name and the MAC address of the object to the arrayAdapter
				mListAdapter.add(device.getName() + "\n" + device.getAddress());
				mListAdapter.notifyDataSetChanged();
			}
		}
	};
}