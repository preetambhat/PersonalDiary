package com.axionet.personaldiary;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public class AddEntry extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_entry);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_entry, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addEntryToDatabase(View view){
		String entryMessageString = ((EditText)findViewById(R.id.entryTextMessage)).getText().toString();
		String userNameString = LoginActivity.LOGGED_IN_USERNAME;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String entryDateString = (dateFormat.format(new Date()));
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String entryTimeString = (timeFormat.format(new Date()));
		
		String insertEntrySQLString = "INSERT INTO Diary VALUES(?,?,?,?)";
		
		Database db = new Database(this, "DATABASE", null, 1);
		
		SQLiteDatabase database = db.getReadableDatabase();
	
		database.execSQL(insertEntrySQLString,new Object[]{userNameString,entryMessageString,entryDateString,entryTimeString});
		
		AddEntry.this.finish();
		startActivity(new Intent(this,DiaryActivity.class));
	}
	
	@Override
	public void onBackPressed(){
		AddEntry.this.finish();
		Intent intent = new Intent(this,DiaryActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
