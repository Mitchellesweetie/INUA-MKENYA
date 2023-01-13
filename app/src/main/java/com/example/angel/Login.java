package com.example.angel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

public class Login extends AppCompatActivity {
    TextView text;
    EditText username,password;
    Button signin,signup;
    DBHelper DB;

    SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "com.example.angel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText) findViewById(R.id.user1);
        password=(EditText) findViewById(R.id.password1);
        signin=(Button) findViewById(R.id.btnsignin1);
        signup=(Button) findViewById(R.id.btnsignup1);
        //text=(TextView) findViewById(R.id.text);
        DB=new DBHelper(this);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        signup.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intent);
        });

        signin.setOnClickListener(view -> {
            String user=username.getText().toString();
            String pass=password.getText().toString();
            if (user.equals("")||pass.equals(""))
                Toast.makeText(Login.this,"Password or Email Invalid",Toast.LENGTH_SHORT).show();
            else {
                Boolean checkuser = DB.checkuserpassword(user, pass);
                if (checkuser) {
                    Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", user);
                    editor.putString("password", pass);
                    editor.apply();
                }
                else {
                    Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}