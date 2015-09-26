package com.example.pitectors.pitection;
/**This is the current launch screen for the application, checks credentials
 * and does a very simple validation check. Using Toast to display a quick error message
 * if login creds are incorrect
 *
 */

import android.content.Context;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import android.net.ConnectivityManager;

import java.net.HttpURLConnection;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    Button loginBtn, registerBtn;
    UserLocalStore userLocalStore;

    //Test credentials for login
    String defaultUser = "Pi";
    String defaultPassword = "Tection";
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
        userLocalStore = new UserLocalStore(this);
        registerBtn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

	protected boolean authenticate(String username, String password) throws MalformedURLException {
		URL url = new URL("localhost/getData.php");
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String line;
			while((line = reader.readLine()) != null){
				sb.append(line + " ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
    //Methods to be used once Http requests can be established for the app
  protected void updateDisplay(String message){
      Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
  }


    //Switch statement for onClick listeners, loginbtn checks login credentials, btnRegister will
    //change screen to the user register activity. IF ADDING NEW BUTTONS!! Make sure to call the
    //onSetClickLister() method on them before adding them to the switch statement
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginBtn:
                if(username.getText().toString().equals(defaultUser) && password.getText().toString().equals(defaultPassword)){
                    Intent intent = new Intent(this,MainScreen.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
                break;
                  case R.id.registerBtn:
                Intent regIntent = new Intent(this,RegisterActivity.class);
                startActivity(regIntent);
                      break;
            default:
                Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();


        }

    }
}
