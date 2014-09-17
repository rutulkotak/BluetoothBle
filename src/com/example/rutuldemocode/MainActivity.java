package com.example.rutuldemocode;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * This is entry point class of application.
 * This gives options to turn on / off bluetooth.
 * Search for paired devices
 * Discover new devices
 * @author Rutul Kotak
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button mBtnOn;
	private Button mBtnOff;
	private Button mBtnPairedDevices;
	private Button mBtnSearchNewDevices;

	private BluetoothAdapter mBluetoothAdapter;

	private static final int REQUEST_ENABLE_BT = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
		CheckBluetoothState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnOn:
			setBluetoothOn();
			break;
		case R.id.btnSetOff:
			setBluetoothOff();
			break;
		case R.id.btnPairedDevices:
				Intent pairedDevices = new Intent(this, ListPairedDevicesActivity.class);
				startActivity(pairedDevices);
			break;
		case R.id.btnSearchNewDevices:
			Intent availableDevices = new Intent(this, ListAvailableDevicesActivity.class);
			startActivity(availableDevices);
			break;
		}
	}

	/**
	 * Method used to set Bluetooth ON.
	 */
	private void setBluetoothOn() {
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	}
	
	/**
	 * Method used to set Bluetooth OFF.
	 */
	private void setBluetoothOff() {
		mBluetoothAdapter.disable();
		configureBluetoothOff();
	}

	/**
	 * Method to be called when get result from anothor activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ENABLE_BT) {
			configureBluetoothOn();
		}
	}

	/**
	 * Initialize UI element.
	 */
	private void initUI() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		mBtnOn = (Button) findViewById(R.id.btnOn);
		mBtnOn.setOnClickListener(this);

		mBtnOff = (Button) findViewById(R.id.btnSetOff);
		mBtnOff.setOnClickListener(this);

		mBtnPairedDevices = (Button) findViewById(R.id.btnPairedDevices);
		mBtnPairedDevices.setOnClickListener(this);

		mBtnSearchNewDevices = (Button) findViewById(R.id.btnSearchNewDevices);
		mBtnSearchNewDevices.setOnClickListener(this);
	}

	/**
	 * BluetoothAdapter s used as a singleton to access the Bluetooth radio on
	 * the Android device. If device does not support Bluetooth, the returned
	 * value will be equal to "null". And if null, then set all button to not
	 * clickable.
	 */
	private void CheckBluetoothState() {
		if (mBluetoothAdapter == null) {
			showTost("Bluetooth NOT supported.");
			mBtnOn.setEnabled(false);
			mBtnOff.setEnabled(false);
			mBtnPairedDevices.setEnabled(false);
			mBtnSearchNewDevices.setEnabled(false);
		} else if (mBluetoothAdapter.isEnabled()) {
			configureBluetoothOn();
		} else {
			configureBluetoothOff();
		}
	}

	/**
	 * Set enable true / false buttons when Bluetooth is on.
	 */
	private void configureBluetoothOn() {
		showTost("Bluetooth is ON !!");
		mBtnOn.setEnabled(false);
		mBtnOff.setEnabled(true);
		mBtnPairedDevices.setEnabled(true);
		mBtnSearchNewDevices.setEnabled(true);
	}
	
	/**
	 * Set enable true / false buttons when Bluetooth is off.
	 */
	private void configureBluetoothOff() {
		showTost("Bluetooth is OFF !!");
		mBtnOn.setEnabled(true);
		mBtnOff.setEnabled(false);
		mBtnPairedDevices.setEnabled(false);
		mBtnSearchNewDevices.setEnabled(false);
	}
	
	/**
	 * Show toast message.
	 */
	private void showTost(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}