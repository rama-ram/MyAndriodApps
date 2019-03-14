package com.example.dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.common.FluidTrackerModel;
import com.example.models.DBModel;

import java.util.ArrayList;
import java.util.List;

public class DrinkHistoryDBManager implements IDBManager {


    public static  DBHelper dbHelper;

    private static Context context;

    private static SQLiteDatabase db;

    public DrinkHistoryDBManager(Context c) {
        context = c;
    }

    public DrinkHistoryDBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertFluidsLogDBEntry(FluidTrackerModel fluid){
        db = dbHelper.getWritableDatabase();
//        List<FluidTrackerModel> temp = getFluidsLogDBEntries("SELECT * FROM " + DBModel.TrackHistory.TABLE1_NAME+ " where "+DBModel.TrackHistory.TABLE1_COLUMN1+" = '"+fluid.getFluidName().toUpperCase()+" AND WHERE ';");
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBModel.TrackHistory.TABLE1_COLUMN1, fluid.getFluidName().toUpperCase());
        values.put(DBModel.TrackHistory.TABLE1_COLUMN2,  fluid.getIntake());
        values.put(DBModel.TrackHistory.TABLE1_COLUMN3,  fluid.getTarget());
        values.put(DBModel.TrackHistory.TABLE1_COLUMN4,  fluid.getDate());
        values.put(DBModel.TrackHistory.TABLE1_COLUMN5,  fluid.getTime());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBModel.TrackHistory.TABLE1_NAME, DBModel.TrackHistory.TABLE1_COLUMN6, values);

        //TO DO log statements
    }
    public List<FluidTrackerModel> getFluidsLogDBEntries(String sql){
        List<FluidTrackerModel> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();//SELECT * FROM " + DBModel.TrackHistory.TABLE1_NAME
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                FluidTrackerModel result = new FluidTrackerModel();
                result.setFluidName(cursor.getString(cursor.getColumnIndex(DBModel.TrackHistory.TABLE1_COLUMN1)));
                result.setIntake(cursor.getInt(cursor.getColumnIndex(DBModel.TrackHistory.TABLE1_COLUMN2)));
                result.setTarget(cursor.getInt(cursor.getColumnIndex(DBModel.TrackHistory.TABLE1_COLUMN3)));
                result.setTime(cursor.getString(cursor.getColumnIndex(DBModel.TrackHistory.TABLE1_COLUMN4)));
                result.setProfile(cursor.getString(cursor.getColumnIndex(DBModel.TrackHistory.TABLE1_COLUMN5)));
                list.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;

    }
    public void updateDBEntry(FluidTrackerModel fluid){
        db = dbHelper.getWritableDatabase();
        //query for the entry with timestamp, if its there, delete it at do a fresh entry, if its not there, simply insert a fresh entry
        deleteFluidsLogDBEntry(fluid.getTime());
        insertFluidsLogDBEntry(fluid);
    }

    public void deleteFluidsLogDBEntry(String timstamp){
        db = dbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = DBModel.TrackHistory.TABLE1_COLUMN4 + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { timstamp };
// Issue SQL statement.
        int deletedRows = db.delete(DBModel.TrackHistory.TABLE1_NAME, selection, selectionArgs);

    }
}
