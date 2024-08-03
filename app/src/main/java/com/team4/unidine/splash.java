package com.team4.unidine;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


public class splash extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 1500; // Duration for splash screen (1500 ms = 1.5 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // Using a handler to delay the start of the main activity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Start the main activity
            Intent mainIntent = new Intent(splash.this, signin.class);
            startActivity(mainIntent);
            finish(); // Close the splash activity
        }, SPLASH_DISPLAY_LENGTH);
    }
}
