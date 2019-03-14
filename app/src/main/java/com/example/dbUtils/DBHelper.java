package com.example.dbUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.models.DBModel;


public class DBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private SQLiteDatabase db;
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "FluidTracker.db";

        public final static String SQL_CREATE_TABLE1 = "CREATE TABLE " +
        DBModel.TrackHistory.TABLE1_NAME  + " ( " +
        DBModel.TrackHistory._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
    DBModel.TrackHistory.TABLE1_COLUMN1+ " INTEGER REFERENCES "+DBModel.ManageFluids.TABLE2_NAME+","+
    DBModel.TrackHistory.TABLE1_COLUMN2 + " INTEGER NOT NULL, " +
    DBModel.TrackHistory.TABLE1_COLUMN3 + " INTEGER, " +
    DBModel.TrackHistory.TABLE1_COLUMN4 + " TEXT NOT NULL," +
        DBModel.TrackHistory.TABLE1_COLUMN5+ " TEXT NOT NULL ," +
                DBModel.TrackHistory.TABLE1_COLUMN6+ " TEXT REFERENCES "+DBModel.UserProfile.TABLE3_NAME+
            ");";


    public final static String SQL_CREATE_TABLE2 = "CREATE TABLE " +
            DBModel.ManageFluids.TABLE2_NAME  + " ( " +
            DBModel.ManageFluids._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBModel.ManageFluids.TABLE2_COLUMN1+ " TEXT UNIQUE, " +
            DBModel.ManageFluids.TABLE2_COLUMN2 + " INTEGER NOT NULL, " +
            DBModel.ManageFluids.TABLE2_COLUMN3 + " INTEGER, " +
            DBModel.ManageFluids.TABLE2_COLUMN4 +  " TEXT REFERENCES "+DBModel.UserProfile.TABLE3_NAME+
                       ");";

    public final static String SQL_CREATE_TABLE3 = "CREATE TABLE " +
            DBModel.UserProfile.TABLE3_NAME  + " ( " +
            DBModel.UserProfile._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBModel.UserProfile.TABLE3_COLUMN1+ " TEXT UNIQUE, " +
            DBModel.UserProfile.TABLE3_COLUMN2 + " INTEGER NOT NULL, " +
            DBModel.UserProfile.TABLE3_COLUMN3 + " INTEGER NOT NULL " +
                        ");";


    public static final String SQL_DELETE_TABLE1 = "DROP TABLE IF EXISTS " + DBModel.TrackHistory.TABLE1_NAME ;
    public static final String SQL_DELETE_TABLE2 = "DROP TABLE IF EXISTS " + DBModel.ManageFluids.TABLE2_NAME ;
    public static final String SQL_DELETE_TABLE3 = "DROP TABLE IF EXISTS " + DBModel.UserProfile.TABLE3_NAME ;


        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLE3 );
            db.execSQL(SQL_CREATE_TABLE2 );
            db.execSQL(SQL_CREATE_TABLE1 );
            }
    @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLE1 );
        db.execSQL(SQL_DELETE_TABLE2);
        db.execSQL(SQL_DELETE_TABLE3 );


            onCreate(db);
        }
    @Override
        public void onDowngrade(SQLiteDatabase db,int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

    }



