package com.example.pitectors.pitection;

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Rob on 11/29/2015.
 */
public class UpdateSystemLog {
	ArrayList<Devices> devicesToList;
	MainScreen main = new MainScreen();
	Object service;
	public void UpdateLog(String userID, String status, Context baseContext, Object systemService){
		service = systemService;
		if (isOnline()) {
			requestData("http://robertnice.altervista.org/armSystem.php?system_id=1&status=" + status +
					"&user_id=" + userID);
			Toast.makeText(baseContext, "System disarmed", Toast.LENGTH_LONG).show();
			main.startService(new Intent(baseContext, CheckDeviceService.class));
		} else {
			Toast.makeText(baseContext, "Network isn't available", Toast.LENGTH_SHORT).show();
		}
	}


	//Checks network connectivity, returns true or false
	protected boolean isOnline(){
		ConnectivityManager cm = (ConnectivityManager)service;
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}


	//Thread pool executer allows multiple tasks in parallel
	private void requestData( String uri) {
		//Begins Async task for HTTP request
		MyTask task = new MyTask();
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
	}

	//Class to instantiate the Async Task class for running
	//Http Requests in the background
	private class MyTask extends AsyncTask<String, String, String>{
		//This method has access to the main thread
		//and runs before doInBackground
		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(String... params) {
			//Gets the content from the URL passed to the
			//requestData method
			String content = HttpManager.getData(params[0]);
			return content;
		}

		//This method receives a result, depending
		//on the RunTasks<> data parameter type
		@Override
		protected void onPostExecute(String s) {



		}
	}
}