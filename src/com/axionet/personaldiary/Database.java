package com.axionet.personaldiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	public Database(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		String createLoginTableSQL = "CREATE TABLE User(Username VARCHAR(12) PRIMARY KEY,Password VARCHAR(12),FirstName VARCHAR(12),LastName VARCHAR(12))";
		database.execSQL(createLoginTableSQL);
		
		String createDiaryTableSQL = "CREATE TABLE Diary(ID INTEGER PRIMARY KEY AUTOINCREMENT,Username VARCHAR(12),Entry_Text VARCHAR(2048),Entry_Date VARCHAR(10),Entry_Time VARCHAR(10))";
		database.execSQL(createDiaryTableSQL);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	

}
