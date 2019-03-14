package com.example.models;

import android.provider.BaseColumns;

public final class DBModel
{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DBModel() {}

    /* Inner class that defines the table contents */
    public static class TrackHistory implements BaseColumns {
        public static final String TABLE1_NAME = "DAILY_LOG";

        public static final String TABLE1_COLUMN1 = "LIQUID_ID";
        public static final String TABLE1_COLUMN2 = "INTAKE";
        public static final String TABLE1_COLUMN3 = "TARGET";
        public static final String TABLE1_COLUMN4 = "DATE";
        public static final String TABLE1_COLUMN5 = "TIME";
        public static final String TABLE1_COLUMN6 = "PROFILE_ID";

    }
    /* Inner class that defines the table contents */
    public static class ManageFluids implements BaseColumns {
        public static final String TABLE2_NAME = "MANAGE_FLUIDS";
        public static final String TABLE2_COLUMN1 = "LIQUID_NAME";
        public static final String TABLE2_COLUMN2 = "TARGET";
        public static final String TABLE2_COLUMN3 = "TIME_STAMP";
        public static final String TABLE2_COLUMN4 = "PROFILE_ID";


    }
    /* Inner class that defines the table contents */
    public static class UserProfile implements BaseColumns {
        public static final String TABLE3_NAME = "USER_PROFILE";
        public static final String TABLE3_COLUMN1 = "PROFILE_NAME";
        public static final String TABLE3_COLUMN2 = "DAILY_TARGET";
        public static final String TABLE3_COLUMN3= "WEIGHT";
        }
    /* Inner class that defines the table contents */
    public static class ManageWeight implements BaseColumns {
        public static final String TABLE4_NAME = "MANAGE_WEIGHT";
        public static final String TABLE4_COLUMN1 = "PROFILE";
        public static final String TABLE4_COLUMN2 = "WEIGHT";
        public static final String TABLE4_COLUMN3 = "DATE";
        public static final String TABLE4_COLUMN4 = "TIME";
    }
}
