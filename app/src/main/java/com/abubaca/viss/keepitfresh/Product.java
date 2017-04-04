package com.abubaca.viss.keepitfresh;

import java.util.Date;

/**
 * Created by viss on 4/3/17.
 */

public class Product {

    private String name;
    private int duration;
    private Date inputDate;

    public Product(String name , int duration){
        this.name = name;
        this.duration = duration;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name =newName;
    }

    public Integer getDuration(){
        return this.duration;
    }

    public void setDuration(int newDuration){
        this.duration = newDuration;
    }

    public Date getInputDate(){
        return this.inputDate;
    }

    public void setInputDate(Date inputDate){
        this.inputDate = inputDate;
    }


}
