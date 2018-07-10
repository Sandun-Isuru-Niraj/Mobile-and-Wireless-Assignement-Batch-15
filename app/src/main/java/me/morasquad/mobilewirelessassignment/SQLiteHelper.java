package me.morasquad.mobilewirelessassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sandun Isuru Niraj on 10/07/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "THA2_DB";

    public static final int DATABASE_VERSION = 1;


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE User_Infor (index_no TEXT PRIMARY KEY, name Text, mobile Text, email Text, GPA Text, password Text)";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS User_Infor");
        onCreate(sqLiteDatabase);
    }

    public boolean RegisterUser(String index, String name, String email, String GPA, String mobileNo, String password){

        Cursor cursor = getUser(index);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mobile", mobileNo);
        contentValues.put("email", email);
        contentValues.put("GPA", GPA);
        contentValues.put("password", password);

        long result;

        if(cursor.getCount() == 0){
            contentValues.put("index_no", index);
            result = sqLiteDatabase.insert("User_Infor", null, contentValues);
        }else {
            result = -1;
        }

        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public Cursor getUser(String index) {

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM User_Infor WHERE index_no=?";

        return db.rawQuery(sql, new String[]{index});

    }

    public Cursor loginUser(String index, String password){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM User_Infor WHERE index_no=? and password=?";

        return db.rawQuery(sql, new String[]{index,password});
    }


}
