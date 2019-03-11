package com.example.common;

import android.provider.BaseColumns;

public final class DBModel
{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DBModel() {}

    /* Inner class that defines the table contents */
    public static class TrackHistory implements BaseColumns {
        public static final String TABLE1_NAME = "DAILY_LOG";
        public static final String TABLE1_COLUMN1 = "LIQUID_NAME";
        public static final String TABLE1_COLUMN2 = "QUANTITY";
        public static final String TABLE1_COLUMN3 = "TARGET";
        public static final String TABLE1_COLUMN4 = "TIME_STAMP";
        public static final String TABLE1_COLUMN5 = "PROFILE_NAME";

    }
    /* Inner class that defines the table contents */
    public static class ManageFluids implements BaseColumns {
        public static final String TABLE2_NAME = "MANAGE_FLUIDS";
        public static final String TABLE2_COLUMN1 = "LIQUID_NAME";
        public static final String TABLE2_COLUMN2 = "TARGET";
        public static final String TABLE2_COLUMN3 = "TIME_STAMP";
        public static final String TABLE2_COLUMN4 = "PROFILE_NAME";


    }
}
