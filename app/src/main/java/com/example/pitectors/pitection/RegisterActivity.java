package com.example.pitectors.pitection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRegister;
    EditText username, password, confirmPass;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.registerUsername);
        password = (EditText) findViewById(R.id.registerUserpass);
        confirmPass = (EditText) findViewById(R.id.registerConfirm);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        btnRegister.setOnClickListener(this);

        //Make sure the progress bar is invisible on startup
        pb.setVisibility(View.INVISIBLE);

    }

    private void updateDisplay(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }


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


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnRegister:
                MyTask task = new MyTask();
                String uname = username.toString();
                String pass = password.toString();

                if(isOnline()){
                    requestData(task, uname);
                }
                else{
                    Toast.makeText(this,"Network isn't available",Toast.LENGTH_SHORT).show();
                }





                User user = new User(uname, pass);
        }
    }
    //Thread pool executer allows multiple tasks in parallel
    private void requestData(MyTask task, String uname) {
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Ok, ", uname, "");
    }

    //Class to instantiate the Async Task class for running
    //Http Requests in the background
    private class MyTask extends AsyncTask<String, String, String>{
        //This method has access to the main thread
        //and runs before doInBackground
        @Override
        protected void onPreExecute() {
           updateDisplay("Starting task");
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            //Prolong task for progress bar demo purposes
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            return "You're registered";
        }

        //This method receives a result, depending
        //on the AsyncTask<> data parameter type
        @Override
        protected void onPostExecute(String s) {
           updateDisplay(s);
            pb.setVisibility(View.INVISIBLE);
        }
    }


}
