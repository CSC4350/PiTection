package com.example.pitectors.pitection;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by rnice01 on 12/5/2015.
 */
public class UserLoginData {

   public static void storeUserLogin(String userID){

           try {
               final  String STORETEXT="storeLogin.txt";
               OutputStreamWriter out=

                       new OutputStreamWriter(ContextClass.getApplication().getBaseContext().openFileOutput(STORETEXT, 0));

               out.write(userID);

               out.close();
           }

           catch (Throwable t) {


           }


    }

    public static void scrubUserLogin(){
        try {
            final  String STORETEXT="storeLogin.txt";
            OutputStreamWriter out=

                    new OutputStreamWriter(ContextClass.getApplication().getBaseContext().openFileOutput(STORETEXT, 0));

            out.write("");

            out.close();
        }

        catch (Throwable t) {


        }

    }

    public boolean autoLogin(){
        Boolean result = null;
        try {
            final  String STORETEXT="storeLogin.txt";
            InputStreamReader input =

                    new InputStreamReader(ContextClass.getApplication().getBaseContext().openFileInput(STORETEXT));

            BufferedReader reader = new BufferedReader(input);
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.equals("")) {
                    result = true;
                }
                else{
                    return false;
                }
            }


        }

        catch (Throwable t) {
        result = false;

        }
        return result;
    }

    public String getStoredUser(){
            String userID = "";
            try {

                final  String STORETEXT="storeLogin.txt";
                InputStream in = ContextClass.getApplication().getBaseContext().openFileInput(STORETEXT);

                if (in != null) {

                    InputStreamReader tmp=new InputStreamReader(in);

                    BufferedReader reader=new BufferedReader(tmp);

                    String str;

                    StringBuilder buf=new StringBuilder();

                    while ((str = reader.readLine()) != null) {

                        buf.append(str);

                    }

                    in.close();

                    userID = buf.toString();

                }

            }

            catch (java.io.FileNotFoundException e) {

// that's OK, we probably haven't created it yet

            }

            catch (Throwable t) {

                return null;

            }
            return userID;
        }


    }

