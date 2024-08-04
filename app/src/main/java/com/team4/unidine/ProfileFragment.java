package com.team4.unidine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends AppCompatActivity {

    Button btnlogout;
    SharedPreferences sharedPreferences;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnlogout = findViewById(R.id.logout);
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sign out from firebase
                mAuth.signOut();

                //clear login state
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("logged_in", false);
                editor.apply();

                //redirect to signin
                Intent intent = new Intent(ProfileFragment.this, signin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}