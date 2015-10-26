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
    ImageButton logoutBtn, logsBtn, deviceBtn,armBtn, settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        //Get buttons
        logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        logsBtn = (ImageButton) findViewById(R.id.logsBtn);
        deviceBtn = (ImageButton) findViewById(R.id.deviceBtn);
        armBtn = (ImageButton) findViewById(R.id.armBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);



        logoutBtn.setOnClickListener(this);
        logsBtn.setOnClickListener(this);
        deviceBtn.setOnClickListener(this);
        armBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);


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
                break;
            case R.id.deviceBtn:
                Intent deviceIntent = new Intent(this,DeviceActivity.class);
                startActivity(deviceIntent);
                break;
            case R.id.armBtn:
                Toast.makeText(this,"System armed",Toast.LENGTH_SHORT).show();
                //Do nothing for now
                break;
            case R.id.settingsBtn:
                //eventually start settings Activity
                Toast.makeText(this,"Starting settings activity",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logsBtn:
                //eventually start logs activity
                Toast.makeText(this,"Starting logs activity",Toast.LENGTH_SHORT).show();
                break;


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
        //on the AsyncTask<> data parameter type
        @Override
        protected void onPostExecute(String s) {
            try {


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
