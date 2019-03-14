package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.dbUtils.ManageFluidsDBManager;
import com.example.dbUtils.MainActivityDBManager;
import com.example.dbUtils.UserProfileDBManager;
import com.example.models.DBModel;
import com.example.dbUtils.FluidTrackerData;
import com.example.common.FluidTrackerModel;
import com.example.common.UserProfileData;
import com.example.models.ProfileModel;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class MainActivity extends AppCompatActivity {


    static Set<String> spinnerValues = new HashSet<>();
    static Set<String> profileSpinnerValues = new HashSet<>();
    private int mYear, mMonth, mDay, mHour, mMinute;
public static Map<String, Map<Integer,Integer>> map = new HashMap<>();
public static int totalIntake=0;
    public static int totalGoalIntake=0;
    public static Spinner profileSpinner;

public static  ArrayAdapter<String> spinnetArr;
    public static  ArrayAdapter<String> profileSpinnerArr;
    public static MainActivityDBManager dbManager;

    public void initiateDB(Context context){
        dbManager =  new MainActivityDBManager(context);
        dbManager.open();
        System.out.println("connection opened");
    }
public static void loadProfiles(UserProfileDBManager dbManager){
    List<ProfileModel> profile = dbManager.getProfileDBEntries("SELECT * FROM " + DBModel.UserProfile.TABLE3_NAME +
            ";" );
    UserProfileData.profiles.clear();
    UserProfileData.profiles.addAll(profile);
    loadProfileSpinner();
    }
    public static void loadProfileSpinner()
    {
        profileSpinnerValues.clear();
        //load Spinner values
        for (FluidTrackerModel f: FluidTrackerData.fluids)
            profileSpinnerValues.add(f.getFluidName());

    }

    public static void loadFluids(ManageFluidsDBManager dbManager){
    //set fluids
    List<FluidTrackerModel> fluids = dbManager.getManageFluidsDBEntries("SELECT * FROM " + DBModel.ManageFluids.TABLE2_NAME +
            " where " +DBModel.ManageFluids.TABLE2_COLUMN4+" = "+MainActivity.profileSpinner.getSelectedItemId()+";" );
    FluidTrackerData.fluids.clear();
    FluidTrackerData.fluids.addAll(fluids);
    loadSpinnnerValues();
}

public static void loadSpinnnerValues()
{spinnerValues.clear();
    //load Spinner values
    for (FluidTrackerModel f: FluidTrackerData.fluids)
        spinnerValues.add(f.getFluidName());



}
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileSpinner = (Spinner) findViewById(R.id.profileSpinner);
        profileSpinnerValues.addAll(Arrays.asList(getResources().getStringArray(R.array.profiles)));
        profileSpinnerArr = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, spinnerValues.toArray(new String[0]));
        profileSpinnerArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileSpinner.setAdapter(profileSpinnerArr);



        initiateDB(this);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
if (mMinute<10)
{
        ((TextView)findViewById(R.id.timeText)).setText(mHour + ":0" + mMinute);}
