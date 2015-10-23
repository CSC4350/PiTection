package com.example.pitectors.pitection;
/**
 * This will be the main screen for the app once the user is logged in,
 * will display system status, show security point errors, and have a settings option
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;


public class MainScreen extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    Button logoutBtn;
    ListView deviceList;
    List<UserDeviceStatus> devicesToList;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        //Get buttons and input fields
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
       username = (EditText) findViewById(R.id.usernameField);
       password = (EditText) findViewById(R.id.passwordField);
       deviceList = (ListView) findViewById(R.id.listView);

        logoutBtn.setOnClickListener(this);


        if(isOnline()){
            requestData("http://robertnice.altervista.org/getDeviceData.php");
        }
        else{
            Toast.makeText(this,"Network isn't available",Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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

    //Takes user to log in screen
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logoutBtn:
                Intent regIntent = new Intent(this,LoginActivity.class);
                startActivity(regIntent);

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
    //Adds items to listView
    private void updateDisplay() {
        if(devicesToList != null){
            ArrayList<String> deviceNames = new ArrayList<>();
            for(UserDeviceStatus devices: devicesToList) {

                    deviceNames.add(devices.getDeviceName());


            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceNames);
            deviceList.setAdapter(adapter);
        }
        else{
            Toast.makeText(this,"Unable to connect to devices", Toast.LENGTH_SHORT).show();
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
        //on the AsyncTask<> data parameter type
        @Override
        protected void onPostExecute(String s) {
            try {

                devicesToList = JsonParser.parseDeviceFeed(s);
                updateDisplay();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
