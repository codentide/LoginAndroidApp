package com.example.iuapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etUser, etPass;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.et_user);
        etPass = findViewById(R.id.et_pass);
        tv1 = findViewById(R.id.tv1_loginView);

    }

    public void logInUser(View view) {

        String user = etUser.getText().toString();
        String pass = etPass.getText().toString();

        if  (isValidField(user, etUser, "Name") && isValidField(pass, etPass, "Pass")){

            if(isValidCredentials(user, pass)){

                // Credenciales válidas, redirige al MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                // Pasar un valor de esta layout al siguiente
                intent.putExtra("username", user); // "username" es la clave para acceder al valor
                startActivity(intent);
                finish(); // Evita que el usuario regrese a esta actividad con el botón Atrás

            } else{
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private boolean isValidCredentials(String username, String password) {
        // Instanciar la base de datos
        AdminSQLite admin = new AdminSQLite(this, AdminSQLite.dbName, null, AdminSQLite.dbVersion);
        SQLiteDatabase database = admin.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE User='"+username+"' AND Pass='"+password+"'", null);

        // Verificar si el cursor tiene al menos una fila, lo que indica credenciales válidas
        boolean isValid = cursor.moveToFirst();

        // Cerrar el cursor y la base de datos
        cursor.close();
        database.close();

        return isValid;
    }

    private boolean isValidField(String value, EditText editText, String fieldName) {
        if (value.isEmpty()) {
            editText.setError(fieldName + " empty");
            editText.requestFocus();
            return false;
        }
        return true;
    }
    public void toSignup(View view){
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }


}