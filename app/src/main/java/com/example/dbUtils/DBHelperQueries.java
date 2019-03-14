package com.example.dbUtils;

import com.example.models.DBModel;
import com.example.myapplication.MainActivity;

public class DBHelperQueries {


    public  static final String getFluidID(String name){
        return "SELECT _ID FROM " + DBModel.ManageFluids.TABLE2_NAME +
                " WHERE "+DBModel.ManageFluids.TABLE2_COLUMN1 +
                " = '"+name+"' AND "+ DBModel.ManageFluids.TABLE2_COLUMN4 +
                " = '"+ MainActivity.profileSpinner.getSelectedItemId() + "';";
    }

}
