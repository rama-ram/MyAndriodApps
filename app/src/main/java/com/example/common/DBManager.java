package com.example.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBHelper dbHelper;

    private Context context;

    private SQLiteDatabase db;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }
    public void insertManageFluidsEntry(FluidTrackerModel fluid){
        db = dbHelper.getWritableDatabase();
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBModel.ManageFluids.TABLE2_COLUMN1, fluid.getFluidName());
        values.put(DBModel.ManageFluids.TABLE2_COLUMN2,  fluid.getTarget());
        values.put(DBModel.ManageFluids.TABLE2_COLUMN3,  fluid.getTimeStamp());
        values.put(DBModel.ManageFluids.TABLE2_COLUMN4,  fluid.getProfile());

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBModel.ManageFluids.TABLE2_NAME, DBModel.ManageFluids.TABLE2_COLUMN4, values);
        //TO DO log statements
    }
    public List<FluidTrackerModel> getManageFluidsDBEntries(String sql){
        List<FluidTrackerModel> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();//SELECT * FROM " + DBModel.TrackHistory.TABLE1_NAME
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                FluidTrackerModel result = new FluidTrackerModel();
                result.setFluidName(cursor.getString(cursor.getColumnIndex(DBModel.ManageFluids.TABLE2_COLUMN1)));
                result.setIntake(cursor.getInt(cursor.getColumnIndex(DBModel.ManageFluids.TABLE2_COLUMN2)));
                result.setTarget(cursor.getInt(cursor.getColumnIndex(DBModel.ManageFluids.TABLE2_COLUMN3)));
                result.setTimeStamp(cursor.getString(cursor.getColumnIndex(DBModel.ManageFluids.TABLE2_COLUMN4)));

                list.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;

    }
    public void updateManageFluidsDBEntry(FluidTrackerModel oldFluid,FluidTrackerModel newFluid){
        db = dbHelper.getWritableDatabase();
        //query for the entry with timestamp, if its there, delete it at do a fresh entry, if its not there, simply insert a fresh entry
        deleteManageFluidDBEntry(oldFluid.getFluidName());
        insertManageFluidsEntry(newFluid);
    }

    public void deleteManageFluidDBEntry(String name){
        db = dbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = DBModel.ManageFluids.TABLE2_COLUMN1 + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { name};
// Issue SQL statement.
        int deletedRows = db.delete(DBModel.ManageFluids.TABLE2_NAME, selection, selectionArgs);

    }
    public void insertFluidsLogDBEntry(FluidTrackerModel fluid){
        db = dbHelper.getWritableDatabase();
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBModel.TrackHistory.TABLE1_COLUMN1, fluid.getFluidName());
        values.put(DBModel.TrackHistory.TABLE1_COLUMN2,  fluid.getIntake());
        values.put(DBModel.TrackHistory.TABLE1_COLUMN3,  fluid.getTarget());
        values.put(DBModel.TrackHistory.TABLE1_COLUMN4,  fluid.getTimeStamp());
        values.put(DBModel.TrackHistory.TABLE1_COLUMN5,  fluid.getProfile());
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBModel.TrackHistory.TABLE1_NAME, DBModel.TrackHistory.TABLE1_COLUMN5, values);
        //TO DO log statements
    }
    public List<FluidTrackerModel>  getFluidsLogDBEntries(String sql){
        List<FluidTrackerModel> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();//SELECT * FROM " + DBModel.TrackHistory.TABLE1_NAME
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                FluidTrackerModel result = new FluidTrackerModel();
                result.setFluidName(cursor.getString(cursor.getColumnIndex(DBModel.TrackHistory.TABLE1_COLUMN1)));
                result.setIntake(cursor.getInt(cursor.getColumnIndex(DBModel.TrackHistory.TABLE1_COLUMN2)));
                result.setTarget(cursor.getInt(cursor.getColumnIndex(DBModel.TrackHistory.TABLE1_COLUMN3)));
                result.setTimeStamp(cursor.getString(cursor.getColumnIndex(DBModel.TrackHistory.TABLE1_COLUMN4)));
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
        deleteFluidsLogDBEntry(fluid.getTimeStamp());
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
