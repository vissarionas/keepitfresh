package com.abubaca.viss.keepitfresh;

import java.util.Date;

/**
 * Created by viss on 4/4/17.
 */

public class Product {

    String name, category;
    Date inputDate , outputDate;
    int duration;

    public Product(String name, String category , Date inputDate , Date outputDate , int duration){
        this.name = name;
        this.category = category;
        this.inputDate = inputDate;
        this.outputDate = outputDate;
        this.duration = duration;
    }

    public String getName(){
        return  this.name;
    }
    public String getCategory(){
        return this.category;
    }
    public Date getInputDate(){
        return this.inputDate;
    }
    public Date getOutputDate(){
        return this.outputDate;
    }
    public int getDuration(){
        return this.duration;
    }
}
