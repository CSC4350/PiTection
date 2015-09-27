package com.example.pitectors.pitection;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rob on 9/27/2015.
 */
public class JsonParser {

	public static  List<User> parseFeed(String content) throws JSONException {

		JSONArray ja = new JSONArray(content);
		List<User> userList = new ArrayList<>();

		for(int i = 0; i < ja.length(); i++) {
			JSONObject obj = ja.getJSONObject(i);
			User user = new User();

			user.getUsername(obj.getString("name"));
			user.getPassword(obj.getString("pass"));

			userList.add(user);
		}
		return userList;
	}
}
