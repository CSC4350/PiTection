package com.example.pitectors.pitection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SystemLogActivity extends AppCompatActivity {
	ListView eventList;
	List<SystemLog> eventsToList;
	SystemLogAdapter adapter;
	GetStoredIP getIP;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_logs);
		//Call method to get stored IP address
		getIP = new GetStoredIP();
		String IP = getIP.readInURL();
		if(isOnline()){
			requestData(IP + "/System_log.php");
		}
		else{
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
		}

		eventList = (ListView) findViewById(R.id.listView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_event_logs, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	//Adds items to listView
	private void updateDisplay() {
		if(eventsToList != null){
			ArrayList<SystemLog> deviceListView = new ArrayList<>();
			for(SystemLog logs: eventsToList){
				deviceListView.add(logs);

			}

			adapter = new SystemLogAdapter(this, deviceListView);
			eventList.setAdapter(adapter);
		}
		else{
			Toast.makeText(this, "Unable to connect to devices", Toast.LENGTH_SHORT).show();
		}
	}

	//Checks network connectivity
	protected boolean isOnline(){
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isConnectedOrConnecting()){
			return true;
		}
		else{
			return false;
		}
	}


	//Thread pool executer allows multiple tasks in parallel
	private void requestData( String uri) {
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
			String content = HttpManager.getData(params[0]);
			return content;
		}

		//This method receives a result, depending
		//on the RunTasks<> data parameter type
		@Override
		protected void onPostExecute(String s) {
			try {
				JsonParser parser = new JsonParser();
				eventsToList = parser.parseSystemLogs(s);
				updateDisplay();

			} catch (Exception e) {
				e.printStackTrace();
			}


		}
	}

}
