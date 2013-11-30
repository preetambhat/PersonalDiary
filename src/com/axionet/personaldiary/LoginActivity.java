package com.axionet.personaldiary;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	public static String LOGGED_IN_USERNAME = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void createUserClick(View view){
    	Intent intent = new Intent(this,CreateUser.class);
    	startActivity(intent);
    }
    
    public void loginAction(View view){
    	Database db = new Database(this, "DATABASE", null, 1);
    	
    	String userName = ((EditText)findViewById(R.id.userNameLogin)).getText().toString();
    	String passWord = ((EditText)findViewById(R.id.passwordLogin)).getText().toString();
    	
    	if(userName.trim().length() == 0 || passWord.trim().length() == 0){
    		Toast.makeText(this, "Enter your username and password.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	SQLiteDatabase database = db.getReadableDatabase();
    	
    	String loginSQL = "SELECT * FROM User where Username = ? AND Password  = ?";
    	
    	if((database.rawQuery(loginSQL, new String[]{userName,passWord}).getCount() == 0)){
    		Toast.makeText(this, "Invalid login details", Toast.LENGTH_SHORT).show();
    	} else {
    		
    		LOGGED_IN_USERNAME = userName;
    		LoginActivity.this.finish();
    		Intent intent = new Intent(this,DiaryActivity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
    	}
    	
    }
    
    @Override
	public void onBackPressed(){
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Do you want to exit?");
		dialogBuilder.setMessage("Are you sure you want to exit?");
		dialogBuilder.setCancelable(false);
		dialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				LoginActivity.this.finish();
				
			}
		});
		
dialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.cancel();
				
			}
		});

dialogBuilder.show();
		
	}
    
   
    
}
