package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dbUtils.ManageFluidsDBManager;
import com.example.dbUtils.FluidTrackerData;
import com.example.common.FluidTrackerModel;
import com.example.common.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static com.example.common.ListViewAdapter.FIRST_COLUMN;
import static com.example.common.ListViewAdapter.SECOND_COLUMN;

public class ManageFluid extends AppCompatActivity {


    ListView listView;

public static ManageFluidsDBManager dbManager;

    static public ArrayList<HashMap<String, String>> list = new ArrayList<>();
    ListViewAdapter ListViewAdapter;

    public static void initiateDB(Context context){
        dbManager =  new ManageFluidsDBManager(context);
        dbManager.open();
        System.out.println("connection opened");
        }
        public static List<HashMap<String, String>> loadDataSet(){
            list.clear();
        for (FluidTrackerModel f: FluidTrackerData.fluids) {
            HashMap<String, String> hashmap = new HashMap<String, String>();
            hashmap.put(FIRST_COLUMN, f.getFluidName());
            hashmap.put(SECOND_COLUMN, String.valueOf(f.getTarget()));

            list.add(hashmap);
        }
        return list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_fluid);
        initiateDB(this);
        listView = (ListView) findViewById(R.id.listview);
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ManageFluid.this);
                                             final View view = getLayoutInflater().inflate(R.layout.manage_fluid_add_fluid_dialog, null);
                                             dialogBuilder.setTitle("Add Fluid");
                                             dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialog, int which) {
                                                     FluidTrackerModel fluid = new FluidTrackerModel();
                                                     TextView text1 = (TextView) view.findViewById(R.id.fluidName);
                                                     fluid.setFluidName(text1.getText().toString());
                                                     TextView text2= (TextView) view.findViewById(R.id.fluiddailytarget);
                                                     int num = Integer.parseInt(text2.getText().toString());
                                                     fluid.setTarget(num);
                                                     dbManager.insertManageFluidsEntry(fluid);
                                                     MainActivity.loadFluids(dbManager);
                                                     loadDataSet();
                                                     ListViewAdapter.notifyDataSetChanged();


                                                 }
                                             });
                                             dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialog, int which) {
                                                     dialog.dismiss();

                                                 }
                                             });


                                             dialogBuilder.setView(view);
                                             AlertDialog alert = dialogBuilder.create();
                                             alert.show();


                                         }

                                     }
        );




        MainActivity.loadFluids(dbManager);
        loadDataSet();
        ListViewAdapter = new ListViewAdapter(this, list);
        if (list.size()==0){
        listView.setEmptyView(findViewById(R.id.emptyList));}
        listView.setAdapter(ListViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                final HashMap<String,String> map = list.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ManageFluid.this);
                final FluidTrackerModel fluidOld = new FluidTrackerModel();

                final View mview = getLayoutInflater().inflate(R.layout.manage_fluid_add_fluid_dialog,null);
                final TextView text1 = (TextView) mview.findViewById(R.id.fluidName);
                text1.setText(map.get("FluidName"));

                final TextView text2 = (TextView) mview.findViewById(R.id.fluiddailytarget);
                text2.setText(map.get("Target"));
                fluidOld.setFluidName(map.get("FluidName"));
                fluidOld.setTarget(Integer.parseInt(map.get("Target")));
                builder.setTitle("Update Fluid");
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                FluidTrackerModel fluidNew = new FluidTrackerModel();
                                TextView text1 = (TextView) mview.findViewById(R.id.fluidName);
                                fluidNew.setFluidName(text1.getText().toString());
                                TextView text2= (TextView) mview.findViewById(R.id.fluiddailytarget);
                                fluidNew.setTarget(Integer.parseInt(text2.getText().toString()));
                                dbManager.updateManageFluidsDBEntry(fluidOld,fluidNew);
                                MainActivity.loadFluids(dbManager);
                                loadDataSet();
                                ListViewAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),"Updated successfully", Toast.LENGTH_LONG).show();
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





    }



}