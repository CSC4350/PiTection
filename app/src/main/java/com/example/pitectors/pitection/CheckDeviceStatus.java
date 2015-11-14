package com.example.pitectors.pitection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnice01 on 11/7/2015.
 * The intent for this class is to get the current status for devices when called and return which device(s)
 * are currently opened. This will be used to notify the user when trying to arm the system if
 * there are any device(s) that needs attention and alert the user of any problems when the system is armed.
 */
public class CheckDeviceStatus {

    ArrayList<String> openedDevices;
    public  ArrayList<String> getCurrentStatus(List<UserDeviceStatus> devicesToList) {
        try {
            //Create a new arraylist to hold device(s) of status 0, opened
            openedDevices = new ArrayList<>();

            //Loop through the current devices, add them to openedDevices array if
            //any are status 0
            for (UserDeviceStatus device : devicesToList) {
                if (device.getStatus().equals("1")) {
                    openedDevices.add(device.getDeviceName());

                }

            }
        }
            catch(Exception ex){
                ex.printStackTrace();
            }


            //Return the arraylist of devices, later the arraylist will be checked
            //to see if it is null or not, if null then no problems were found
            // if not null then display the names of what devices have problems
            return openedDevices;



    }



}
