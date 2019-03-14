package com.example.common;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dbUtils.UserProfileDBManager;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ManageFluid;
import com.example.myapplication.ManageProfile;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageProfileAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    public static final String FIRST_COLUMN="ProfileName";
    public static final String SECOND_COLUMN="TotalTarget";




    public ManageProfileAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;


    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder{
        TextView txtFirst;
        TextView txtSecond;
        ImageView imgRemove;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ManageProfileAdapter.ViewHolder holder;

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.manage_fluids_list_items, null);
            holder=new ManageProfileAdapter.ViewHolder();
            holder.txtFirst=(TextView) convertView.findViewById(R.id.tvName);
            holder.txtSecond=(TextView) convertView.findViewById(R.id.tvId);
            holder.imgRemove = (ImageView) convertView.findViewById(R.id.imgRemove);
            convertView.setTag(holder);
        }else{

            holder=(ManageProfileAdapter.ViewHolder) convertView.getTag();
        }

        HashMap<String, String> map=list.get(position);
        holder.txtFirst.setText(map.get(FIRST_COLUMN));
        holder.txtSecond.setText(map.get(SECOND_COLUMN));


// click listener for remove button
        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map=list.get(position);
                UserProfileDBManager dbManager = ManageProfile.dbManager;
                String name = map.get(FIRST_COLUMN);
                dbManager.deleteProfileDBEntry(name);
                MainActivity.loadProfiles(ManageProfile.dbManager);
                list= (ArrayList<HashMap<String, String>> )ManageFluid.loadDataSet();
                notifyDataSetChanged();
            }
        });


        return convertView;
    }

}

