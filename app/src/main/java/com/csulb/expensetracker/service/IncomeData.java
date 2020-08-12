package com.csulb.expensetracker.service;



import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

//Database to handle settings data
public class IncomeData extends SQLiteOpenHelper {

    //Database name
    private static final String DATABASE = "tracker";
    //Table name
    private static final String TABLE = "settingsData";
    //Column names
    private static final String ANNUAL_INCOME = "annualIncome";
    private static final String DAILY_EXPENSE = "dailyExpense";
    private static final String SAVING = "saving";
    private static final String EXPENSE = "expense";
    private static final String FLAG = "flag";
    private static final String USERID = "userid";

    public IncomeData(Context context)
    {
        super(context,DATABASE,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String QUERY="CREATE TABLE "+TABLE+" ("+ ANNUAL_INCOME +" TEXT,"+ DAILY_EXPENSE +" TEXT,"+ SAVING +" TEXT,"+ EXPENSE +" TEXT,"+ FLAG +" TEXT,"+ USERID +" TEXT)";
        db.execSQL(QUERY);

    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public void onInsert(String k1,String k2,String k3,String k4,String k5,String k6)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ANNUAL_INCOME,k1);
        values.put(DAILY_EXPENSE,k2);
        values.put(SAVING,k3);
        values.put(EXPENSE,k4);
        values.put(FLAG,k5);
        values.put(USERID,k6);
        db.insert(TABLE,null,values);
        db.close();
    }

    public void update(String k1,String k2,String k3,String k4,String k5,String k6)
    {
        SQLiteDatabase db=this.getWritableDatabase();
//        String QUERY="CREATE TABLE "+TABLE+"("+KEY_1+" TEXT,"+KEY_2+" TEXT,"+KEY_3+" TEXT,"+KEY_4+" TEXT,"+KEY_5+" TEXT)";
        String QUERY="UPDATE "+TABLE+" SET "+ ANNUAL_INCOME +"='"+k1+"',"+ DAILY_EXPENSE +"='"+k2+"',"+ SAVING +"='"+k3+"',"+ EXPENSE +"='"+k4+"',"+ FLAG +"='"+k5+"',"+ USERID +"='"+k6+"' WHERE "+ USERID +"='"+k6+"'";
        Log.e("QUERY",QUERY);
        db.execSQL(QUERY);
    }

    /*<summary>to get the count from the database</summary>*/
    public String getCount(String userid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT annualIncome FROM "+TABLE+" WHERE "+ USERID +"='"+userid+"'";
        Cursor cursor;
        cursor = db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            return "1";
        }
        cursor.close();
        db.close();
        return "0";
    }

    public String getAnnualIncome(String userid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT "+ ANNUAL_INCOME +" FROM "+TABLE+" WHERE "+ USERID +"='"+userid+"'";
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "0";
    }
    public String getDailyExpense(String userid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT "+ DAILY_EXPENSE +" FROM "+TABLE+" WHERE "+ USERID +"='"+userid+"'";
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "0";
    }
    public String getSaving(String userid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT "+ SAVING +" FROM "+TABLE+" WHERE "+ USERID +"='"+userid+"'";
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "0";
    }
    public String getk4(String userid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT "+ EXPENSE +" FROM "+TABLE+" WHERE "+ USERID +"='"+userid+"'";
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "0";
    }

}
