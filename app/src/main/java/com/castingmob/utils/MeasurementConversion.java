package com.castingmob.utils;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 23/01/16
 ==============================================
 */

import java.util.Locale;

public class MeasurementConversion {

    /**
     * Convert the Centimeter in Feet
     * @param centimeter
     * @return
     */
    public static String convertInFeet(int centimeter){
        int feet = (int) (centimeter/30.48);
        double inches = (centimeter/2.54) - ((int)feet * 12);
//        return String.format(Locale.ENGLISH,"%d cm [%d' %.2f'']", centimeter, feet, inches);
        return String.format(Locale.ENGLISH,"%d' %.2f''", feet, inches);
    }
}
