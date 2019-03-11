package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.DBHelper;
import com.example.common.DBManager;
import com.example.common.DBModel;
import com.example.common.FluidTrackerData;
import com.example.common.FluidTrackerModel;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class MainActivity extends AppCompatActivity {
    static Set<String> spinnerValues = new HashSet<>();
public static Map<String, Map<Integer,Integer>> map = new HashMap<>();
public static int totalIntake=0;
    public static int totalGoalIntake=0;
public static  ArrayAdapter<String> spinnetArr;
    private DBManager dbManager;
    public void initiateDB(Context context){
    dbManager =  new DBManager(context);
        dbManager.open();

}
public static void loadFluids(DBManager dbManager){
    //set fluids
    List<FluidTrackerModel> fluids = dbManager.getManageFluidsDBEntries("SELECT * FROM " + DBModel.ManageFluids.TABLE2_NAME);
    FluidTrackerData.fluids.clear();
    FluidTrackerData.fluids.addAll(fluids);
    loadSpinnnerValues();
}

public static void loadSpinnnerValues()
{spinnerValues.clear();
    //load Spinner values
    for (FluidTrackerModel f: FluidTrackerData.fluids)
        spinnerValues.add(f.getFluidName());

}    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateDB(this);


//display date n time in first row
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
// textView is the TextView view that should display it
        TextView textView = (TextView)findViewById(R.id.timeText);
        textView.setText(currentDateTimeString);

        textView = (TextView)findViewById(R.id.dateText);
        textView.setText(currentDateTimeString);

        Button track = (Button) findViewById(R.id.trackDrink);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.track_fluid_dialog,null);
                builder.setTitle("Track Fluid");
                Spinner spinner = (Spinner)mview.findViewById(R.id.spinner2);
        spinnerValues.addAll(Arrays.asList(getResources().getStringArray(R.array.fluid)));
        spinnetArr =  new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_item,spinnerValues.toArray(new String[0]));
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
                                Toast.makeText(getApplicationContext(),"Nothing Added!",Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });
                builder.setView(mview);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        Button history = (Button) findViewById(R.id.ManageFluids);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (MainActivity.this,ManageFluid.class));

            }
        });
        loadFluids(dbManager);

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
    }
    private void showTrackFluidDialog() {//custom dialog with spinner
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.add_fluid_dialog,null);
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
