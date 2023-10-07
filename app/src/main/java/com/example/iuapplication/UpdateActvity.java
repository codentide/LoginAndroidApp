package com.example.iuapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActvity extends AppCompatActivity {

    EditText etUpdatePass;
    Button btnUpdatePass, btnUpdateBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etUpdatePass = findViewById(R.id.et_updatePass);

        Intent mainIntent = getIntent();
        String username = mainIntent.getStringExtra("username");

    }

    // Recuperar un elemento pasado por el intent anterior
    public String getIntentValue(String valueKey){
        Intent thisIntent = getIntent();
        String value =  thisIntent.getStringExtra(valueKey);
        return value;
    }

    public void updatePassword(View view) {
        String user = getIntentValue("username");
        String newPass = etUpdatePass.getText().toString();

        if(isValidField(newPass, etUpdatePass, "New password")){
            AdminSQLite admin = new AdminSQLite(this, AdminSQLite.dbName, null, AdminSQLite.dbVersion);
            SQLiteDatabase database = admin.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("Pass", newPass);

            // Actualiza y devuelve el numero de columnas afectadas
            int rowsAffected = database.update("Users", values, "User='" + user + "'", null);

            // Si almenos una columna fue afectada, ah sido exitoso el proceso
            if (rowsAffected > 0) {
                Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                toLogin(view);
            } else {
                Toast.makeText(this, "No se pudo actualizar la contraseña", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidField(String value, EditText editText, String fieldName) {
        if (value.isEmpty()) {
            editText.setError(fieldName + " empty");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public void toLogin(View view){
        Intent intent = new Intent(UpdateActvity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void toMain(View view){
        String username = getIntentValue("username");

        Intent intent = new Intent(UpdateActvity.this, MainActivity.class);
        intent.putExtra("username", username); // "username" es la clave para acceder al valor
        startActivity(intent);
        finish();
    }


}