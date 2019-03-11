package com.example.common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.ManageFluid;
import com.example.myapplication.R;



public class ListViewAdapter extends BaseAdapter{

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    public static final String FIRST_COLUMN="FluidName";
    public static final String SECOND_COLUMN="Target";


    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
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
        // TODO Auto-generated method stub

        ViewHolder holder;

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.fluid_list_items, null);
            holder=new ViewHolder();
            holder.txtFirst=(TextView) convertView.findViewById(R.id.tvName);
            holder.txtSecond=(TextView) convertView.findViewById(R.id.tvId);
            holder.imgRemove = (ImageView) convertView.findViewById(R.id.imgRemove);
            convertView.setTag(holder);
        }else{

            holder=(ViewHolder) convertView.getTag();
        }

        HashMap<String, String> map=list.get(position);
        holder.txtFirst.setText(map.get(FIRST_COLUMN));
        holder.txtSecond.setText(map.get(SECOND_COLUMN));


// click listener for remove button
        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map=list.get(position);
                DBManager dbManager = new ManageFluid().getDbManager();
                dbManager.deleteManageFluidDBEntry(map.get(FIRST_COLUMN));
                MainActivity.loadFluids(dbManager);
                list= (ArrayList<HashMap<String, String>> )ManageFluid.loadDataSet();
                notifyDataSetChanged();
            }
        });


        return convertView;
    }

}
