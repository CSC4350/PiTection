package com.example.pitectors.pitection;

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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRegister;
    EditText username, password, keyCode, rfid;
	List<User> userList;
    GetStoredIP getIP;
    ArrayList<String>listOfKeys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.registerUsername);
        password = (EditText) findViewById(R.id.registerUserpass);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        keyCode = (EditText)findViewById(R.id.keyCode);
        rfid = (EditText) findViewById(R.id.rfid);
        btnRegister.setOnClickListener(this);


    }

    private void updateDisplay() {

			    Toast.makeText(this,"Registration successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
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
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnRegister:
                getIP = new GetStoredIP();
                String IP = getIP.readInURL();

                String uname = username.getText().toString();
                String pass = password.getText().toString();
                String key = keyCode.getText().toString();
                String RFID = rfid.getText().toString();

                ValidateRegistration validateRegistration = new ValidateRegistration();

                if (validateRegistration.returnValidation(uname, pass, key, RFID)){
                 if (isOnline()) {
                    requestData(IP + "/getKeys.php", "Request");
                } else {
                    Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
                }

            }else{
                //do nothing
            }





        }
    }

    private void keyDoesNotExist(ArrayList<String> keyList) {
        Boolean result = false;
      if(keyList!= null){
          String keyCodeNum = keyCode.getText().toString();
          for(String key: keyList){
              if(keyCodeNum.equals(key)){
                  result = false;
                  break;

              }
              else{
                 result = true;
              }
          }
          if(result){
              registerUser();
          }
          else{
              Toast.makeText(this,"Key code already registered, please try another", Toast.LENGTH_SHORT).show();
          }
      }
    }

    public void registerUser(){
        getIP = new GetStoredIP();
        String IP = getIP.readInURL();

        String uname = username.getText().toString();
        String pass = password.getText().toString();
        String rfidNum = rfid.getText().toString();
        String keyCodeNum = keyCode.getText().toString();
        Encrypt encrypt = new Encrypt();
        encrypt.setPassword(pass);
        encrypt.encryptPassword();
        String encryptedPassword = encrypt.getGeneratedPassword();

        if (isOnline()) {
            requestData(IP + "/pitected_registration.php?username=" +
                    uname + "&password=" + encryptedPassword + "&keyCode=" + keyCodeNum + "&RFIDCode=" + rfidNum, "Send");
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
        }
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
                    listOfKeys = new ArrayList<>();
                    listOfKeys.addAll(parser.getAllKeys(result[0]));
                    keyDoesNotExist(listOfKeys);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(result[1].equals("Send")){
                updateDisplay();
            }


        }
    }


}
