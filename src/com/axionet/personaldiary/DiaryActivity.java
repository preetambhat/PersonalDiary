package com.axionet.personaldiary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
		startActivity(new Intent(this,AddEntry.class));
	}
	
	public void refreshTable(){
		
		Database db = new Database(this, "DATABASE", null, 1);
		SQLiteDatabase database = db.getReadableDatabase();
	
		String selectEntriesQueryString = "SELECT * FROM DIARY WHERE Username = ?";
		Cursor cursor = database.rawQuery(selectEntriesQueryString, new String[]{LoginActivity.LOGGED_IN_USERNAME} );
		
		((LinearLayout)findViewById(R.id.diaryLinearLayout)).removeAllViews();
		
		while(cursor.moveToNext()){
			TextView textView = new TextView(this);
			textView.setText(cursor.getString(1));
			textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			textView.setPadding(20, 20, 20, 20);
			textView.setBackgroundColor(Color.GRAY);
			((LinearLayout)findViewById(R.id.diaryLinearLayout)).addView(textView);
		}
		
		cursor.close();
		
	}


}
