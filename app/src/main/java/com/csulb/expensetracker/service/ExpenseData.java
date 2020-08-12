package com.csulb.expensetracker.service;



import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

//database to handle expenses data
public class ExpenseData extends SQLiteOpenHelper {

    //Database version
    private static final int VERSION = 1;
    //Database name
    private static final String DATABASE = "expenseData";
    //Table name
    private static final String TABLE = "expenses";
    //Column names
    private static final String KEY_1 = "expense";
    private static final String KEY_2 = "price";
    private static final String KEY_3 = "date";
    private static final String KEY_4 = "userid";

    public ExpenseData(Context context)
    {
        super(context,DATABASE,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String QUERY="CREATE TABLE "+TABLE+"("+KEY_1+" TEXT,"+KEY_2+" TEXT,"+KEY_3+" TEXT,"+KEY_4+" TEXT)";
        db.execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public void onInsert(String k1,String k2,String k3,String k4){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_1,k1);
        values.put(KEY_2,k2);
        values.put(KEY_3,k3);
        values.put(KEY_4,k4);
        db.insert(TABLE,null,values);
        db.close();
    }

    /**

    public String getCount(String userid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT "+KEY_3+" FROM "+TABLE+" WHERE "+KEY_4+"='"+userid+"'";
        Cursor cursor;
        cursor = db.rawQuery(QUERY,null);
        return cursor.getCount()+"";
    }

    public String getk1()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT "+KEY_1+" FROM "+TABLE;
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
    public String getk2()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT "+KEY_2+" FROM "+TABLE;
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
    public String getk3()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT "+KEY_3+" FROM "+TABLE;
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "0";
    } **/

    public int getPrice(String userid){
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT "+KEY_2+" FROM "+TABLE+" WHERE "+KEY_4+"='"+userid+"'";
        Cursor cursor=db.rawQuery(QUERY,null);
        int sum=0;
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++)
        {
            sum+=Integer.parseInt(cursor.getString(0).isEmpty() ? "0" : cursor.getString(0));
            Log.e("Value",cursor.getString(0)+"");
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return sum;
    }

    public String getexpense(String userid){
        String temp="";
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT SUM("+KEY_2+"),"+KEY_3+" FROM "+TABLE+" WHERE "+KEY_4+"='"+userid+"'"+" GROUP BY "+KEY_3;
        Cursor cursor=db.rawQuery(QUERY,null);
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++)
        {
            temp=temp+cursor.getString(0)+","+cursor.getString(1)+",";
            Log.e("Value",cursor.getString(0)+" "+cursor.getString(1));
            cursor.moveToNext();
        }
        Log.e("ERROR",temp.substring(0, temp.length() - 1));
        return temp.substring(0, temp.length() - 1);
    }

    public String getitemized(String userid){
        String temp="";
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT SUM("+KEY_2+"),"+KEY_1+" FROM "+TABLE+" WHERE "+KEY_4+"='"+userid+"'"+" GROUP BY "+KEY_1;
        Cursor cursor=db.rawQuery(QUERY,null);
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++)
        {
            temp=temp+cursor.getString(0)+","+cursor.getString(1)+",";
            Log.e("Value",cursor.getString(0)+" "+cursor.getString(1));
            cursor.moveToNext();
        }
        Log.e("ERROR",temp.substring(0, temp.length() - 1));
        return temp.substring(0, temp.length() - 1);
    }


}

