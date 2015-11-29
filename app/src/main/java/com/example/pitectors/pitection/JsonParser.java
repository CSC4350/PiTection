package com.example.pitectors.pitection;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

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

	public static User parseFeed(String content) {
		try {
			JSONArray ja = new JSONArray(content);
			User user = new User();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject obj = ja.getJSONObject(i);


				user.setUserID(obj.getString("id"));
				user.setUsername(obj.getString("username"));
				user.setPassword(obj.getString("password"));

			}

			return user;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	//Takes content front background task, parses as JSON data for devices
	public static List<Devices> parseDeviceFeed(String content) {
		try {
			JSONArray ja = new JSONArray(content);
			//Creating a list of UserDeviceStatus objects
			List<Devices> devices = new ArrayList<>();

			for (int i = 0; i < ja.length(); i++) {
				JSONObject obj = ja.getJSONObject(i);
				Devices devicesListed = new Devices();
				//Creating objects from the JSON data posted in the provided URI
				devicesListed.setDeviceName(obj.getString("name"));
				devicesListed.setDeviceStatus(obj.getString("status"));



				devices.add(devicesListed);
			}
			return devices;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Events> parseEventFeed(String content) {
		try {
			JSONArray ja = new JSONArray(content);

			//Creating a list of Event objects
			List<Events> events = new ArrayList<>();

			for (int i = 0; i < ja.length(); i++) {
				JSONObject obj = ja.getJSONObject(i);
				Events eventList = new Events();
				//Creating objects from the JSON data posted in the provided URI
				eventList.setEventDevice(obj.getString("username"));
				eventList.setEventDate(obj.getString("timestamp"));
				eventList.setUser(obj.getString("username"));
				eventList.setEventStatus(obj.getString("status"));
				//Check the the status and the device type and set the
				//event status accordingly


				events.add(eventList);
			}
			return events;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String parseSystemFeed(String s) throws JSONException {
		JSONArray ja = new JSONArray(s);

		String status = "";

		for (int i = 0; i < ja.length(); i++) {
			JSONObject obj = ja.getJSONObject(i);
			Events eventList = new Events();
			//Creating objects from the JSON data posted in the provided URI
			status = obj.getString("status");
		}
		return status;
	}
}
