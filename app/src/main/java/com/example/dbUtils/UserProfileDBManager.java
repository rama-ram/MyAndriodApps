package com.example.dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.models.DBModel;
import com.example.models.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class UserProfileDBManager implements IDBManager {

    private DBHelper dbHelper;

    private Context context;

    private SQLiteDatabase db;

    public UserProfileDBManager(Context c) {
        context = c;
    }

    public UserProfileDBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertProfileEntry(ProfileModel profile){
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        List<ProfileModel> temp = getProfileDBEntries("SELECT * FROM " + DBModel.UserProfile.TABLE3_NAME+
                " where "+DBModel.UserProfile.TABLE3_COLUMN1+" = '"+profile.getProfileName().toUpperCase()+"'  ;");
        System.out.println("List size is " + temp.size());
        values.put(DBModel.UserProfile.TABLE3_COLUMN1, profile.getProfileName().toUpperCase());
        values.put(DBModel.UserProfile.TABLE3_COLUMN2,  profile.getTotalDailyTarget());
        values.put(DBModel.UserProfile.TABLE3_COLUMN3,  profile.getWeight());

        if(temp.size()==0) {
// Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(DBModel.UserProfile.TABLE3_NAME, null, values);
        }else{
            long newRowId = db.replace(DBModel.UserProfile.TABLE3_NAME, null, values);
        }
    }
    public List<ProfileModel> getProfileDBEntries(String sql){
        List<ProfileModel> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();//SELECT * FROM " + DBModel.UserProfile.TABLE1_NAME
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                ProfileModel result = new ProfileModel();
                result.setProfileName(cursor.getString(cursor.getColumnIndex(DBModel.UserProfile.TABLE3_COLUMN1)));
                result.setTotalDailyTarget(cursor.getInt(cursor.getColumnIndex(DBModel.UserProfile.TABLE3_COLUMN2)));
                result.setWeight(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBModel.UserProfile.TABLE3_COLUMN3))));

                list.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;

    }


    public void updateUserProfileDBEntry(ProfileModel old, ProfileModel latest){
        db = dbHelper.getWritableDatabase();
        //query for the entry with timestamp, if its there, delete it at do a fresh entry, if its not there, simply insert a fresh entry
        deleteProfileDBEntry(old.getProfileName());
        insertProfileEntry(latest);
    }

    public void deleteProfileDBEntry(String name){
        db = dbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = DBModel.UserProfile.TABLE3_COLUMN1 + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { name};
// Issue SQL statement.
        int deletedRows = db.delete(DBModel.UserProfile.TABLE3_NAME, selection, selectionArgs);

    }
    
}
