package com.example.pitectors.pitection;

import java.util.regex.Pattern;

/**
 * Created by rnice01 on 12/2/2015.
 */
public class Validator {

    public Boolean validateUsername(String username){

        username.trim();
        if(getLength(username) && !hasSpecialChar(username)){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean validatePassword(String password){
        password.trim();
        if(getLength(password) && !hasSpecialChar(password)){
            return true;
        }
        else {
            return false;
        }
    }


    public Boolean getLength(String string){
        char[] charArray = string.toCharArray();
        int length = charArray.length;

        if(string.length() >= 6 && string.length() <= 9){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean hasSpecialChar(String string){

        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        boolean hasSpecialChar = p.matcher(string).find();

        return hasSpecialChar;

    }
}
