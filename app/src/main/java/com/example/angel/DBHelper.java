package com.example.angel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME="Login.db";

    public DBHelper( Context context) {
        super(context,"Login.db", null, 1);
    }

    //creating table FOR LOGIN page
    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create table user" +
                "(email TEXT primary key,password TEXT)");

        myDB.execSQL("create table UserdetailsTable" +
                "(name TEXT, age TEXT, school TEXT, address TEXT, email TEXT, phone TEXT, fee TEXT)");

    }
    //droping any existence of login page
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop table if exists user");
        myDB.execSQL("drop table if exists UserdetailsTable");
        //creating tables again
        onCreate(myDB);

    }
    //adding any user that registers
    public Boolean insertData(String user,String password){
        SQLiteDatabase myDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Email",user);
        contentValues.put("Password",password);
        long result=myDB.insert("user",null,contentValues);
        if (result==-1)
            return false;
        else
            return  true;
    }

    public Boolean insertFeeData(String username, String age, String school, String address, String email, String phone, String fee) {
        SQLiteDatabase myDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username", username);
        contentValues.put("age", age);
        contentValues.put("school", school);
        contentValues.put("address", address);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("fee", fee);

        long result=myDB.insert("UserdetailsTable",null,contentValues);
        return result != -1;
    }
    //checking the existence of password and user
    public Boolean checkusername(String user){
        SQLiteDatabase myDB= this.getWritableDatabase();
        Cursor cursor=myDB.rawQuery("select * from user where email=?", new String[]{user});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkuserpassword(String user,String password){
        SQLiteDatabase myDB= this.getWritableDatabase();
        Cursor cursor=myDB.rawQuery("select * from user where email=? and password=?", new String[] {user,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;



    }
}


