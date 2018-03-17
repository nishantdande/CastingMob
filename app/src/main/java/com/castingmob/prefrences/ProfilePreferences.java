package com.castingmob.prefrences;


import com.castingmob.Feed.model.Filter;
import com.castingmob.account.model.Profile;
import com.google.gson.Gson;

/**
 * Created by on 11/07/15.
 */
public class ProfilePreferences extends SharedPreferencesUtils {

    private static ProfilePreferences profilePreferences;

    public static ProfilePreferences getInstance() {

        if (profilePreferences == null) {
            profilePreferences = new ProfilePreferences();
            profilePreferences.initPreferences();
        }

        return profilePreferences;
    }

    @Override
    protected String getFileName() {
        return "profile_info";
    }

    public void setIsLoggedIn(){
        setBoolean("isLoggedIn", true);
    }

    public boolean isLoggedIn(){
        return getBoolean("isLoggedIn",false);
    }

    public void setProfileInfo(Profile profile){
        setString("profile", new Gson().toJson(profile));
    }

    public void setFilterInfo(Filter filter){
        setString("filter", new Gson().toJson(filter));
    }

    public Filter getFilterInfo(){
        String filterString = getString("filter",null);

        if (filterString != null){
            return new Gson().fromJson(filterString, Filter.class);
        }
        return null;
    }

    public Profile getProfileInfo(){
        String profileString = getString("profile",null);

        if (profileString != null){
            return new Gson().fromJson(profileString, Profile.class);
        }
        return null;
    }
}
