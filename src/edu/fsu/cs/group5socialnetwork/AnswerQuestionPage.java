package edu.fsu.cs.group5socialnetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.mobdb.android.GetRowData;
import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerQuestionPage extends Activity {
	final String APP_KEY = "66TP6D-1Ss-00L7SKWoWLlKpaduIiUiUMIR-BLUuIiZxZpPSCIAeua";

	TextView topQuestion;
	EditText answerbox;
	Button answerButton;
	String question, answer, username;
	ListView listView;

	@Override public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.answerquestionpage);
	    	    
	    Intent myIntent = getIntent();
	    Bundle myBundle = myIntent.getExtras();
	    
	    if (myBundle != null)
	    	question = myBundle.getString("question");
	    
	    topQuestion = (TextView)findViewById(R.id.textView1);
	    answerbox = (EditText)findViewById(R.id.editText1);
	    listView = (ListView)findViewById(R.id.listView1);
	    
		SharedPreferences userDetails = AnswerQuestionPage.this.getSharedPreferences("userdetails", MODE_PRIVATE);
		username = userDetails.getString("username", "");		
	    
	    topQuestion.setText(question);
	    
	    populate();
	    
	    Toast.makeText(this, question, Toast.LENGTH_SHORT).show();
	}
	
	
	public void answerQuestionHandler(View v){
		if (answerbox.getText().toString() != null) {
			Toast.makeText(AnswerQuestionPage.this, "Answer submitted", Toast.LENGTH_SHORT).show();

			answer = answerbox.getText().toString();

			InsertRowData insertRowData = new InsertRowData("answers");
			insertRowData.setValue("question", question);
			insertRowData.setValue("username", username);
			insertRowData.setValue("answer", answer);
			MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, new MobDBResponseListener() {
				public void mobDBSuccessResponse() {}
				public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {}
				public void mobDBResponse(String jsonObj) {}
				public void mobDBFileResponse(String fileName, byte[] fileData) {}
				public void mobDBErrorResponse(Integer errValue, String errMsg) {}
			});
		}
		
		populate();
		sendSMS(); 
		answerbox.setText("");
	}
	
	public void populate() {
		GetRowData data = new GetRowData("answers");
		data.getField("question");
		data.getField("answer");

		MobDB.getInstance().execute(APP_KEY, data, null, false, new MobDBResponseListener() {
			public void mobDBSuccessResponse() { }
			public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
				ArrayList<String> toAdd = new ArrayList<String>(); 
				int count = 0; 
				// result.get(0) = first row
				// .get("question") = question attribute
				// [0] since it is a 2D array always have to have [0]
				if (result.size() > 0) { 
					do {
						if (result.get(count).get("question")[0].toString().equals(question)) 
							toAdd.add(result.get(count).get("answer")[0].toString());
						count++;
					} while (count < result.size());
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(AnswerQuestionPage.this, android.R.layout.simple_list_item_1, android.R.id.text1, toAdd);
					listView.setAdapter(adapter);
				}
			}
			public void mobDBResponse(String jsonObj) {}
			public void mobDBFileResponse(String fileName, byte[] fileData) {}
			public void mobDBErrorResponse(Integer errValue, String errMsg) {}
		});
	}

	// sends an SMS message to another device
	public void sendSMS() {		
		GetRowData data = new GetRowData("questions");
		data.getField("username");
		data.whereEqualsTo("question", topQuestion.getText().toString());

		MobDB.getInstance().execute(APP_KEY, data, null, false, new MobDBResponseListener() {
			public void mobDBSuccessResponse() { }
			public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
				if (result.size() > 0) {
					String questionUser; 
					questionUser = result.get(0).get("username")[0].toString(); 
					getPhone(questionUser); 
				}
			}
			public void mobDBResponse(String jsonObj) {}
			public void mobDBFileResponse(String fileName, byte[] fileData) {}
			public void mobDBErrorResponse(Integer errValue, String errMsg) {}
		});
	}
	
	public void getPhone(String questionUser) {
		GetRowData data = new GetRowData("users");
		data.getField("phonenum");
		data.whereEqualsTo("username", questionUser);

		MobDB.getInstance().execute(APP_KEY, data, null, false, new MobDBResponseListener() {
			public void mobDBSuccessResponse() { }
			public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
				String number;
				ArrayList<String> toAdd = new ArrayList<String>();
				// result.get(0) = first row
				// .get("question") = question attribute
				// [0] since it is a 2D array always have to have [0]
				if (result.size() > 0) {
					number = result.get(0).get("phonenum")[0].toString();
					SmsManager sm = SmsManager.getDefault();
					// HERE IS WHERE THE DESTINATION OF THE TEXT SHOULD GO
					sm.sendTextMessage(number, null, answer, null, null);
					Toast.makeText(AnswerQuestionPage.this, "TEXT MESSAGE SENT", Toast.LENGTH_LONG).show();
				}
			}
			public void mobDBResponse(String jsonObj) {}
			public void mobDBFileResponse(String fileName, byte[] fileData) {}
			public void mobDBErrorResponse(Integer errValue, String errMsg) {}
		});
		
	}

}
