package com.example.pitectors.pitection;
/**
 * This will be the main screen for the app once the user is logged in,
 * will display system status, show security point errors, and have a settings option
 */

import android.content.Context;
import android.content.Intent;

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
    CheckDeviceStatus stat;
    ArrayList<String> devicesWithProblems;
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
                stat = new CheckDeviceStatus();
                devicesWithProblems = new ArrayList<>();
                devicesWithProblems = stat.getCurrentStatus();

                if(devicesWithProblems.isEmpty()){
                    Toast.makeText(this, "System is armed", Toast.LENGTH_SHORT).show();
                }
                else if(!devicesWithProblems.isEmpty()){
                    for(String devices: devicesWithProblems) {
                        Toast.makeText(this, "Please check: " + devices + "\n", Toast.LENGTH_SHORT).show();
                    }
                }
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

    public  void systemArming(ArrayList<String> deviceList) {
        //get the app's context through the context class created for static methods/classes
        Context context = ContextClass.getContext();

        //If the returned array is not empty, then a device with a problem was found
        //alert user of issue, else notify user that system is armed
        if (!deviceList.isEmpty()) {
            for (String device : deviceList) {
                Toast.makeText(context , "Please check " + device + "for any problems", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(context,"System is armed", Toast.LENGTH_SHORT);
        }
    }




}