else{
    ((TextView)findViewById(R.id.timeText)).setText(mHour + ":" + mMinute);}
        ((TextView)findViewById(R.id.dateText)).setText(mDay + "-" + (mMonth+ 1) + "-" + mYear);
        Button track = (Button) findViewById(R.id.trackDrink);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                final View mview = getLayoutInflater().inflate(R.layout.track_history_edit_dialog, null);
                builder.setTitle("Track Fluid");
                final Spinner spinner = (Spinner) mview.findViewById(R.id.spinner2);
                spinnerValues.addAll(Arrays.asList(getResources().getStringArray(R.array.fluid)));
                spinnetArr = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, spinnerValues.toArray(new String[0]));
                spinnetArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnetArr);

                final Button btnDatePicker = (Button) mview.findViewById(R.id.selectdate);

                btnDatePicker.setText(mDay + "-" + (mMonth+ 1) + "-" + mYear);
                final Button  btnTimePicker = (Button) mview.findViewById(R.id.selecttime);


                if(mMinute<10){
                    btnTimePicker.setText(mHour + ":0" + mMinute);
                }
                else{btnTimePicker.setText(mHour + ":" + mMinute);}

                btnDatePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            if (v == btnDatePicker) {

                                // Get Current Date
                                final Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR);
                                mMonth = c.get(Calendar.MONTH);
                                mDay = c.get(Calendar.DAY_OF_MONTH);


                                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {

                                                btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();
                            }
                            if (v == btnTimePicker) {

                                // Get Current Time
                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                if(mMinute<10){
                                                    btnTimePicker.setText(hourOfDay + ":0" + minute);
                                                }
                                                else{btnTimePicker.setText(hourOfDay + ":" + minute);}
                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();
                            }
                        }

                    }
                );
                btnTimePicker.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         if (v == btnDatePicker) {

                                                             // Get Current Date
                                                             final Calendar c = Calendar.getInstance();
                                                             mYear = c.get(Calendar.YEAR);
                                                             mMonth = c.get(Calendar.MONTH);
                                                             mDay = c.get(Calendar.DAY_OF_MONTH);


                                                             DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                                                                     new DatePickerDialog.OnDateSetListener() {

                                                                         @Override
                                                                         public void onDateSet(DatePicker view, int year,
                                                                                               int monthOfYear, int dayOfMonth) {

                                                                             btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                                                         }
                                                                     }, mYear, mMonth, mDay);
                                                             datePickerDialog.show();
                                                         }
                                                         if (v == btnTimePicker) {

                                                             // Get Current Time
                                                             final Calendar c = Calendar.getInstance();
                                                             mHour = c.get(Calendar.HOUR_OF_DAY);
                                                             mMinute = c.get(Calendar.MINUTE);

                                                             // Launch Time Picker Dialog
                                                             TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                                                                     new TimePickerDialog.OnTimeSetListener() {

                                                                         @Override
                                                                         public void onTimeSet(TimePicker view, int hourOfDay,
                                                                                               int minute) {
                                                                                if(mMinute<10){
                                                                                    btnTimePicker.setText(hourOfDay + ":0" + minute);
                                                                                }
                                                                             else{btnTimePicker.setText(hourOfDay + ":" + minute);}
                                                                         }
                                                                     }, mHour, mMinute, false);
                                                             timePickerDialog.show();
                                                         }
                                                     }

                                                 }
                );

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(((EditText) mview.findViewById(R.id.dailyAmount)).getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please add the drink and intake", Toast.LENGTH_LONG).show();
                        }
                        else{
                        FluidTrackerModel fluid = new FluidTrackerModel();
                        fluid.setFluidName(spinner.getSelectedItem().toString());
                        TextView text2 = (TextView) mview.findViewById(R.id.dailyAmount);
                        int num = Integer.parseInt(text2.getText().toString());
                        fluid.setIntake(num);
                        fluid.setDate(btnDatePicker.getText().toString());
                        fluid.setTime(btnTimePicker.getText().toString());
                            DrinkHistory.dbManager.insertFluidsLogDBEntry(fluid);
                        //load the progress bar based on this change


                        Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        //add values to DB
                    }}
                });
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(getApplicationContext(), "Nothing Added!", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });
                builder.setView(mview);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        Button history = (Button) findViewById(R.id.drinkHistory);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DrinkHistory.class));

            }
        });
        Button manageFluids = (Button) findViewById(R.id.ManageFluids);
        manageFluids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ManageFluid.class));

            }
        });

        Button manageProfile = (Button) findViewById(R.id.manageProfile);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ManageProfile.class));

            }
        });

        loadFluids(ManageFluid.dbManager);
    }


//Button history = (Button) findViewById(R.id.History);
//        history.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        startActivity(new Intent (MainActivity.this,EventHistory.class));
//
//    }
//});
//
//        Button addLiquid = (Button) findViewById(R.id.AddLiquid);
//        addLiquid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent (MainActivity.this, ManageFluid.class));
//
//            }
//        });
//displayGoals();

    private void showTrackFluidDialog() {//custom dialog with spinner
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.manage_fluid_add_fluid_dialog,null);
                builder.setTitle("Track Fluid");
                Spinner spinner = (Spinner)mview.findViewById(R.id.spinner2);

