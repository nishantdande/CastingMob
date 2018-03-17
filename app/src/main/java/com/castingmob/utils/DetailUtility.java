package com.castingmob.utils;

import android.text.TextUtils;

import com.castingmob.CastingMob;
import com.castingmob.R;
import com.castingmob.account.model.Profile;

/**
 * Created by nishantdande on 20/09/16.
 */

public class DetailUtility {

    public static String getGender(Profile.Gender gender){
        if (gender != null){
            switch (gender){
                case male:
                    return "Male";

                case female:
                    return "Female";

                case both:
                    return "Both";

                default:
                    return CastingMob.getText(R.string.profile_blank);
            }
        }
        return CastingMob.getText(R.string.profile_blank);
    }

    public static String getValue(Integer value){
        if (value != null && value>0){
            return String.valueOf(value);
        }

        return CastingMob.getText(R.string.profile_blank);
    }

    public static String getValue(String value){
        if (value != null && !TextUtils.isEmpty(value)){
            return String.valueOf(value);
        }

        return CastingMob.getText(R.string.profile_blank);
    }

    public static String getValue(Boolean value){
        if (value != null){
            if (value == true){
                return "YES";
            }else if(value == false )
                return "NO";
        }
        return CastingMob.getText(R.string.profile_blank);
    }

    public static String getValue(String smin, String smax){
        Integer minVal = 0;
        Integer maxVal = 0;
        if (smin != null || smax != null){
            Integer min = TextUtils.isEmpty(smin) ? 0 :Integer.valueOf(smin);
            Integer max = TextUtils.isEmpty(smax) ? 0 : Integer.valueOf(smax);
            return String.format(CastingMob.getInstance().getContext().
                    getString(R.string.profile_rates,(min > 0 ? min : minVal), (max > 0 ? max : maxVal)));
        }

        return CastingMob.getText(R.string.profile_rates_blank);
    }



}
