package com.example.pitectors.pitection;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rob on 9/27/2015.
 * The purpose of this class is to provide two methods of parsing JSON data obtained from the background tasks
 * running from both the Login Activity and the MainScreen activity. Both methods parse the content as JSON arrays
 * then creates User or Device objects, the objects are then used for validating data or obtaining device information, name and current
 * status which is updated by the Raspberry Pi.
 */
public class JsonParser {

	public static  List<User> parseFeed(String content)  {
		try {
			JSONArray ja = new JSONArray(content);
			List<User> userList = new ArrayList<>();

			for (int i = 0; i < ja.length(); i++) {
				JSONObject obj = ja.getJSONObject(i);
				User user = new User();


				user.setUsername(obj.getString("username"));
				user.setPassword(obj.getString("password"));






				userList.add(user);

			}
			return userList;

		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	//Takes content front background task, parses as JSON data for devices
	public static  List<UserDeviceStatus> parseDeviceFeed(String content)  {
		try {
			JSONArray ja = new JSONArray(content);
			//Creating a list of UserDeviceStatus objects
			List<UserDeviceStatus> devices = new ArrayList<>();

			for (int i = 0; i < ja.length(); i++) {
				JSONObject obj = ja.getJSONObject(i);
				UserDeviceStatus devicesListed = new UserDeviceStatus();
				//Creating objects from the JSON data posted in the provided URI
				devicesListed.setDeviceName(obj.getString("device_name"));
				devicesListed.setStatus(obj.getString("device_status"));





				devices.add(devicesListed);
			}
			return devices;
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
}
