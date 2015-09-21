package com.example.pitectors.pitection;
/**This is the current launch screen for the application, checks credentials
 * and does a very simple validation check. Using Toast to display a quick error message
 * if login creds are incorrect
 *
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    Button loginBtn;
    UserLocalStore userLocalStore;

    //Test credentials for login
    String defaultUser = "Pi";
    String defaultPassword = "Tection";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Assigns buttons and input fields
        loginBtn = (Button) findViewById(R.id.loginBtn);
        username = (EditText) findViewById(R.id.usernameField);
        password = (EditText) findViewById(R.id.passwordField);

        loginBtn.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);

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

    //Methods to be used once Http requests can be established for the app
    /**
    @Override
    protected void onStart(){
        super.onStart();
        if(authenticate() == true){
            Intent intent = new Intent(this,MainScreen.class);
           startActivity(intent);
        }
    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();

    }

    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();


    }
*/
    //Switch statement for onClick listeners, loginbtn checks login credentials, btnRegister will
    //change screen to the user register activity.
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
               // User user = new User(null, null);

                //userLocalStore.storeUserData(user);
                //userLocalStore.setUserLoggedIn(true);
                break;
            case R.id.btnRegister:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);


        }

    }
}
