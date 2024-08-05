package com.team4.unidine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private Button btnlogout;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnlogout = view.findViewById(R.id.logout);
        sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        btnlogout.setOnClickListener(v -> {
            Log.d(TAG, "Logout button clicked");

            // Sign out from Firebase
            Log.d(TAG, "Attempting to sign out from Firebase");
            mAuth.signOut();
            Log.d(TAG, "Firebase sign-out completed");

            // Clear login state from SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Clears all values
            editor.apply();
            Log.d(TAG, "SharedPreferences cleared");

            // Redirect to sign-in
            Intent intent = new Intent(requireActivity(), signin.class);
            startActivity(intent);
            Log.d(TAG, "Redirecting to sign-in page");
            requireActivity().finish();
        });

        return view;
    }
}
