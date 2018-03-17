package com.castingmob;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 12/01/16
 ==============================================
 */

import android.app.Application;

import com.castingmob.database.CastingDatabase;
import com.castingmob.logger.Logger;
import com.castingmob.utils.ValueGenerator;

public class CastingMobApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // initialize Logger
        Logger.init(this);

        // Set the Project Application Context
        CastingMob.getInstance().setContext(this);

        // Set the basic values which will
        // require later in application
        ValueGenerator.getInstance().init();

        CastingDatabase.getInstance().createDatabase();

        // Set the base app url
//        NetworkManager.getInstance().setBaseUrl(getString(R.string.app_url));
    }
}
