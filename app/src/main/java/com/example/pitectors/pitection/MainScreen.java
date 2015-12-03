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
   static ImageButton logoutBtn, logsBtn, deviceBtn,armBtn, systemLogBtn, disarmBtn;
   static Button btnConfirm,btnStartSystemService,btnStopSystemService;
    static TextView armText;
    CheckDeviceStatus stat;
    ArrayList<String> devicesWithProblems;
    List<Devices> devicesToList;
    CheckDeviceService checkDeviceService;
    GetStoredIP getIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        //Get buttons and textviews
        logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        logsBtn = (ImageButton) findViewById(R.id.logsBtn);
        deviceBtn = (ImageButton) findViewById(R.id.deviceBtn);
        armBtn = (ImageButton) findViewById(R.id.armBtn);
        systemLogBtn = (ImageButton) findViewById(R.id.systemLogBtn);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnStartSystemService= (Button) findViewById(R.id.startSystemService);
        btnStopSystemService = (Button) findViewById(R.id.stopSystemService);


        disarmBtn = (ImageButton) findViewById(R.id.disarmBtn);
        logoutBtn.setOnClickListener(this);
        logsBtn.setOnClickListener(this);
        deviceBtn.setOnClickListener(this);
        armBtn.setOnClickListener(this);
        systemLogBtn.setOnClickListener(this);

        armText = (TextView) findViewById(R.id.armTxt);

        //Start checking the system for anyone other than current user arming system
        btnStartSystemService.performClick();




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
                //Call method to get stored IP address
                getIP = new GetStoredIP();
                String IP = getIP.readInURL();
                    if (isOnline()) {
                        requestData(IP + "/getDeviceData.php", "Request");
                    } else {
                        Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
                    }
                break;
            case R.id.systemLogBtn:
                //eventually start settings Activity
                Intent systemLogIntent = new Intent(this, SystemLogActivity.class);
                startActivity(systemLogIntent);
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
    private void requestData(String uri, String type) {
        //Begins Async task for HTTP request
        MyTask task = new MyTask();
        task.execute(uri, type);
    }



    //Class to instantiate the Async Task class for running
    //Http Requests in the background
    private class MyTask extends AsyncTask<String, String, String[]>{
        //This method has access to the main thread
        //and runs before doInBackground
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String[] doInBackground(String... params) {
            //Gets the content from the URL passed to the
            //requestData method
            String[] content = new String [2];
            content[0] = HttpManager.getData(params[0]);
            content[1] = params[1];
            return content;
        }

        //This method receives a result, depending
        //on the RunTasks<> data parameter type
        @Override
        protected void onPostExecute(String[] result) {
            if(result[1].equals("Request")) {
                try {
                    JsonParser parser = new JsonParser();
                    devicesToList = new ArrayList();
                    devicesToList = parser.parseDeviceFeed(result[0]);
                    updateArmProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(result[1].equals("Send")){
                //Do nothing
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

        //Stop the system checking service once the system is armed
        //to then begin the service to check the devices
        btnStopSystemService.performClick();

        //Get the ID for the current user
        //From the static variable in the JsonParser
        //class where the User object gets created
        //upon logging in to the app
        JsonParser json = new JsonParser();
        String userID = json.user.getUserID();
        //Call method to get stored IP address

        getIP = new GetStoredIP();
        String IP = getIP.readInURL();
        if (isOnline()) {
            requestData(IP + "/armSystem.php?system_id=1&status=1" +
                    "&user_id=" + userID, "Send");
            Toast.makeText(this, "System armed", Toast.LENGTH_LONG).show();
           startService(new Intent(getBaseContext(), CheckDeviceService.class));
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
        }

    }

    public void stopService(View view){
        stopService(new Intent(getBaseContext(), CheckDeviceService.class));
        //Begin service to check for system being armed
        Button btnStartSystemService = (Button)findViewById(R.id.startSystemService);
        btnStartSystemService.performClick();
        disarmBtn.setVisibility(View.GONE);
        armBtn.setVisibility(View.VISIBLE);
        armText.setText("Arm System");

        //Get the ID for the current user
        //From the static variable in the JsonParser
        //class where the User object gets created
        //upon logging in to the app
        JsonParser json = new JsonParser();
        String userID = json.user.getUserID();

        //Call method to get stored IP address
        getIP = new GetStoredIP();
        String IP = getIP.readInURL();
        if (isOnline()) {
            requestData(IP + "/armSystem.php?system_id=1&status=0" +
                    "&user_id=" + userID, "Send");

        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
        }


    }

    //Method to access the perform button click
    //method on the invisible button in the main
    //screen layout that invokes the stopSystemService
    //method to stop the service
    public void performStopSystemService(){
    if(armBtn != null) {
        armBtn.setVisibility(View.GONE);
        btnConfirm.setVisibility(View.VISIBLE);
        btnConfirm.performClick();
    }
        else{
       //do nothing
    }
        //Only reason for this method to be called
        //is if the system is armed manually out of the app
        //so invoke the startService method
        //that btnConfirm onClick property is tied to
        //this will indicate on the app that the system is armed


    }
    //Invoked when service to check doors
    //and motions sensors stops running
    //to show the user that the system is
    //no longer secure, likely due to
    //door being opened or motion sensed
    //while system is armed
    public void performStopDeviceService() {
        disarmBtn.setVisibility(View.GONE);
        armBtn.setVisibility(View.VISIBLE);
        armText.setText("Arm System");

    }

    public void startSystemService(View view){


        startService(new Intent(getBaseContext(), CheckSystemService.class));
    }

    public void stopSystemService(View view){


        stopService(new Intent(getBaseContext(), CheckSystemService.class));
    }




}
