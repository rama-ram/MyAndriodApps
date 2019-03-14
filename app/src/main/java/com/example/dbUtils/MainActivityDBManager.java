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

public class MainActivityDBManager implements IDBManager{



        public static DBHelper dbHelper;

        private Context context;

        private SQLiteDatabase db;

        public MainActivityDBManager(Context c) {
            context = c;
        }

        public MainActivityDBManager open() throws SQLException {
            dbHelper = new DBHelper(context);
            db = dbHelper.getWritableDatabase();
            return this;
        }

        public void close() {
            dbHelper.close();
        }


    }

