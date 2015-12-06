package com.example.pitectors.pitection;

/**
 * Created by Rob on 9/20/2015.
 */
public class User {
    String username, password, userID, keyCode;

    public User (String username, String userpass, String userID, String keyCode){
        this.username = username;
        this.password = userpass;
        this.userID = userID;
        this.keyCode = keyCode;

    }

    public User(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(String userID){this.userID = userID;}

    public String getUserID(){return userID;}

    public String getKeyCode(){return keyCode;}

    public void setKeyCode(String keyCode){this.keyCode = keyCode;}
}

