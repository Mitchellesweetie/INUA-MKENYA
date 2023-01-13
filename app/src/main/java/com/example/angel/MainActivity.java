package com.example.angel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SQLiteAdapter mydb;
    ListView listView;
    TextView usernameTextView;
    ArrayList<StudentInfo> arrayList;
    StudentInfoAdapter adapter;
    public int select_item;
    String username;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "com.example.angel";

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom);
        mydb=new SQLiteAdapter(this);
        listView=(ListView) findViewById(R.id.listview1);
        usernameTextView = findViewById(R.id.username1);

        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        username = sharedPreferences.getString("username", "username");

        usernameTextView.setText(username);

        bottomNavigationView.setSelectedItemId(R.id.home_option);
        BadgeDrawable badgeDrawable=bottomNavigationView.getOrCreateBadge(R.id.notification_option);
        badgeDrawable.setVisible(true);
        showInfo();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_option:
                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    //overridePendingTransition(0,0);
                    return true;

                case R.id.notification_option:
                    startActivity(new Intent(getApplicationContext(),Notification.class));
                    overridePendingTransition(0,0);
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
        });
    }
    private void showInfo() {
        arrayList=mydb.getStudentInfo();
        adapter=new StudentInfoAdapter(this,arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.insert_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        LayoutInflater inflater=(LayoutInflater)MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.insert_layout,null);
        final EditText name=view.findViewById(R.id.name);
        final EditText school=view.findViewById(R.id.school);
        final EditText age=view.findViewById(R.id.age);
        final EditText address=view.findViewById(R.id.address);
        final EditText email=view.findViewById(R.id.email);
        final EditText phone=view.findViewById(R.id.phone);

        //dialogue
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setView(view)
                .setTitle("Enter Details")
                .setMessage("Enter new Person")
                .setIcon(R.drawable.ic_baseline_person_add_24)
                .setPositiveButton("Add a new Person", (dialogInterface, which) -> {
                    boolean res= mydb.insertuserdata(name.getText().toString(),school.getText().toString(),age.getText().toString(),address.getText().toString(),email.getText().toString(),phone.getText().toString());
                    if (res){
                        showInfo();
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelled", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                    }
                });
        builder.create().show();


        return super.onOptionsItemSelected(item);

    }
    AbsListView.MultiChoiceModeListener modeListener=new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int position, long l, boolean b) {
            select_item=position;
            StudentInfo studentInfo=arrayList.get(position);
            actionMode.setTitle(studentInfo.getName());

        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater menuInflater= actionMode.getMenuInflater();
            menuInflater.inflate(R.menu.abs_menu,menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {



            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getTitle().equals("delete"))
            {
                StudentInfo studentInfo=arrayList.get(select_item);
                int selectposition=studentInfo.getId();
                Integer deleteRow=mydb.deletedata(String.valueOf(selectposition));

                if (deleteRow>0){
                    showInfo();
                    Toast.makeText(MainActivity.this, studentInfo.getName()+"items deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MainActivity.this, "Delete", Toast.LENGTH_SHORT).show();
            }
            else if (menuItem.getTitle().equals("update")){
                LayoutInflater inflater=(LayoutInflater)MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View view=inflater.inflate(R.layout.insert_layout,null);
                final EditText name=view.findViewById(R.id.name);
                final EditText school=view.findViewById(R.id.school);
                final EditText age=view.findViewById(R.id.age);
                final EditText address=view.findViewById(R.id.address);
                final EditText email=view.findViewById(R.id.email);
                final EditText phone=view.findViewById(R.id.phone);


                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setView(view)
                        .setTitle("Enter Details")
                        .setMessage("Enter new Person")
                        .setIcon(R.drawable.ic_baseline_person_add_24)
                        .setPositiveButton("Add a new Person", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                StudentInfo studentInfo = arrayList.get(select_item);
                                int id = studentInfo.getId();

                                boolean isupdated = mydb.updateuserdata(String.valueOf(id),name.getText().toString(),school.getText().toString(),age.getText().toString(),address.getText().toString(),email.getText().toString(),phone.getText().toString());
                                if (isupdated){
                                    showInfo();
                                    Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Data not  updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelled", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                            }
                        });
                builder.create().show();

                Toast.makeText(MainActivity.this, "Update", Toast.LENGTH_SHORT).show();

            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }
    };
}