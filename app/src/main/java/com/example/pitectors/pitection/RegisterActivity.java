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

import org.json.JSONException;

import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRegister;
    EditText username, password, confirmPass;
    ProgressBar pb;
	List<User> userList;
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

    private void updateDisplay() {
	    if(userList != null){
		    for(User user: userList){
			    Toast.makeText(this,user.getPassword(),Toast.LENGTH_SHORT).show();
		    }

	    }
	    else{
		    Toast.makeText(this, "I didn't get anything", Toast.LENGTH_SHORT).show();
	    }

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

                String uname = username.toString();
                String pass = password.toString();

                if(isOnline()){
                    requestData("http://jsonplaceholder.typicode.com/users");
                }
                else{
                    Toast.makeText(this,"Network isn't available",Toast.LENGTH_SHORT).show();
                }





                //User user = new User(uname, pass);
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
            pb.setVisibility(View.VISIBLE);
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

		        userList = JsonParser.parseFeed(s);
		        updateDisplay();

		        pb.setVisibility(View.INVISIBLE);
	        } catch (Exception e) {
		        e.printStackTrace();
	        }


        }
    }


}
