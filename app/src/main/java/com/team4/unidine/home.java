package com.team4.unidine;


import android.view.MenuItem;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;


public class home extends AppCompatActivity {

    FirebaseAnalytics mFirebaseAnalytics;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null);

        navigation = findViewById(R.id.bottom_navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.navigation_home:
                        selectedFragment = new Fragment(R.layout.fragment_home);
                        break;
                    case R.id.navigation_cart:
                        selectedFragment = new Fragment(R.layout.fragment_cart);
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new Fragment(R.layout.fragment_profile);
                        break;
                }
                if (selectedFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                }
                return true;
            }
        });

        navigation.setSelectedItemId(R.id.navigation_home);
    }
}