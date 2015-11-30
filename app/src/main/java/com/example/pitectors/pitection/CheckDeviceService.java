package com.example.pitectors.pitection;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnice01 on 11/14/2015.
 */
public class CheckDeviceService extends Service {
    Context context;
    List<Devices> deviceList;
    CheckDeviceStatus deviceStatus;
    ArrayList<String> devicesChecked;
    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //return super.onStartCommand(intent, flags, startId);
        start();


        return START_STICKY;
    }

    @Override
    public void onDestroy(){
       Toast.makeText(this,"System disarmed", Toast.LENGTH_SHORT).show();
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean started = false;
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
           requestData("http://robertnice.altervista.org/getDeviceData.php");
            if(started) {
                start();
            }
        }
    };

    public void stop() {
        started = false;
        handler.removeCallbacks(runnable);
    }

    public void start() {
        started = true;
        handler.postDelayed(runnable, 5000);
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
                JsonParser parser = new JsonParser();
                deviceList = parser.parseDeviceFeed(s);
                checkDevices(deviceList);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkDevices(List<Devices> userList) {
            //Use the CheckDeviceStatus method to check
            //for any problems with doors or motion detectors
            //notification is sent if the arraylist returned is not empty
        if(userList != null) {
          devicesChecked = new ArrayList<>();
            deviceStatus = new CheckDeviceStatus();
            devicesChecked.addAll(deviceStatus.getCurrentStatus(userList));
            if (!devicesChecked.isEmpty()) {

                //Stop the service once a problem is found, otherwise
                //notifications will continue popping
                stopService(new Intent(getBaseContext(), CheckDeviceService.class));

                //Prep the intent to launch the Event logs activity
                //if the user interacts with the notification
                Intent intent = new Intent(this, EventLogsActivity.class);

                // use System.currentTimeMillis() to have a unique ID for the pending intent
                PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

                //Build notification to alert the user of a disturbance
                Notification n = new Notification.Builder(this)
                        .setContentTitle("Detected disturbance at your home!")
                        .setContentText("Subject")
                        .setSmallIcon(R.mipmap.ic_event_white_48dp)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
                        .build();


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, n);

            }
        }
        else{
            Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
        }

    }
}
