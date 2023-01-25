package com.example.angel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteAdapter extends SQLiteOpenHelper {
    private static final String DB_NAME="Userdetails";
    private static final String TABLE_NAME="UserdetailsTable";
    private static final String COL_ID="id";
    private static final String COL_FNAME="Name";
    private static final String COL_AGE="Age";
    private static final String COL_SCHOOL="School";
    private static final String COL_ADDRESS="Address";
    private static final String COL_EMAIL="Email";
    private static final String COL_PHONE="Phonenumber";
    private static final String COL_WORK = "Work";
    private static final String COL_FEE="fee";
   // private static final String COL_ORPHAN ="Orphan";
    //private static final String COL_parentsjob="parentsjob";
   // private static final String COL_employed="Employed";

    //private static final String COL_feedoc="feedoc";
   // private static final String COL_adddmission_letter="letter";

    public SQLiteAdapter( Context context) {
        super(context,DB_NAME , null, 1);
        SQLiteDatabase Db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase Db) {
        Db.execSQL("create table UserdetailsTable"+"(id integer primary key AUTOINCREMENT,Name TEXT ,School TEXT,Age TEXT," +
                "Address TEXT,email text,phonenumber TEXT,fee text)");

        Db.execSQL("create table UserDetails"+"(id integer primary key AUTOINCREMENT,Name TEXT ,School TEXT,Age TEXT," +
                "Address TEXT,email text,phonenumber TEXT,fee text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase  Db, int i, int i1) {
        Db.execSQL("drop table if exists "+TABLE_NAME);
        Db.execSQL("drop table if exists UserDetails" );
        onCreate(Db);
    }
    public  Boolean insertuserdata(String name,String school,String age,String Address,String email, String phone,String fee){
        SQLiteDatabase Db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_FNAME,name);
        contentValues.put(COL_AGE,age);
        contentValues.put(COL_SCHOOL,school);
        contentValues.put(COL_ADDRESS,Address);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_PHONE,phone);
        contentValues.put(COL_FEE,fee);
        long result=Db.insert("UserDetails",null,contentValues);
        if (result==-1)
            return false;
        else
            return  true;
    }
    public ArrayList<StudentInfo> getStudentInfo(){
        SQLiteDatabase Db=this.getReadableDatabase();
        ArrayList<StudentInfo> arrayList=new ArrayList<>();
        Cursor cursor=Db.rawQuery("Select * from UserDetails",null);

        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String age=cursor.getString(2);
            String school=cursor.getString(3);
            String address=cursor.getString(4);
            String email=cursor.getString(5);
            String phone=cursor.getString(6);
            String fee=cursor.getString(7);
            StudentInfo studentInfo=new StudentInfo(id,name,age,school,address,email,phone,fee);
            arrayList.add(studentInfo);
        }
        return arrayList;
    }

    public ArrayList<StudentNotification> getStudentNotification(){
        SQLiteDatabase Db=this.getReadableDatabase();
        ArrayList<StudentNotification> arrayList=new ArrayList<>();
        Cursor cursor=Db.rawQuery("Select * from UserDetails",null);

        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String school=cursor.getString(3);
            String email=cursor.getString(5);
            String phone=cursor.getString(6);
            StudentNotification studentNotification=new StudentNotification(id,name,school,email,phone);
            arrayList.add(studentNotification);
        }
        return arrayList;
    }



    public  Integer deletedata(String id){
        SQLiteDatabase Db=this.getWritableDatabase();
        return Db.delete(TABLE_NAME,"id=?",new String[]{id});
    }


    public  Boolean updateuserdata(String id,String name,String age,String school,String Address,String email,String phone,String fee) {
        SQLiteDatabase Db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FNAME,name);
        contentValues.put(COL_AGE,age);
        contentValues.put(COL_SCHOOL,school);
        contentValues.put(COL_ADDRESS,Address);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_PHONE,phone);
        contentValues.put(COL_FEE,fee);

        Db.update(TABLE_NAME,contentValues,"id=?",new String[]{id});

        return true;

    }

    public  Boolean updateUserData(String name,String Address,String email, String phone) {
        SQLiteDatabase Db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FNAME,name);
        contentValues.put(COL_ADDRESS,Address);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_PHONE,phone);
//        contentValues.put(COL_WORK, work);

        Db.update(TABLE_NAME,contentValues,"name=?",new String[]{name});

        return true;

    }




}

