package edu.fsu.cs.group5socialnetwork;

import java.util.HashMap;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
import android.widget.CheckBox;
=======
>>>>>>> b6fca0369a58504bb2d53ea7015b86fa7464c15c
import android.widget.EditText;
import android.widget.Toast;

import com.mobdb.android.GetRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

@SuppressLint("WorldReadableFiles")
public class MainActivity extends Activity {
	final String APP_KEY = "66TP6D-1Ss-00L7SKWoWLlKpaduIiUiUMIR-BLUuIiZxZpPSCIAeua";
	final String TABLE_NAME = "users";
	EditText mUserName; EditText mPassword;
	String password, username; Boolean booly; 
<<<<<<< HEAD
	CheckBox mCheckBox; 
=======
>>>>>>> b6fca0369a58504bb2d53ea7015b86fa7464c15c

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mUserName = (EditText)findViewById(R.id.mainUsername);
		mPassword = (EditText)findViewById(R.id.mainPassword);
<<<<<<< HEAD
		mCheckBox = (CheckBox)findViewById(R.id.rememberCheck);
=======
>>>>>>> b6fca0369a58504bb2d53ea7015b86fa7464c15c

		SharedPreferences userDetails = MainActivity.this.getSharedPreferences("userdetails", MODE_WORLD_READABLE);
		username = userDetails.getString("username", "");
		mUserName.setText(username);
		password = userDetails.getString("password", "");
		mPassword.setText(password);
<<<<<<< HEAD
		
		if (!(username.equals("") && password.equals("")))
			mCheckBox.setChecked(true);
=======
>>>>>>> b6fca0369a58504bb2d53ea7015b86fa7464c15c

		booly = true;
	}

	public void myLoginHandler(View v){
		//Logs the person in if they have a valid username, if not then
		//they are asked to register, we can test this with a query to the database
		//we set up with the usernames and passwords.
		username = mUserName.getText().toString();
		password = mPassword.getText().toString();
		invalid(mUserName);
		invalid(mPassword);

		if (booly == true) {
			//Where we check the two against one another to make sure they work
			//Then proceed to the categories page
			GetRowData data = new GetRowData(TABLE_NAME);
			data.whereEqualsTo("username", username);
			data.andEqualsTo("password", password);

			MobDB.getInstance().execute(APP_KEY, data, null, false, new MobDBResponseListener() {
				public void mobDBSuccessResponse() { }
				public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
					if (result.size() > 0) {
						Intent myIntent = new Intent(MainActivity.this, FirstCategories.class);
						startActivity(myIntent);	

<<<<<<< HEAD
						// save preferences if "Remember Me" is checked
						if (mCheckBox.isChecked()) {
							SharedPreferences userDetails = MainActivity.this.getSharedPreferences("userdetails", MODE_WORLD_READABLE);
							Editor edit = userDetails.edit();
							edit.clear();
							edit.putString("username", username);
							edit.putString("password", password);
							edit.commit();
						}
						// otherwise don't save anything
						else {
							SharedPreferences userDetails = MainActivity.this.getSharedPreferences("userdetails", MODE_WORLD_READABLE);
							Editor edit = userDetails.edit();
							edit.clear();
							edit.commit();
						}
=======
						SharedPreferences userDetails = MainActivity.this.getSharedPreferences("userdetails", MODE_WORLD_READABLE);
						Editor edit = userDetails.edit();
						edit.clear();
						edit.putString("username", username);
						edit.putString("password", password);
						edit.commit();
>>>>>>> b6fca0369a58504bb2d53ea7015b86fa7464c15c
					}
					else
						Toast.makeText(MainActivity.this, "Invalid username/password. \n Please register or try again.", Toast.LENGTH_SHORT).show();
				}
				public void mobDBResponse(String jsonObj) {}
				public void mobDBFileResponse(String fileName, byte[] fileData) {}
				public void mobDBErrorResponse(Integer errValue, String errMsg) {}
			});
		}
	}

	// check for invalid characters
	public void invalid (EditText edit) {
		String str = edit.getText().toString();
		str = str.toUpperCase();
		for (int i = 0; i < edit.length(); i++) {
			if ((str.charAt(i) == '\'') || (str.charAt(i) == '"') || (str.charAt(i) == '/') || 
					(str.charAt(i) == '\\') || (str.charAt(i) == ';') || (str.charAt(i) == '-') || 
					(str.charAt(i) == '#')) {
				edit.setError("must not contain \', \", /, \\, ;, -, #");
				booly = false; 
			}
			else if (str.substring(0).equals("NULL")) {
				edit.setError("must not contain NULL");
				booly = false;
			}
		}
	}

	public void myRegisterHandler(View v){
		Intent myIntent = new Intent(this, RegisterActivity.class);
		startActivity(myIntent);
	}
<<<<<<< HEAD

	@Override protected void onDestroy() {
		super.onDestroy();
		// save preferences if "Remember Me" is checked
		if (mCheckBox.isChecked()) {
			SharedPreferences userDetails = MainActivity.this.getSharedPreferences("userdetails", MODE_WORLD_READABLE);
			Editor edit = userDetails.edit();
			edit.clear();
			edit.putString("username", username);
			edit.putString("password", password);
			edit.commit();
		}
		else {
			SharedPreferences userDetails = MainActivity.this.getSharedPreferences("userdetails", MODE_WORLD_READABLE);
			Editor edit = userDetails.edit();
			edit.clear();
			edit.commit();
		}
=======
	
	@Override protected void onDestroy() {
		super.onDestroy();
		SharedPreferences userDetails = MainActivity.this.getSharedPreferences("userdetails", MODE_WORLD_READABLE);
		Editor edit = userDetails.edit();
		edit.clear();
		edit.putString("username", username);
		edit.putString("password", password);
		edit.commit();
>>>>>>> b6fca0369a58504bb2d53ea7015b86fa7464c15c
	}
}
