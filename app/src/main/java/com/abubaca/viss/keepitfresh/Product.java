package com.abubaca.viss.keepitfresh;

/**
 * Created by viss on 4/4/17.
 */

public class Product {

    String name, description;
    int duration;

    public Product(String name, String description, int duration){
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public String getName(){
        return  this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public int getDuration(){
        return this.duration;
    }
}
