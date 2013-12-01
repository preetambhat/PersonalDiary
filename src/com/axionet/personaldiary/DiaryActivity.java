package com.axionet.personaldiary;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DiaryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary);
		
		refreshTable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.diary, menu);
		return true;
	}
	
	public void addEntry(View view){
		DiaryActivity.this.finish();
		startActivity(new Intent(this,AddEntry.class));
	}
	
	public void refreshTable(){
		
		Database db = new Database(this, "DATABASE", null, 1);
		SQLiteDatabase database = db.getReadableDatabase();
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
	
		String selectEntriesQueryString = "SELECT * FROM DIARY WHERE Username = ?";
		Cursor cursor = database.rawQuery(selectEntriesQueryString, new String[]{LoginActivity.LOGGED_IN_USERNAME} );
		
		((LinearLayout)findViewById(R.id.diaryLinearLayout)).removeAllViews();
		
		while(cursor.moveToNext()){
			
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			
			layout.setLayoutParams(layoutParams);
			layoutParams.setMargins(10, 10, 0, 10);
			layout.setPadding(10, 10, 10, 10);
			
			layout.setBackgroundResource(R.drawable.papyrus);
				
			
			TextView messageView = new TextView(this);
			messageView.setText(cursor.getString(2));
			messageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			
			messageView.setTextColor(Color.BLACK);
			messageView.setGravity(Gravity.FILL);
			
			TextView dateView = new TextView(this);
			dateView.setText(cursor.getString(3) + " " + cursor.getString(4));
			dateView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			
			dateView.setTextSize(10);
			dateView.setTextColor(Color.BLACK);
			
			dateView.setGravity(Gravity.RIGHT);
			layout.addView(messageView);
			layout.addView(dateView);
		
			
			
			((LinearLayout)findViewById(R.id.diaryLinearLayout)).addView(layout);
		}
		
		cursor.close();
		database.close();
		
	}
	
	 
	@Override
	public void onBackPressed(){
		
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Logout");
		dialogBuilder.setMessage("Are you sure you want to logout?");
		dialogBuilder.setCancelable(false);
		dialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {

				DiaryActivity.this.finish();
				Intent intent = new Intent(DiaryActivity.this,LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);		
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
