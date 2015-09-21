package com.example.pitectors.pitection;

/**
 * Created by Rob on 9/20/2015.
 */
public class User {
    String username, password;

    public User (String username, String userpass){
        this.username = username;
        this.password = userpass;

    }

    public User(){
        this.username = "user";
        this.password = "pass";
    }

}

