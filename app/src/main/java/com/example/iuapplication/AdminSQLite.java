package com.example.iuapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AdminSQLite extends SQLiteOpenHelper {

    //Attributes
    public static final String tableName = "Users"; //
    public static final String dbName = "Clients";
    public static final int dbVersion = 1;

    // Constructor
    public AdminSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, dbVersion);
    }

    // Methods
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE "+tableName+"(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "User TEXT NOT NULL," +
            "Name TEXT NOT NULL," +
            "Pass TEXT NOT NULL" +
            ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        onCreate(db);
    }

}
