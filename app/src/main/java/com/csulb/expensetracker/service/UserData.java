package com.csulb.expensetracker.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserData extends SQLiteOpenHelper {

    //Table name
    private static final String TABLE = "userdetails";
    //Column names
    private static final String KEY_ID = "userid";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String COMPANY = "company";
    private static final String NAME = "name";

    public UserData(Context context){
        super(context, "trackermobile" ,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY="CREATE TABLE "+TABLE+"("+KEY_ID+" TEXT,"+KEY_USERNAME+" TEXT,"+KEY_EMAIL+ " TEXT," + PHONE +  " TEXT,"+COMPANY +" TEXT," +NAME +")";
        db.execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public void onInsert(String userid, String username, String email, String phone, String company, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,userid);
        values.put(KEY_USERNAME,username);
        values.put(KEY_EMAIL,email);
        values.put(PHONE,phone);
        values.put(COMPANY,company);
        values.put(NAME,name);

        db.insert(TABLE,null,values);
        db.close();
    }

    public void update(String userid, String phone, String company, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHONE, phone);
        values.put(COMPANY, company);
        values.put(NAME, name);
        db.update(TABLE, values, "userid = '" + userid + "'", null);
        db.close();
    }

    public String getId() {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT userid FROM "+TABLE;
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "Failed";
    }

    public String getUsername()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT username FROM "+TABLE;
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "Failed";
    }

    public String getEmail()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT email FROM "+TABLE;
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "Failed";
    }

    public String getCompany()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT company FROM "+TABLE;
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "Failed";
    }

    public String getPhone()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT phone FROM "+TABLE;
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "Failed";
    }

    public String getName()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT name FROM "+TABLE;
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "Failed";
    }

    public void logout()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String QUERY="DELETE FROM "+TABLE;
        db.execSQL(QUERY);
    }

}