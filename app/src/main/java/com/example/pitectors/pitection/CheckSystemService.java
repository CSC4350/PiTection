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
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by rnice01 on 11/28/2015.
 */
public class CheckSystemService extends Service {
    Context context;
    String status;
    CheckDeviceStatus deviceStatus;
    GetStoredIP getIP;
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
        Toast.makeText(getBaseContext(), "Service stopped", Toast.LENGTH_LONG).show();
        stop();
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
            Toast.makeText(getBaseContext(), "System service running", Toast.LENGTH_SHORT).show();
            //Call method to get stored IP address
            getIP = new GetStoredIP();
            String IP = getIP.readInURL();
            requestData(IP + "/getSystemStatus.php");
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
        handler.postDelayed(runnable, 10000);
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
                status = parser.parseSystemFeed(s);
                checkSystem(status);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkSystem(String status) {
        Toast.makeText(getBaseContext(), "System status is: " + status, Toast.LENGTH_SHORT).show();
        //If the system has been armed, stop the service
        if(status.equals("1")){
            stop();
        CheckDeviceService deviceService = new CheckDeviceService();
            deviceService.onStartCommand(new Intent(this, CheckDeviceService.class), 0, 0);
        MainScreen main = new MainScreen();

            //Stop the service to check the system
           main.performStopSystemService();
        }


    }
}
