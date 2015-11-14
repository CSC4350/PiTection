package com.example.pitectors.pitection;

import android.content.SharedPreferences;
import android.content.Context;

/**
 * Created by Rob on 9/20/2015.
 * This class allows the storing of user data on a file, stores the logged in user
 * checks if a user is logged in, and clears the data when a user logs out
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    //Used to store locally
    SharedPreferences userLocalDatabase;

    //Used to get shared preference
    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);

    }

    public void storeUserData(User user){

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);

        spEditor.commit();
    }

    //Get logged in User
    public User getLoggedInUser(){
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password","");

        User storedUser = new User(username, password);

        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();

        spEditor.putBoolean("LoggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        return userLocalDatabase.getBoolean("LoggedIn", false) == true;
    }

    //Clears everything in shared preference
    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
