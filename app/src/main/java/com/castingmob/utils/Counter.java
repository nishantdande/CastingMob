package com.castingmob.utils;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 17/01/16
 ==============================================
 */

public class Counter {

    private static final Counter mCounter = new Counter();

    public static int instanceCounter = 0;

    int counter = 0;

    public static Counter getInstance() {
        return mCounter;
    }

    public void increment()
    {
        ++instanceCounter;
        counter = instanceCounter;
    }

    public void decrement()
    {
        --instanceCounter;
        counter = instanceCounter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
        instanceCounter = this.counter;
    }
}
