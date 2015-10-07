package com.example.pitectors.pitection;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rob on 9/27/2015.
 */
public class JsonParser {

	public static  List<User> parseFeed(String content)  {
		try {
			JSONArray ja = new JSONArray(content);
			List<User> userList = new ArrayList<>();

			for (int i = 0; i < ja.length(); i++) {
				JSONObject obj = ja.getJSONObject(i);
				User user = new User();


				user.setUsername(obj.getString("id"));
				user.setPassword(obj.getString("name"));






				userList.add(user);

			}
			return userList;

		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}

	public static  List<UserDeviceStatus> parseDeviceFeed(String content)  {
		try {
			JSONArray ja = new JSONArray(content);

			List<UserDeviceStatus> devices = new ArrayList<>();

			for (int i = 0; i < ja.length(); i++) {
				JSONObject obj = ja.getJSONObject(i);
				UserDeviceStatus devicesListed = new UserDeviceStatus();

				devicesListed.setDeviceName(obj.getString("name"));





				devices.add(devicesListed);
			}
			return devices;
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
}
