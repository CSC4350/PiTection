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
    ImageButton logoutBtn, logsBtn, deviceBtn,armBtn, settingsBtn, disarmBtn;
    Button btnConfirm;
    TextView armText;
    CheckDeviceStatus stat;
    ArrayList<String> devicesWithProblems;
    List<Devices> devicesToList;
    CheckDeviceService checkDeviceService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        //Get buttons and textviews
        logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        logsBtn = (ImageButton) findViewById(R.id.logsBtn);
        deviceBtn = (ImageButton) findViewById(R.id.deviceBtn);
        armBtn = (ImageButton) findViewById(R.id.armBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);


        disarmBtn = (ImageButton) findViewById(R.id.disarmBtn);
        logoutBtn.setOnClickListener(this);
        logsBtn.setOnClickListener(this);
        deviceBtn.setOnClickListener(this);
        armBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);

        armText = (TextView) findViewById(R.id.armTxt);







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

    //Switch statement to handle all onclick functions
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logoutBtn:
                //Begins login activity if clicked
                Intent regIntent = new Intent(this,LoginActivity.class);
                startActivity(regIntent);
                break;
            case R.id.deviceBtn:
                //Begin the device activity on click to display the user's devices
                Intent deviceIntent = new Intent(this,DeviceActivity.class);
                startActivity(deviceIntent);
                break;
            case R.id.armBtn:
                //When armed button is clicked, begin checking network connectivity
                //request data from given URL
                    if (isOnline()) {
                        requestData("http://robertnice.altervista.org/getDeviceData.php");
                    } else {
                        Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
                    }
                break;
            case R.id.settingsBtn:
                //eventually start settings Activity
                Toast.makeText(this,"Starting settings activity",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logsBtn:
                //Build intent to start the logs activity on button action
                Intent logsIntent = new Intent(this,EventLogsActivity.class);
                startActivity(logsIntent);
                break;


        }
    }


    //Checks network connectivity, returns true or false
    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
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
            try {

                devicesToList = new ArrayList();
                devicesToList = JsonParser.parseDeviceFeed(s);
                updateArmProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    //This method instantiates the CheckDeviceStatus class
    //and passes the list of Device objects
    //the method in the CheckDeviceStatus class
    //then returns a String list with any device
    //names that are currently opened or are detecting motion
    //which is status 0 in the database
    private void updateArmProgress() {
        if(devicesToList != null){
            CheckDeviceStatus checkStatus = new CheckDeviceStatus();
            devicesWithProblems = new ArrayList<>();
           devicesWithProblems = checkStatus.getCurrentStatus(devicesToList);
            //if the list returned is no empty alert the user to what
            //devices need attention before alarm can be set
            if(!(devicesWithProblems).isEmpty()){
                for(String items: devicesWithProblems) {
                    Toast.makeText(this, "Please check " + items + " before alarm can be set", Toast.LENGTH_LONG).show();
                }

            }
            else{//If the system is ready to be armed
                //show confirm button, confirm button then starts the background service
                //to check the database every 5 seconds for status changes
                armBtn.setVisibility(View.GONE);
                btnConfirm.setVisibility(View.VISIBLE);
            }
        } else{//Network error
            Toast.makeText(this , "No devices to check", Toast.LENGTH_SHORT).show();
        }
    }

    //onClick method that runs when confirm button is pressed
    //shows the disarm button
    public void startService(View view){
       btnConfirm.setVisibility(View.GONE);
        disarmBtn.setVisibility(View.VISIBLE);
        armText.setText("Disarm System");

        Toast.makeText(this,"System is armed", Toast.LENGTH_LONG).show();

        startService(new Intent(getBaseContext(), CheckDeviceService.class));
    }

    public void stopService(View view){
        disarmBtn.setVisibility(View.GONE);
        armBtn.setVisibility(View.VISIBLE);
        armText.setText("Arm System");

        stopService(new Intent(getBaseContext(), CheckDeviceService.class));
    }



}
