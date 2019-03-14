package com.example.dbUtils;

import com.example.common.FluidTrackerModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FluidTrackerData {
    public static Set<FluidTrackerModel> fluids = new HashSet<>();

    public static void addFluid(FluidTrackerModel fluid){
    fluids.add(fluid);

    }
    public static  void deleteFluid(FluidTrackerModel fluid){
        fluids.remove(fluid);

    }

    public static  FluidTrackerModel getFluid(String name){
        Iterator <FluidTrackerModel>ite=fluids.iterator();
        while(ite.hasNext()){
        FluidTrackerModel temp = ite.next();
        if(temp.getFluidName().equalsIgnoreCase(name))
            return temp;

        }
        return new FluidTrackerModel();
    }

}
