package com.example.pitectors.pitection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStreamWriter;

public class PreRegistration extends AppCompatActivity {
Button btnSubmit;
	EditText systemKey, systemIP;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_registration);

		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		systemKey = (EditText)findViewById(R.id.systemKey);
		systemIP = (EditText)findViewById(R.id.systemIP);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_pre_registration, menu);
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

	public void submitPreregistration(View view) {
		String url = systemIP.getText().toString();
		storeIP(url);
		GetStoredIP getIP = new GetStoredIP();
		String IP = getIP.readInURL();
		if(isOnline()){

			requestData(IP + "/getSystemStatus.php");
		}
		else{
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
		}

	}

	private void storeIP(String url) {
		try {
			 final  String STORETEXT="storeURL.txt";
			OutputStreamWriter out=

					new OutputStreamWriter(openFileOutput(STORETEXT, 0));

			out.write(url);

			out.close();

			Toast

					.makeText(this, "IP Address has been stored", Toast.LENGTH_LONG)

					.show();

		}

		catch (Throwable t) {

			Toast

					.makeText(this, "Exception: "+t.toString(), Toast.LENGTH_LONG)

					.show();

		}
	}

	protected boolean isOnline(){
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	//Thread pool executer allows multiple tasks in parallel
	private void requestData( String uri) {
		LoginTask lTask = new LoginTask();
		lTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
	}

	//Class to instantiate the Async Task class for running
	//Http Requests in the background
	private class LoginTask extends AsyncTask<String, String, String>{
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
				JsonParser parse = new JsonParser();


				String returnedKey = parse.parseSystemKey(s);
				verifyKey(returnedKey);

			} catch (Exception e) {
				e.printStackTrace();
			}


		}
	}

	private void verifyKey(String returnedKey) {
		String enteredKey = systemKey.getText().toString();
		Encrypt encrypt = new Encrypt();
		encrypt.setPassword(enteredKey);
		encrypt.encryptPassword();
		String encryptedPassword = encrypt.getGeneratedPassword();
		if(returnedKey.equals(encryptedPassword)){
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
		}
		else{
			Toast.makeText(this,"Invalid key", Toast.LENGTH_SHORT).show();
		}
	}
}
