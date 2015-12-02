package com.example.pitectors.pitection;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Rob on 12/2/2015.
 */
public class GetStoredIP {

	 String readInURL() {
		String IP = "";
		try {

			final  String STORETEXT="storeURL.txt";
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

				IP = buf.toString();

			}

		}

		catch (java.io.FileNotFoundException e) {

// that's OK, we probably haven't created it yet

		}

		catch (Throwable t) {

			return null;

		}
		return IP;
	}
}
