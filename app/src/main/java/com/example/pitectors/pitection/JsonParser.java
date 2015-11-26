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
	public static  List<Devices> parseDeviceFeed(String content)  {
		try {
			JSONArray ja = new JSONArray(content);
			//Creating a list of UserDeviceStatus objects
			List<Devices> devices = new ArrayList<>();

			for (int i = 0; i < ja.length(); i++) {
				JSONObject obj = ja.getJSONObject(i);
				Devices devicesListed = new Devices();
				//Creating objects from the JSON data posted in the provided URI
				devicesListed.setDeviceName(obj.getString("device_name"));
				devicesListed.setDeviceStatus(obj.getString("device_status"));
				devicesListed.setDeviceType(obj.getString("device_type"));






				devices.add(devicesListed);
			}
			return devices;
		}catch(JSONException e){
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
				eventList.setEventDevice(obj.getString("device_name"));
				eventList.setEventDate(obj.getString("event_date"));
				eventList.setUser(obj.getString("user"));
				//Check the the status and the device type and set the
				//event status accordingly
				if(obj.getString("event_status").equals("0") && obj.getString("device_type").equals("Door")){
					eventList.setEventStatus("Door opened");
				}
				else if(obj.getString("event_status").equals("1") && obj.getString("device_type").equals("Motion Sensor")) {
					eventList.setEventStatus("Motion detected");
				}
				else if(obj.getString("event_status").equals("1") && obj.getString("device_type").equals("Door")){
					eventList.setEventStatus("Door closed");
				}






				events.add(eventList);
			}
			return events;
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
}
