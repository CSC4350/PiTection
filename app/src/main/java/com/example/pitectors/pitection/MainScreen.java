package com.example.pitectors.pitection;
/**
 * This will be the main screen for the app once the user is logged in,
 * will display system status, show security point errors, and have a settings option
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.*;


public class MainScreen extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    Button logoutBtn;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        //Get buttons and input fields
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
       username = (EditText) findViewById(R.id.usernameField);
       password = (EditText) findViewById(R.id.passwordField);
        final TextView loginError = (TextView) findViewById(R.id.loginError);

        //Test for login credentials, pop ok
        final TextView loginOk = (TextView) findViewById(R.id.textView2);

        logoutBtn.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);



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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logoutBtn:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

        }
    }
}
