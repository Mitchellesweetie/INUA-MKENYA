package com.example.angel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;
    String username;
    String email;
    TextView usernameTextView;
    TextView emailTextView;
    Button logoutButton;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom);
        logoutButton = findViewById(R.id.logout_button);
        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        bottomNavigationView.setSelectedItemId(R.id.profile_option);
        BadgeDrawable badgeDrawable=bottomNavigationView.getOrCreateBadge(R.id.notification_option);
        badgeDrawable.setVisible(true);

        usernameTextView = findViewById(R.id.username_text_view);
        emailTextView = findViewById(R.id.email_text_view);
        sharedPreferences = getSharedPreferences("com.example.angel", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "username");
        email = sharedPreferences.getString("email", "email");

        logoutButton.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
        });

        usernameTextView.setText(username);
        emailTextView.setText(email);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_option:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.notification_option:
                    startActivity(new Intent(getApplicationContext(),Notification.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.profile_option:
                    //startActivity(new Intent(getApplicationContext(),Profile.class));
                    //overridePendingTransition(0,0);
                    return true;

                case R.id.settig_option:
                  startActivity(new Intent(getApplicationContext(),Setting.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }
}