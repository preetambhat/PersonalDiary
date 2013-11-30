package com.axionet.personaldiary;

import android.os.Bundle;
import android.app.Activity;
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
	
		String selectEntriesQueryString = "SELECT * FROM DIARY WHERE Username = ?";
		Cursor cursor = database.rawQuery(selectEntriesQueryString, new String[]{LoginActivity.LOGGED_IN_USERNAME} );
		
		((LinearLayout)findViewById(R.id.diaryLinearLayout)).removeAllViews();
		
		while(cursor.moveToNext()){
			
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			
			TextView messageView = new TextView(this);
			messageView.setText(cursor.getString(1));
			messageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			//messageView.setPadding(20, 20, 20, 20);
			messageView.setBackgroundColor(Color.WHITE);
			
			TextView dateView = new TextView(this);
			dateView.setText(cursor.getString(2) + " " + cursor.getString(3));
			dateView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			dateView.setPadding(0, 5, 10, 20);
			dateView.setTextSize(10);
			dateView.setBackgroundColor(Color.WHITE);
			dateView.setTextColor(Color.GRAY);
			
			
			dateView.setGravity(Gravity.RIGHT);
			
			layout.addView(messageView);
			layout.addView(dateView);
			
			layout.setBackgroundResource(R.drawable.bordershape);
		
			View view = new View(this);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,2));
			view.setBackgroundColor(Color.BLACK);
			
			layout.addView(view);
			
			((LinearLayout)findViewById(R.id.diaryLinearLayout)).addView(layout);
		}
		
		cursor.close();
		
	}
	
	@Override
	public void onBackPressed(){
		
		DiaryActivity.this.finish();
		Intent intent = new Intent(this,LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}


}
