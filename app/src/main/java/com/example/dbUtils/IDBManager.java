package com.example.dbUtils;

import android.content.Context;
import android.database.SQLException;

interface IDBManager {
 IDBManager open() throws SQLException;

    void close();
}
