package com.castingmob.utils;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 17/01/16
 ==============================================
 */

import android.text.TextUtils;
import android.widget.EditText;

public class Validation {

    /**
     * Check whether email address is valid
     * @param email
     * @return
     */
    public static final boolean isValidEmailAddress(String email){
        if (email != null)
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

        return false;
    }

    /**
     * Check whether edit text is empty or not.
     * @param string
     * @return
     */
    public static final boolean isEmpty(String string){

        if (string != null){
            return TextUtils.isEmpty(string);
        }

        return true;
    }
}
