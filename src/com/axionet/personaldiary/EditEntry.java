package com.axionet.personaldiary;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class EditEntry extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_entry);
		
		Database db = new Database(this, "DATABASE", null, 1);
		
		SQLiteDatabase database = db.getReadableDatabase();
		
		Cursor cursor = database.rawQuery("SELECT Entry_Text from Diary where ID = ?",new String[]{String.valueOf(DiaryActivity.EDITID)});
		cursor.moveToFirst();
		
		EditText messageText = (EditText)findViewById(R.id.editEntryMessageText);
		messageText.setText(cursor.getString(0));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_entry, menu);
		return true;
	}
	
	public void editEntry(View view){
		
		Database db = new Database(this, "DATABASE", null, 1);
		SQLiteDatabase database = db.getReadableDatabase();
		
		String newMessageString = ((EditText)findViewById(R.id.editEntryMessageText)).getText().toString();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String entryDateString = (dateFormat.format(new Date()));
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String entryTimeString = (timeFormat.format(new Date()));
		
		database.execSQL("UPDATE Diary SET Entry_Text = ?,Entry_Date = ?, Entry_Time = ? WHERE ID = ?",new String[]{newMessageString,entryDateString,entryTimeString,String.valueOf(DiaryActivity.EDITID)});
		EditEntry.this.finish();
		startActivity(new Intent(this,DiaryActivity.class));
	}
	
	public void deleteEntry(View view){
		
		Database db = new Database(this, "DATABASE", null, 1);
		SQLiteDatabase database = db.getReadableDatabase();
		
		database.execSQL("DELETE FROM Diary WHERE ID = ?",new String[]{String.valueOf(DiaryActivity.EDITID)});
		
		EditEntry.this.finish();
		startActivity(new Intent(this,DiaryActivity.class));
		
	}
	

}
