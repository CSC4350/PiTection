package com.example.pitectors.pitection;

import android.widget.Button;

/**
 * Created by Rob on 10/4/2015.
 * This class is intended to create buttons to be added to the Main Screen activities list view.
 * The App will look at the user's stored devices on the Pi's database and create the buttons
 * based on the data received.
 */
public class UserDeviceStatus {
	String status;
	String deviceName;

	public UserDeviceStatus( String deviceName, String status) {
		this.status = status;
		this.deviceName = deviceName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeviceName(){
		return deviceName;
	}

	public void setDeviceName(String name){
		this.deviceName = name;
	}
}

