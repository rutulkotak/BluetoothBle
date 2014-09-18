package com.example.rutuldemocode;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This activity will start BLE scan and will show devices that is sending message.
 * @author Rutul Kotak
 *
 */
public class BleActivity extends Activity implements BluetoothAdapter.LeScanCallback {
	
	private TextView mTitle;
	private ListView mListAvailableDevices;
	private View mEmpty;
	private BluetoothAdapter mBluetoothAdapter;
	private ArrayAdapter<String> mListAdapter;
	private final Map<String,BluetoothDevice> mDevices = new HashMap<String, BluetoothDevice>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth_devices);
		initUI();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mBluetoothAdapter.startLeScan(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mBluetoothAdapter.stopLeScan(this);
	}
	
	@Override
	public void onContentChanged() {
	    super.onContentChanged();
	    
	    mListAvailableDevices = (ListView) findViewById(R.id.listPairedDevices);
	    mEmpty = findViewById(R.id.empty);
	    mListAvailableDevices.setEmptyView(mEmpty);
	}
	
	/**
	 * Initialize UI element.
	 */
	private void initUI() {
		initActionBar();
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		mListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		mListAvailableDevices.setAdapter(mListAdapter);

		mTitle = (TextView) findViewById(R.id.textTitle);
		mTitle.setText(R.string.lable_list_ble_devices);
	}
	
	/**
	 * Initialize ActionBar.
	 */
	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(this.getResources().getDrawable(R.drawable.ic_launcher));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action buttons
		switch (item.getItemId()) {
		case android.R.id.home:
            finish();
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * The onLeScan() method is called each time the Bluetooth adapter receives any advertising message 
	 * from a BLE device while it is scanning.
	 */
	@Override
	public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
		
		System.out.println(device.getAddress());
		if (device != null && !mDevices.containsValue(device) && device.getName() != null) {
			mDevices.put(device.getAddress(), device);
			
			runOnUiThread(new Runnable() {

                @Override
                public void run() {
                	mListAdapter.add(device.getName() + "\n" + device.getAddress());
        			mListAdapter.notifyDataSetChanged();
                }
            });
		}
	}
}