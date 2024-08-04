package com.team4.unidine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class signin extends AppCompatActivity {

    EditText editTextemail, editTextpassword;
    TextView tvsignup;
    Button btnlogin;
    SharedPreferences sharedpreferences;
    FirebaseAuth mAuth;

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

        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);
        btnlogin = findViewById(R.id.loginButton);
        tvsignup = findViewById(R.id.signUp);

        //initialize firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //initialize shared preferences
        sharedpreferences = getSharedPreferences("user_prefs",MODE_PRIVATE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String email = editTextemail.getText().toString().trim();
                String password = editTextpassword.getText().toString().trim();
                
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(signin.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }
                signIn(email,password);
            } 
        });

        tvsignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //navigate to signin page
                Intent intent = new Intent(signin.this, signup.class);
                startActivity(intent);
            }
        });
    }
    
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,task -> {
            if (task.isSuccessful()){
                //sigin is success
                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null){
                    //save login state
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("logged_in",true);
                    editor.apply();

                    // redirect to home
                    Intent intent = new Intent(signin.this, home.class);
                    startActivity(intent);
                    finish();
                }
            } else{
                Toast.makeText(this, "Sign in failed: "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}