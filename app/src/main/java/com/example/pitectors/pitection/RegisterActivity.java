package com.example.pitectors.pitection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRegister;
    EditText username, password, confirmPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.registerUsername);
        password = (EditText) findViewById(R.id.registerUserpass);
        confirmPass = (EditText) findViewById(R.id.registerConfirm);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnRegister:

                String uname = username.toString();
                String pass = password.toString();

                User user = new User(uname, pass);
        }
    }
}