//                spinnrValues.addAll(Arrays.asList(getResources().getStringArray(R.array.fluid)));
        spinnetArr =  new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.fluid));
        spinnetArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnetArr);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Toast.makeText(getApplicationContext(),"Added successfully", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(getApplicationContext(),"No is clicked",Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });
                builder.setView(mview);
                AlertDialog dialog = builder.create();
                dialog.show();
//                builder.show();
            }



//    private void showTrackFluidDialog() {//simpl implementation
//        Button track = (Button) findViewById(R.id.trackDrink);
//        track.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(
//                        MainActivity.this);
//                builder.setTitle("Track Fluid");
//                builder.setMessage("Two Action Buttons Alert Dialog");
//                builder.setPositiveButton("OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                Toast.makeText(getApplicationContext(),"Added successfully", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                builder.setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                Toast.makeText(getApplicationContext(),"No is clicked",Toast.LENGTH_LONG).show();
//                            }
//                        });
//                builder.show();
//            }
//        });
//    }
    public void displayGoals(){

//        ListView listView = (ListView) findViewById(R.id.progressListView);
//        List<ProgressInfo> downloadInfo = new ArrayList<>();
//        for(int i = 0; i < FluidTrackerData.fluids.size(); ++i) {
//            downloadInfo.add(new ProgressInfo("File " + i, 1000));
//        }
//
//        listView.setAdapter(new ProgressListViewAdaptor(getApplicationContext(), R.id.progressListView, downloadInfo));
//        spinnetArr =  new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,spinnerValues.toArray(new String[0]));

//        LinearLayout lm; lm = (LinearLayout) findViewById(R.id.linearLayout);
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
//        // Create LinearLayout
//        LinearLayout ll = new LinearLayout(this);
//       ll.setOrientation(LinearLayout.HORIZONTAL);
//        params.setMargins(10, 10, 50, 50);
//
//       TextView total = new TextView(this);
//        total.setText("Total Daily goal/ Total Intake        ");
//        total.setLayoutParams(params);
//        ll.addView(total);
//
//        // Create TextView
//        TextView goal = new TextView(this);
//        goal.setText(totalGoalIntake+ " / " +  totalIntake );
//        goal.setLayoutParams(params);
//        ll.addView(goal);
//        // Create TextView
//        TextView ml = new TextView(this);
//        ml.setText("ml");
//        ml.setLayoutParams(params);
//        ll.addView(ml);
//
//        lm.addView(ll);
//        LinearLayout outerLL = new LinearLayout(this);
//        outerLL.setOrientation(LinearLayout.VERTICAL);
//        for(String s :spinnerValues)
//        {
//            // Create LinearLayout
//            LinearLayout innerLL = new LinearLayout(this);
//            innerLL.setOrientation(LinearLayout.HORIZONTAL);
//
//            // Create TextView
//            TextView liquid = new TextView(this);
//            liquid.setText(s);
//            liquid.setLayoutParams(params);
//            innerLL.addView(liquid);
//
//            // Create TextView
//            goal = new TextView(this);
//            goal.setText(getTotalIntake(s)+ " / " + getIntakeGoal(s));
//            goal.setLayoutParams(params);
//            innerLL.addView(goal);
//            outerLL.addView(innerLL);
//
//        }
//                lm.addView(outerLL);
        ListView listView = (ListView) findViewById(R.id.progressListView);
    }

    private String getTotalIntake(String s) {
        Map <Integer,Integer> m = map.get(s);
        Map.Entry<Integer,Integer> it =  m.entrySet().iterator().next();
        return it.getKey().toString();


    }
    private String getIntakeGoal(String s) {
        Map <Integer,Integer> m = map.get(s);
        Map.Entry<Integer,Integer> it =  m.entrySet().iterator().next();
        return it.getValue().toString();

    }




}
