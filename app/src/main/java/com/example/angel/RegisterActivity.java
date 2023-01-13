package com.example.angel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText username,email,password,repassword;
    Button signup,signin;
    DBHelper DB;
    boolean checkusername;

    SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "com.example.angel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username= (EditText)findViewById(R.id.username);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        repassword=(EditText)findViewById(R.id.repassword);
        signin=(Button) findViewById(R.id.btnsignin);
        signup=(Button) findViewById(R.id.btnsignup);
        DB=new DBHelper(this);

        signup.setOnClickListener(view -> {
            String user=username.getText().toString();
            String emal=email.getText().toString();
            String pass=password.getText().toString();
            String repas=repassword.getText().toString();
            if (user.equals("")||emal.equals("")||pass.equals("")||repas.equals(""))
                Toast.makeText(com.example.angel.RegisterActivity.this,"Please Enter all the fields",Toast.LENGTH_SHORT).show();
            else
            {
                if (pass.equals(repas)){
                    boolean checkuser=DB.checkusername(user);
                    if (!checkuser){
                        Boolean insert=DB.insertData(user,pass);
                        if (insert){
                            Toast.makeText(com.example.angel.RegisterActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                            sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", user);
                            editor.putString("email", emal);
                            editor.putString("password", pass);
                            editor.apply();
                        }
                        else{
                            Toast.makeText(com.example.angel.RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(com.example.angel.RegisterActivity.this, "User already exits", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(com.example.angel.RegisterActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                }

            }
        });
        signin.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);

        });

    }
}