package com.example.common;

import com.example.models.ProfileModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserProfileData {

    public static Set<ProfileModel> profiles = new HashSet<>();

    public static void addProfile(ProfileModel profile){
        profiles.add(profile);

    }
    public static  void deleteFluid(ProfileModel profile){
        profiles.remove(profile);

    }

    public static  ProfileModel getProfiles(String name){
        Iterator<ProfileModel> ite=profiles.iterator();
        while(ite.hasNext()){
            ProfileModel temp = ite.next();
            if(temp.getProfileName().equalsIgnoreCase(name))
                return temp;

        }
        return new ProfileModel();
    }

}
