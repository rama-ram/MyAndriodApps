package com.example.dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.common.FluidTrackerModel;
import com.example.models.DBModel;
import com.example.myapplication.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ManageFluidsDBManager implements IDBManager {


    private DBHelper dbHelper;

    private Context context;

    private SQLiteDatabase db;

    public ManageFluidsDBManager(Context c) {
        context = c;
    }

    public ManageFluidsDBManager open() throws SQLException {
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
//        if the entry is already there, just update it
        List<FluidTrackerModel> temp = getManageFluidsDBEntries("SELECT * FROM " + DBModel.ManageFluids.TABLE2_NAME+
                " where "+DBModel.ManageFluids.TABLE2_COLUMN1+" = '"+fluid.getFluidName().toUpperCase()+"' AND " +
                " where " +DBModel.ManageFluids.TABLE2_COLUMN4+" = "+ MainActivity.profileSpinner.getSelectedItemId()+" ;");
        System.out.println("List size is " + temp.size());
        values.put(DBModel.ManageFluids.TABLE2_COLUMN1, fluid.getFluidName().toUpperCase());
        values.put(DBModel.ManageFluids.TABLE2_COLUMN2,  fluid.getTarget());
        values.put(DBModel.ManageFluids.TABLE2_COLUMN3,  fluid.getTime());
        values.put(DBModel.ManageFluids.TABLE2_COLUMN4, MainActivity.profileSpinner.getSelectedItemId());
        if(temp.size()==0) {
// Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(DBModel.ManageFluids.TABLE2_NAME, null, values);
        }else{
            long newRowId = db.replace(DBModel.ManageFluids.TABLE2_NAME, null, values);
        }
    }
    public List<FluidTrackerModel> getManageFluidsDBEntries(String sql){
        List<FluidTrackerModel> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();//SELECT * FROM " + DBModel.TrackHistory.TABLE1_NAME
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                FluidTrackerModel result = new FluidTrackerModel();
                result.setFluidName(cursor.getString(cursor.getColumnIndex(DBModel.ManageFluids.TABLE2_COLUMN1)));
//                result.setIntake(cursor.getInt(cursor.getColumnIndex(DBModel.ManageFluids.TABLE2_COLUMN3)));
                result.setTarget(cursor.getInt(cursor.getColumnIndex(DBModel.ManageFluids.TABLE2_COLUMN2)));
                result.setTime(cursor.getString(cursor.getColumnIndex(DBModel.ManageFluids.TABLE2_COLUMN4)));

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

}
