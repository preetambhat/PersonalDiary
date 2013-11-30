package com.axionet.personaldiary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);
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
		getMenuInflater().inflate(R.menu.create_user, menu);
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
	
	public void createUserInDatabase(View view){
		Database db = new Database(this, "DATABASE", null, 1);
		SQLiteDatabase database = db.getReadableDatabase();
		
		String usernameString = ((EditText)findViewById(R.id.userNameCreateUser)).getText().toString();
		String passwordString = ((EditText)findViewById(R.id.passwordCreateUser)).getText().toString();
		String firstNameString = ((EditText)findViewById(R.id.firstNameCreateUser)).getText().toString();
		String lastNameString = ((EditText)findViewById(R.id.lastNameCreateUser)).getText().toString();
		String retypePasswordString = ((EditText)findViewById(R.id.retypePasswordCreateUser)).getText().toString();
		if(usernameString.trim().length() < 5){
			Toast.makeText(this, "Please have atleast 5 characters in username.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(passwordString.trim().length() < 8){
			Toast.makeText(this, "Please have atleast 8 characters in password.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(firstNameString.trim().length() == 0 || lastNameString.trim().length() == 0){
			Toast.makeText(this, "Please enter your first name and last name.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(passwordString.equals(retypePasswordString) == false){
			Toast.makeText(this, "Passwords donot match.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String insertUserSQL = "INSERT INTO User VALUES(?,?,?,?)";
		
		String checkUserSQL = "SELECT * from User where Username = ?";
		if(database.rawQuery(checkUserSQL, new String[]{usernameString}).getCount() == 0) {
		
		database.execSQL(insertUserSQL, new Object[]{usernameString,passwordString,firstNameString,lastNameString});
		
		Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show();
		CreateUser.this.finish();
		startActivity(new Intent(this,LoginActivity.class));
		} else {
			Toast.makeText(this, "Username is taken. Use different username", Toast.LENGTH_LONG).show();	
		}
	}
	
	@Override
	public void onBackPressed(){
		
		CreateUser.this.finish();
		Intent intent = new Intent(this,LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
