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
public class CheckDeviceStatus extends AppCompatActivity {
    List<UserDeviceStatus> devicesToList;
    ArrayList<String> openedDevices;
    public  ArrayList<String> getCurrentStatus() {
        //get the app's context through the context class created for static methods/classes
        try {
            if (isOnline()) {
                requestData("http://robertnice.altervista.org/getDeviceData.php");
                //Call method to check each device and display which ones have problems if any

            } else {
                Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
            }
            //Loop through the current devices, add them to openedDevices array if
            //any are status 0
            for (UserDeviceStatus device : devicesToList) {
                if (device.getStatus() == "1") {
                    openedDevices.add(device.getDeviceName());
                }
                //Create a new arraylist to hold device(s) of status 0, opened
                openedDevices = new ArrayList<>();
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

    //Checks network connectivity
    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
                devicesToList = new ArrayList();
                devicesToList = JsonParser.parseDeviceFeed(s);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
