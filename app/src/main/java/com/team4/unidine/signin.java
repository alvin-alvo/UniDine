package com.team4.unidine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class signin extends AppCompatActivity {

    EditText editTextusername;
    EditText editTextpassword;
    Button btnlogin;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextusername = findViewById(R.id.username);
        editTextpassword = findViewById(R.id.password);
        btnlogin = findViewById(R.id.loginButton);
        sharedpreferences = getSharedPreferences("user_prefs",MODE_PRIVATE);
        
        // checking the user is already logged in
        if (sharedpreferences.getBoolean("logged_in",false)){
            navigatetohome();
        }
        
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String username = editTextusername.getText().toString();
                String password = editTextpassword.getText().toString();
                
                if (username.equals("user") && password.equals("password")){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("logged_in",true);
                    editor.apply();
                    navigatetohome();
                }else{
                    Toast.makeText(signin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            } 
        });
    }
    
    private void navigatetohome(){
        Intent intent = new Intent(signin.this, home.class);
        startActivity(intent);
        finish();
    }
}