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
 * are currently opened. When looping through the Device list, checks need to be made for the device type and the status
 * The motion detectors will change to a status of 1 when motion is detected and doors switch to status 0 when
 * the contacts are broken.
 * This will be used to notify the user when trying to arm the system if
 * there are any device(s) that needs attention and alert the user of any problems when the system is armed.
 */
public class CheckDeviceStatus {

    ArrayList<String> openedDevices;
    public  ArrayList<String> getCurrentStatus(List<Devices> devicesToList) {
        try {
            //Create a new arraylist to hold device(s) of status 0, opened for doors
            //status 1 for motion detectors
            openedDevices = new ArrayList<>();

            //Loop through the current devices, add them to openedDevices arraylist if
            //any are status 0 for doors or status 1 for motion detectors
            for (Devices device : devicesToList) {
                if (device.getDeviceStatus().equals("1")) {
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
