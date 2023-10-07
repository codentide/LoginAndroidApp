package com.example.iuapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvGreeting ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent mainIntent = getIntent();
        String username = mainIntent.getStringExtra("username");

        tvGreeting = findViewById(R.id.tv_greeting);
        tvGreeting.setText("Welcome, "+getNameByUser(username));

    }

    public String getNameByUser(String user){
        AdminSQLite admin = new AdminSQLite(this, AdminSQLite.dbName, null, AdminSQLite.dbVersion);
        SQLiteDatabase database = admin.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE user='"+user+"'", null);

        if (cursor!=null & cursor.moveToFirst()){
            return cursor.getString(2);
        }
        return null;
    }

    /*
    public void consult(View view){
        String user = etUser.getText().toString();
        String pass = etPass.getText().toString();

        AdminSQLite admin = new AdminSQLite(this, AdminSQLite.dbName, null, AdminSQLite.dbVersion);
        SQLiteDatabase database = admin.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE user='"+user+"'", null);

        // Consulta el usuario ingresado y rellena el et2 con su respectiva clave
        if (cursor!=null){
            if(cursor.moveToFirst()){
                etPass.setText(cursor.getString(2));
            }
        }
    }
 */

    public void deleteUser(View view){

        Intent mainIntent = getIntent();
        String username = mainIntent.getStringExtra("username");

        AdminSQLite admin = new AdminSQLite(this, AdminSQLite.dbName, null, AdminSQLite.dbVersion);
        SQLiteDatabase  database = admin.getWritableDatabase();

        int res = database.delete("Users", "user='"+username+"' ",null);
        if (res!=0){
            Toast.makeText(this,"User deleted", Toast.LENGTH_SHORT).show();
        }

        // Cuando borra un usuario sale de la cuenta
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void toUpdate(View view){
        Intent mainIntent = getIntent();
        String username = mainIntent.getStringExtra("username");

        Intent intent = new Intent(MainActivity.this, UpdateActvity.class);
        intent.putExtra("username", username); // "username" es la clave para acceder al valor
        startActivity(intent);
        finish();
    }

    public void toLogin(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}