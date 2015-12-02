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
	static User user;
	public  User parseFeed(String content) {
		try {
			JSONArray ja = new JSONArray(content);
			 user = new User();
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
	public  List<Devices> parseDeviceFeed(String content) {
		try {
			JSONArray ja1 = new JSONArray(content);
			//Creating a list of UserDeviceStatus objects
			List<Devices> devices = new ArrayList<>();

			for (int i = 0; i < ja1.length(); i++) {
				JSONObject obj = ja1.getJSONObject(i);
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

	public  List<Events> parseEventFeed(String content) {
		try {
			JSONArray ja2 = new JSONArray(content);

			//Creating a list of Event objects
			List<Events> events = new ArrayList<>();

			for (int i = 0; i < ja2.length(); i++) {
				JSONObject obj = ja2.getJSONObject(i);
				Events eventList = new Events();
				//Creating objects from the JSON data posted in the provided URI
				eventList.setEventDevice(obj.getString("name"));
				eventList.setEventDate(obj.getString("timestamp"));
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

	public  String parseSystemFeed(String s) throws JSONException {
		JSONArray ja3 = new JSONArray(s);

		String status = "";

		for (int i = 0; i < ja3.length(); i++) {
			JSONObject obj = ja3.getJSONObject(i);

			status = obj.getString("status");
		}
		return status;
	}

	public  List<SystemLog> parseSystemLogs(String content) {
		try {
			JSONArray ja4 = new JSONArray(content);

			//Creating a list of Event objects
			List<SystemLog> logList = new ArrayList<>();

			for (int i = 0; i < ja4.length(); i++) {
				JSONObject obj = ja4.getJSONObject(i);
				SystemLog log = new SystemLog();
				//Creating objects from the JSON data posted in the provided URI
				log.setUsername(obj.getString("username"));
				log.setTimeStamp(obj.getString("timestamp"));
				log.setStatus(obj.getString("status"));
				//log.setSystemName(obj.getString("name"));


				logList.add(log);
			}
			return logList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String parseSystemKey(String content) {
		try {
			JSONArray ja4 = new JSONArray(content);

			String key = "";
			for (int i = 0; i < ja4.length(); i++) {
				JSONObject obj = ja4.getJSONObject(i);
				 key = obj.getString("passphrase");

			}
			return key;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
