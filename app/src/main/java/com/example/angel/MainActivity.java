package com.example.angel;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.angel";
    public int select_item;
    BottomNavigationView bottomNavigationView;
    SQLiteAdapter mydb;
    ListView listView;
    TextView usernameTextView;
    ArrayList<StudentInfo> arrayList;
    StudentInfoAdapter adapter;
    String usernameText;
    String emailText;
    String addressText;
    String phoneText;
    SharedPreferences sharedPreferences;
    Button uploadButton;

    DBHelper DB;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.profile_dialog, null);
        final EditText name = view.findViewById(R.id.namep);
        final EditText address = view.findViewById(R.id.addressp);
        final EditText email = view.findViewById(R.id.emailp);
        final EditText phone = view.findViewById(R.id.phonep);

        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        usernameText = sharedPreferences.getString("username", "");
        emailText = sharedPreferences.getString("email", "");
        addressText = sharedPreferences.getString("address", "");
        phoneText = sharedPreferences.getString("phone", "");

        name.setText(usernameText);
        email.setText(emailText);
        address.setText(addressText);
        phone.setText(phoneText);

        if (Objects.equals(usernameText, "") || Objects.equals(emailText, "") || Objects.equals(addressText, "") || Objects.equals(phoneText, "")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(view)
                    .setTitle("Update Details")
                    .setMessage("Update User Details")
                    .setIcon(R.drawable.ic_baseline_person_add_24)
                    .setPositiveButton("Update", (dialogInterface, i) -> {
                        boolean res = mydb.updateUserData(name.getText().toString(), address.getText().toString(), email.getText().toString(), phone.getText().toString());
                        if (res) {
                            showInfo();
                            editor.putString("username", String.valueOf(name.getText()));
                            editor.putString("email", String.valueOf(email.getText()));
                            editor.putString("address", String.valueOf(address.getText()));
                            editor.putString("phone", String.valueOf(phone.getText()));
                            editor.apply();
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelable(false)
                    .setNegativeButton("Cancel", (dialogInterface, which) -> dialogInterface.cancel());

            builder.create().show();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom);
        mydb = new SQLiteAdapter(this);
        listView = (ListView) findViewById(R.id.listview1);

//        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//
//        usernameText = sharedPreferences.getString("username", "username");
//        emailText = sharedPreferences.getString("email", "email");


        bottomNavigationView.setSelectedItemId(R.id.home_option);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notification_option);
        badgeDrawable.setVisible(true);
        showInfo();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_option:
                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    //overridePendingTransition(0,0);
                    return true;

                case R.id.notification_option:
                    startActivity(new Intent(getApplicationContext(), Notification.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.profile_option:
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.settig_option:
                    startActivity(new Intent(getApplicationContext(), Setting.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void showInfo() {
        arrayList = mydb.getStudentInfo();
        adapter = new StudentInfoAdapter(this, arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.insert_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.insert_layout, null);
        uploadButton = findViewById(R.id.feestruture);
        final EditText name = view.findViewById(R.id.name);
        final EditText school = view.findViewById(R.id.school);
        final EditText age = view.findViewById(R.id.age);
        final EditText address = view.findViewById(R.id.address);
        final EditText email = view.findViewById(R.id.email);
        final EditText phone = view.findViewById(R.id.phone);
        final EditText fee = view.findViewById(R.id.feeamount);

        name.setText(usernameText);
        email.setText(emailText);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view)
                .setTitle("Enter Details")
                .setMessage("Enter new Person")
                .setIcon(R.drawable.ic_baseline_person_add_24)
                .setPositiveButton("Add Person", (dialogInterface, i) -> {
                    boolean res = mydb.insertuserdata(name.getText().toString(), school.getText().toString(), age.getText().toString(), address.getText().toString(), email.getText().toString(), phone.getText().toString(), fee.getText().toString());
                    if (res) {
                        showInfo();
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelled", (dialogInterface, which) -> dialogInterface.cancel());
//        final AlertDialog dialog = builder.create();
//        dialog.show();
        builder.create().show();


        return super.onOptionsItemSelected(item);

    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int position, long l, boolean b) {
            select_item = position;
            StudentInfo studentInfo = arrayList.get(position);
            actionMode.setTitle(studentInfo.getName());

        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater menuInflater = actionMode.getMenuInflater();
            menuInflater.inflate(R.menu.abs_menu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {


            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getTitle().equals("delete")) {
                StudentInfo studentInfo = arrayList.get(select_item);
                int selectposition = studentInfo.getId();
                Integer deleteRow = mydb.deletedata(String.valueOf(selectposition));

                if (deleteRow > 0) {
                    showInfo();
                    Toast.makeText(MainActivity.this, studentInfo.getName() + "items deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MainActivity.this, "Delete", Toast.LENGTH_SHORT).show();
            } else if (menuItem.getTitle().equals("update")) {
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.insert_layout, null);
                final EditText name = view.findViewById(R.id.name);
                final EditText school = view.findViewById(R.id.school);
                final EditText age = view.findViewById(R.id.age);
                final EditText address = view.findViewById(R.id.address);
                final EditText email = view.findViewById(R.id.email);
                final EditText phone = view.findViewById(R.id.phone);
                final EditText fee = view.findViewById(R.id.feeamount);


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(view)
                        .setTitle("Enter Details")
                        .setMessage("Enter new Person")
                        .setIcon(R.drawable.ic_baseline_person_add_24)
                        .setPositiveButton("Add a new Person", (dialogInterface, which) -> {
                            StudentInfo studentInfo = arrayList.get(select_item);
                            int id = studentInfo.getId();

                            boolean isupdated = mydb.updateuserdata(String.valueOf(id), name.getText().toString(), school.getText().toString(), age.getText().toString(), address.getText().toString(), email.getText().toString(), phone.getText().toString(), fee.getText().toString());
                            if (isupdated) {
                                showInfo();
                                Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Data not  updated", Toast.LENGTH_SHORT).show();
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