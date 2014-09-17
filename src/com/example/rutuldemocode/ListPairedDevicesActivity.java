package com.example.rutuldemocode;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity shows a list of paired devices.
 * @author Rutul Kotak
 *
 */
public class ListPairedDevicesActivity extends Activity {

	private BluetoothAdapter mBluetoothAdapter;
	
	private TextView mTitle;
	private ListView mListPairedDevices;
	private View mEmpty;
	
	private ArrayAdapter<String> mListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth_devices);
		initUI();
		searchPairedDevices();
	}
	
	@Override
	public void onContentChanged() {
	    super.onContentChanged();
	    
	    mListPairedDevices = (ListView) findViewById(R.id.listPairedDevices);
	    mEmpty = findViewById(R.id.empty);
	    mListPairedDevices.setEmptyView(mEmpty);
	}

	/**
	 * Initialize UI element.
	 */
	private void initUI() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		mListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		mListPairedDevices.setAdapter(mListAdapter);
		
		mTitle = (TextView) findViewById(R.id.textTitle);
		mTitle.setText(R.string.lable_list_paired_devices);
	}

	/**
	 * BluetoothAdapter's getBondedDevices() method will return all devices that are paired.
	 * Iterate one by one and add them to list.
	 */
	private void searchPairedDevices() {
		if(mBluetoothAdapter.isEnabled()) {
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices(); 
			for(BluetoothDevice device : pairedDevices)
				mListAdapter.add(device.getName()+ "\n" + device.getAddress());
		} else
			showToast("Bluetooth is OFF !!");
	}
	
	/**
	 * Show toast message.
	 */
	public void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}