package com.castingmob.utils;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 09/03/16
 ==============================================
 */

import com.castingmob.R;

import java.util.ArrayList;

public class ValueGenerator {

    private final static ValueGenerator valueGenerator = new ValueGenerator();

    private ValueGenerator(){}

    public static ValueGenerator getInstance() {
        return valueGenerator;
    }

    public void init(){
        setAge();
        setHeight();
        setWeight();
        setOthers();
    }

    final ArrayList<String> height = new ArrayList<>();
    final ArrayList<String> weight = new ArrayList<>();
    final ArrayList<Integer> age = new ArrayList<>();
    final ArrayList<String> others = new ArrayList<>();

    public ArrayList<String> getHeight(){
        return height;
    }

    public ArrayList<String> getWeight(){
        return weight;
    }

    public ArrayList<Integer> getAge(){
        return age;
    }

    public void setHeight() {
        if (height.size() == 0){
            for (int i = 100; i <= 300; i++) {
                height.add(MeasurementConversion.convertInFeet(i));
            }
        }
    }

    public void setWeight() {
        if (weight.size() == 0){
            for (int i = 20; i <= 200; i++) {
                weight.add(String.format("%d pounds",i));
            }
        }
    }

    public void setAge() {
        if (age.size() == 0){
            for (int i = 18; i <= 80; i++) {
                age.add(i);
            }
        }
    }

    public ArrayList<String> getOthers() {
        return others;
    }

    public void setOthers() {
        for (int i = 1; i < 50; i++) {
            others.add(String.valueOf(i)+"''");
        }
    }
}

