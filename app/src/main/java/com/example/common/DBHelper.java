package com.example.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private SQLiteDatabase db;
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "FluidTracker.db";

        public final static String SQL_CREATE_TABLE1 = "CREATE TABLE " +
        DBModel.TrackHistory.TABLE1_NAME  + " ( " +
        DBModel.TrackHistory._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
    DBModel.TrackHistory.TABLE1_COLUMN1+ " TEXT, " +
    DBModel.TrackHistory.TABLE1_COLUMN2 + " INTEGER, " +
    DBModel.TrackHistory.TABLE1_COLUMN3 + " INTEGER, " +
    DBModel.TrackHistory.TABLE1_COLUMN4 + " TEXT," +
        DBModel.TrackHistory.TABLE1_COLUMN5+ " TEXT " +
            ");";
        public static final String SQL_DELETE_TABLE1 = "DROP TABLE IF EXISTS " + DBModel.TrackHistory.TABLE1_NAME ;

    public final static String SQL_CREATE_TABLE2 = "CREATE TABLE " +
            DBModel.ManageFluids.TABLE2_NAME  + " ( " +
            DBModel.ManageFluids._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBModel.ManageFluids.TABLE2_COLUMN1+ " TEXT, " +
            DBModel.ManageFluids.TABLE2_COLUMN2 + " INTEGER, " +
            DBModel.ManageFluids.TABLE2_COLUMN3 + " INTEGER, " +
            DBModel.ManageFluids.TABLE2_COLUMN4 + " INTEGER"+            ");";
    public static final String SQL_DELETE_TABLE2 = "DROP TABLE IF EXISTS " + DBModel.ManageFluids.TABLE2_NAME ;

    public final static String SQL_INSERT_ENTRIES1 = "CREATE TABLE " +
            DBModel.TrackHistory.TABLE1_NAME  + " ( " +
            DBModel.TrackHistory._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBModel.TrackHistory.TABLE1_COLUMN1+ " TEXT NOT NULL, " +
            DBModel.TrackHistory.TABLE1_COLUMN2 + " INTEGER, " +
            DBModel.TrackHistory.TABLE1_COLUMN3 + " INTEGER, " +
            DBModel.TrackHistory.TABLE1_COLUMN4 + " TEXT," +
            DBModel.TrackHistory.TABLE1_COLUMN5+ " TEXT " +
            ")";


    public final static String SQL_INSERT_ENTRIES2 = "CREATE TABLE " +
            DBModel.ManageFluids.TABLE2_NAME  + " ( " +
            DBModel.ManageFluids._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBModel.ManageFluids.TABLE2_COLUMN1+ " TEXT NOT NULL, " +
            DBModel.ManageFluids.TABLE2_COLUMN2 + " INTEGER, " +
            DBModel.ManageFluids.TABLE2_COLUMN3 + " INTEGER, " +
            DBModel.ManageFluids.TABLE2_COLUMN4 + " TEXT" +

            ");";
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
             System.out.println(SQL_CREATE_TABLE1 );
            System.out.println(SQL_CREATE_TABLE2 );
            db.execSQL(SQL_CREATE_TABLE1 );
            System.out.println(SQL_CREATE_TABLE1 + " Created" );
            db.execSQL(SQL_CREATE_TABLE2 );
            System.out.println(SQL_CREATE_TABLE2 + " Created" );


        }
    @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_TABLE1 );
        db.execSQL(SQL_DELETE_TABLE2);
            onCreate(db);
        }
    @Override
        public void onDowngrade(SQLiteDatabase db,int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

    }



