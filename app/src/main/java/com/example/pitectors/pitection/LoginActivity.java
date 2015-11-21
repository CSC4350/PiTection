package com.example.pitectors.pitection;
/**This is the current launch screen for the application, checks credentials
 * and does a very simple validation check. Using Toast to display a quick error message
 * if login creds are incorrect
 *
 */

import android.content.Context;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.content.Intent;
import android.net.ConnectivityManager;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    Button loginBtn, registerBtn;
    List<User> userList;

    //Test credentials for login
    String validateUser;
    String validatePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Assigns buttons and input fields
        registerBtn = (Button) findViewById(R.id.registerBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        username = (EditText) findViewById(R.id.usernameField);
        password = (EditText) findViewById(R.id.passwordField);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**Check the network connectivity
     */

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

    private void updateDisplay() {
        //userList is the information returned from the JsonParser
        //if the list is not empty, use a foreach loop to get the username and password
        //from the created object(s)
       if(userList != null){
            for(User user: userList){

               validatePassword = user.getPassword();
                validateUser = user.getUsername();
            }

        }
        else{
            Toast.makeText(this, "I didn't get anything", Toast.LENGTH_SHORT).show();
        }
        //Validate the login credentials, begin MainScreen activity
        if(username.getText().toString().equals(validateUser) && password.getText().toString().equals(validatePassword)) {
            Intent mainScreenIntent = new Intent(this, MainScreen.class);

            startActivity(mainScreenIntent);
        }
        else{
            Toast.makeText(this,"Invalid Credentials", Toast.LENGTH_SHORT).show();
        }

    }




    //Switch statement for onClick listeners, loginbtn checks login credentials, btnRegister will
    //change screen to the user register activity. IF ADDING NEW BUTTONS!! Make sure to call the
    //onSetClickLister() method on them before adding them to the switch statement
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginBtn:
                Intent mainScreenIntent = new Intent(this, MainScreen.class);

                startActivity(mainScreenIntent);
                /**
                if(isOnline()){
                    requestData("http://robertnice.altervista.org/getUserData.php");
                }
                else{
                    Toast.makeText(this,"Network isn't available",Toast.LENGTH_SHORT).show();
                }
                break;
                  case R.id.registerBtn:
                Intent regIntent = new Intent(this,RegisterActivity.class);
                startActivity(regIntent);
                      break;
            default:
                Toast.makeText(getApplicationContext(), "Invalid credentials", LENGTH_SHORT).show();

                 */
        }

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

                userList = JsonParser.parseFeed(s);
                updateDisplay();


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    }
