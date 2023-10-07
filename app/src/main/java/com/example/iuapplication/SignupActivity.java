package com.example.iuapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etUser, etPass;
    TextView tvToLogin;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.et_signFirstName);
        etUser = findViewById(R.id.et_signUser);
        etPass = findViewById(R.id.et_signPass);
        tvToLogin = findViewById(R.id.tv_toLogin);
        btnSignup = findViewById(R.id.btn_signup);

    }

    public void signupUser(View view){
        // Capturar los string de los et
        String name = etName.getText().toString();
        String user = etUser.getText().toString();
        String pass = etPass.getText().toString();

        // Validar si los campos estan llenos
        if  (isValidField(name, etName, "Name")
                && isValidField(user, etUser, "User")
                && isValidField(pass, etPass, "Pass"))
        {
            // Validar si no existe un usuario con el mismo correo (username)
            if(!userExists(user)) {
                // Insertar Usuario
                insertUser(user, pass, name);

                // Limpiar los EditText
                etName.setText("");
                etUser.setText("");
                etPass.setText("");
            } else{
                Toast.makeText(this, "Este correo ya ha sido registrado anteriormente", Toast.LENGTH_SHORT).show();
            }
        }

    }
    // Método para válidar inputs
    private boolean isValidField(String value, EditText editText, String fieldName) {
        if (value.isEmpty()) {
            editText.setError(fieldName + " empty");
            editText.requestFocus();
            return false;
        }
        return true;
    }
    private void insertUser(String user, String pass, String name) {
        AdminSQLite admin = new AdminSQLite(this, AdminSQLite.dbName, null, AdminSQLite.dbVersion);
        SQLiteDatabase database = admin.getWritableDatabase();

        // Conjunta los datos en un ConcentValues
        ContentValues data = new ContentValues();
        data.put("User", user);
        data.put("Pass", pass);
        data.put("Name", name);

        // Inserta el contentValues en la base de datos
        long result = database.insert(AdminSQLite.tableName, null, data);
        // Si el retorno es diferente de -1 quiere decir que no hubo errores
        if (result != -1) {
            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean userExists(String user) {
        // Instanciación
        AdminSQLite admin = new AdminSQLite(this, AdminSQLite.dbName, null, AdminSQLite.dbVersion);
        SQLiteDatabase  database = admin.getWritableDatabase();

        // Instanciación del cursor con el query pertinente
        Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE User='"+user+"'", null);

        // Se valida si el cursor tiene al menos una fila, si no la tiene, es porque no existe
        return cursor != null && cursor.moveToFirst();
    }




    public void consult(View view){
        String user = etUser.getText().toString();
        String pass = etPass.getText().toString();

        AdminSQLite admin = new AdminSQLite(this, AdminSQLite.dbName, null, AdminSQLite.dbVersion);
        SQLiteDatabase  database = admin.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE user='"+user+"'", null);

        // Consulta el usuario ingresado y rellena el et2 con su respectiva clave
        if (cursor!=null){
            if(cursor.moveToFirst()){
                etPass.setText(cursor.getString(2));
            }
        }
    }
    public void delete(View view){
        String user = etUser.getText().toString();
        String pass = etPass.getText().toString();

        AdminSQLite admin = new AdminSQLite(this, AdminSQLite.dbName, null, AdminSQLite.dbVersion);
        SQLiteDatabase  database = admin.getWritableDatabase();

        int res = database.delete("Users", "user='"+user+"' ",null);
        if (res!=0){
            Toast.makeText(this,"User deleted", Toast.LENGTH_SHORT).show();
        }

    }



    public void toLogin(View view){
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}