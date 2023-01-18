package com.example.angel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SQLiteAdapter mydb;
    ListView listView;
    ArrayList<StudentNotification> arrayList;
    StudentNotiAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mydb=new SQLiteAdapter(this);
        listView=(ListView) findViewById(R.id.listview8);



        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        bottomNavigationView.setSelectedItemId(R.id.notification_option);
        BadgeDrawable badgeDrawable=bottomNavigationView.getOrCreateBadge(R.id.notification_option);
        badgeDrawable.setVisible(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_option:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.notification_option:
                        //startActivity(new Intent(getApplicationContext(),Notification.class));
                       // overridePendingTransition(0,0);
                        return true;

                    case R.id.profile_option:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settig_option:
                        startActivity(new Intent(getApplicationContext(),Setting.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        arrayList = mydb.getStudentNotification();
        adapter = new StudentNotiAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }
}