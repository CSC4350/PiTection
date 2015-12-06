package com.example.pitectors.pitection;

import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by rnice01 on 12/2/2015.
 */
public class ValidateRegistration {

    public Boolean returnValidation(String uname, String pass, String key, String RFID) {
        if(!validateUsernameAndPass(uname)){
            Toast.makeText(ContextClass.getApplication().getBaseContext(), "Username must be 6-9 alphanumeric characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!validateUsernameAndPass(pass)){
            Toast.makeText(ContextClass.getApplication().getBaseContext(), "Password must be 6-9 alphanumeric characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!validateKey(key)){
            Toast.makeText(ContextClass.getApplication().getBaseContext(), "Key code must be 6 numeric characters", Toast.LENGTH_SHORT).show();
            return false;

        }
        else if(validateRFID(RFID)){
            Toast.makeText(ContextClass.getApplication().getBaseContext(), "RFID is not valid", Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;
    }

    private boolean validateRFID(String rfid) {
        if(rfid.length() > 1 && rfid.length() < 20){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean validateKey(String key) {
        if(validteKeyLength(key) && !isNumbersOnly(key)){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean validteKeyLength(String key) {

        key.trim();
        if(key.length() == 6){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean validateUsernameAndPass(String string){

        string.trim();
        if(validateUsernamePasswordLength(string) && !hasSpecialChar(string)){
            return true;
        }
        else {
            return false;
        }
    }



    public Boolean validateUsernamePasswordLength(String string){

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
    public Boolean isNumbersOnly(String string){

        Pattern p = Pattern.compile("[^0-9]");
        boolean numbersOnly = p.matcher(string).find();

        return numbersOnly;

    }


}
