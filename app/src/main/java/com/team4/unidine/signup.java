package com.team4.unidine;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class signup extends AppCompatActivity {

    EditText etusername, etemail, etpassword, etconfirmpassword, etsemester, etage, etphone;
    TextView tvlogin;
    Button btnsignup;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //initialize firebase authentication
        mAuth = FirebaseAuth.getInstance();
        //initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();


        etusername = findViewById(R.id.Username);
        etemail = findViewById(R.id.Email);
        etpassword = findViewById(R.id.Password);
        etconfirmpassword = findViewById(R.id.ConfirmPassword);
        etsemester = findViewById(R.id.Semester);
        etage = findViewById(R.id.etAge);
        etphone = findViewById(R.id.etPhone);
        btnsignup = findViewById(R.id.btnSignUp);
        tvlogin = findViewById(R.id.tvLogin);

        btnsignup.setOnClickListener(v ->{
            String username = etusername.getText().toString().trim();
            String email = etemail.getText().toString().trim();
            String password = etpassword.getText().toString().trim();
            String confirmPassword = etconfirmpassword.getText().toString().trim();
            String semester = etsemester.getText().toString().trim();
            String age = etage.getText().toString().trim();
            String phone = etphone.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(semester) || TextUtils.isEmpty(age) || TextUtils.isEmpty(phone  )){
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            signUp(username, email, password, semester, age, phone);
        });

        tvlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //navigate to signin page
                Intent intent = new Intent(signup.this, signin.class);
                startActivity(intent);
            }
        });
    }

    private void signUp(String username, String email, String password, String semester, String age, String phone){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task ->{
            if (task.isSuccessful()) {
                //signup success
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null){
                    //save additional info to database
                    User newUser = new User(username, email, semester, age, phone);
                    mDatabase.child(user.getUid()).setValue(newUser).addOnCompleteListener(task1 ->{

                        if (task1.isSuccessful()){
                            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            //redirect to home
                            Intent intent = new Intent(signup.this, home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Failed to save user info", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                //signup failed message
                Toast.makeText(this, "Sign Up failed: "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //user class to represent user data
    public static class User{
        String username, email, semester, age, phone;


        public User(String username, String email, String semester, String age, String phone){
            this.username = username;
            this.email  = email;
            this.semester = semester;
            this.age = age;
            this.phone = phone;
        }
    }
}