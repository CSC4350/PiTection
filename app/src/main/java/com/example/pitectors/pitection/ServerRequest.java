package com.example.pitectors.pitection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


/**
 * Created by Rob on 9/20/2015.
 */
public class ServerRequest {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIME = 1000 * 15;
    public static final String SERVER_ADDRESS = "localhost/";

    public ServerRequest(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait..");

    }

    public void storeUserDataInBackground(User user, GetUserCallback callback){
        progressDialog.show();
        new StoreUserDataAsyncTask(user, callback).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback callback){
        progressDialog.show();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void>{
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback callback){
            this.user = user;
            this.userCallback = callback;
        }
        @Override
        protected Void doInBackground(Void... params) {

            //Access the server

            return null;
        }

        @Override
        protected void onPostExecute(Void a){

            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(a);
        }
    }
}
