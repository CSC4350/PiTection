package com.example.pitectors.pitection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.*;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //Get buttons and input fields
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        final EditText username = (EditText) findViewById(R.id.usernameField);
       final  EditText password = (EditText) findViewById(R.id.passwordField);
        final TextView loginError = (TextView) findViewById(R.id.loginError);

        //Test for login credentials, pop ok
        final TextView loginOk = (TextView) findViewById(R.id.textView2);

        //Test user and pass for form validation
       final String userCheck = "user";
        final String userPass = "1234";
        //Login event handlers with form validation

       loginBtn.setOnClickListener(
               new Button.OnClickListener(){
                   public void onClick(View v){
                       if(username.getText().toString().equals(userCheck) && password.getText().toString().equals(userPass)){
                           loginOk.setVisibility(View.VISIBLE);
                       }
                       else{
                           loginError.setVisibility(View.VISIBLE);
                       }

                   }
               }
       );

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
}
