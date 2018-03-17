package com.castingmob;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description : This is the base project class to obtain
               base Application context
 Date        : 12/01/16
 ==============================================
 */

import android.content.Context;
import android.support.annotation.StringRes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastingMob {

    private static final CastingMob castingMob = new CastingMob();
    private Context mContext;

    private CastingMob() {}

    public static CastingMob getInstance() {
        return castingMob;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }

    public static Logger getLogger(){
        return LoggerFactory.getLogger("casting_logs");
    }

    public static String getText(@StringRes int string){
        return CastingMob.getInstance().getContext().getString(string);
    }
}
