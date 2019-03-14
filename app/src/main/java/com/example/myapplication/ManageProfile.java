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

import com.example.common.UserProfileData;
import com.example.dbUtils.UserProfileDBManager;
import com.example.common.ManageProfileAdapter;
import com.example.models.ProfileModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.common.ManageProfileAdapter.FIRST_COLUMN;
import static com.example.common.ManageProfileAdapter.SECOND_COLUMN;

public class ManageProfile extends AppCompatActivity {

    ListView listView;

    public static UserProfileDBManager dbManager;
    static public ArrayList<HashMap<String, String>> list = new ArrayList<>();
    com.example.common.ManageProfileAdapter ManageProfileAdapter;

    public void initiateDB(Context context){
        dbManager =  new UserProfileDBManager(context);
        dbManager.open();
        System.out.println("connection opened");
    }
    public static List<HashMap<String, String>> loadDataSet(){
        list.clear();
        for ( ProfileModel f: UserProfileData.profiles) {
            HashMap<String, String> hashmap = new HashMap<String, String>();
            hashmap.put(FIRST_COLUMN, f.getProfileName());
            hashmap.put(SECOND_COLUMN, String.valueOf(f.getTotalDailyTarget()));

            list.add(hashmap);
        }
        return list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_profiles);
        initiateDB(this);
        listView = (ListView) findViewById(R.id.ProfileListview);
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonProfile);
        addButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ManageProfile.this);
                                             final View view = getLayoutInflater().inflate(R.layout.manage_fluid_add_fluid_dialog, null);
                                             dialogBuilder.setTitle("Add Profile");
                                             dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialog, int which) {
                                                     ProfileModel profile= new ProfileModel();
                                                     TextView text1 = (TextView) view.findViewById(R.id.profileName);
                                                     profile.setProfileName(text1.getText().toString());
                                                     TextView text2= (TextView) view.findViewById(R.id.profileDailyTarget);
                                                     int num = Integer.parseInt(text2.getText().toString());
                                                     profile.setTotalDailyTarget(num);
                                                     dbManager.insertProfileEntry(profile);
                                                     MainActivity.loadProfiles(ManageProfile.dbManager);
                                                     loadDataSet();
                                                     ManageProfileAdapter.notifyDataSetChanged();


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




        MainActivity.loadFluids(ManageFluid.dbManager);
        loadDataSet();
        ManageProfileAdapter = new ManageProfileAdapter(this, list);
        if (list.size()==0){
            listView.setEmptyView(findViewById(R.id.emptyList));}
        listView.setAdapter(ManageProfileAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                final HashMap<String,String> map = list.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ManageProfile.this);
                final ProfileModel profileOld = new ProfileModel();

                final View mview = getLayoutInflater().inflate(R.layout.manage_profile_add_profile_dialog,null);
                final TextView text1 = (TextView) mview.findViewById(R.id.addDialogProfileName);
                text1.setText(map.get("ProfileName"));

                final TextView text2 = (TextView) mview.findViewById(R.id.addDialogProfileDailyTarget);
                text2.setText(map.get("TotalTarget"));
                profileOld.setProfileName(map.get("ProfileName"));
                profileOld.setTotalDailyTarget(Integer.parseInt(map.get("TotalTarget")));
                builder.setTitle("Update Profile");
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                ProfileModel profileNew = new ProfileModel();
                                profileNew.setProfileName(text1.getText().toString());
                                profileNew.setTotalDailyTarget(Integer.parseInt(text2.getText().toString()));
                                dbManager.updateUserProfileDBEntry(profileOld,profileNew);
                                MainActivity.loadProfiles(dbManager);
                                loadDataSet();
                                ManageProfileAdapter.notifyDataSetChanged();
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
