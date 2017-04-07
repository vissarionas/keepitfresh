package com.abubaca.viss.keepitfresh;

/**
 * Created by viss on 4/4/17.
 */

public class Product {

    String name;
    int duration;

    public Product(String name, int duration){
        this.name = name;
        this.duration = duration;
    }

    public String getName(){
        return  this.name;
    }

    public int getDuration(){
        return this.duration;
    }
}
