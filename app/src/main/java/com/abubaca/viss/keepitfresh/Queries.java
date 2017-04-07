package com.abubaca.viss.keepitfresh;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viss on 4/7/17.
 */

public class Queries {

    DateUtils dateUtils;

    public Queries(){
        dateUtils = new DateUtils();
    }

    private final static String TAG = "QUERIES";

    public List<String> getCategories(DataSnapshot dataSnapshot){
        List<String> categories = new ArrayList<>();
        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
            categories.add(snapshot.getKey());
        }
        return categories;
    }

    public List<String> getProducts(DataSnapshot dataSnapshot , String category) {
        List<String> products = new ArrayList<>();
        for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
            if(categorySnapshot.getKey().equals(category)){
                if (categorySnapshot.hasChildren()) {
                    for (DataSnapshot productSnapshot : categorySnapshot.getChildren()) {
                        products.add(productSnapshot.getKey());
                    }
                }
                break;
            }

        }
        return products;
    }

    public int getProductDuration(DataSnapshot dataSnapshot , String product){
        Integer productDuration = 0;
        if(dataSnapshot.hasChildren()){
            for(DataSnapshot categorySnapshot : dataSnapshot.getChildren()){
                if(categorySnapshot.hasChildren()){
                    for(DataSnapshot productSnapshot : categorySnapshot.getChildren()){
                        if(productSnapshot.getKey().equals(product)){
                            if(productSnapshot.hasChild("duration")){
                                try {
                                    productDuration = Integer.parseInt(productSnapshot.child("duration").getValue().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        return productDuration;
    }

    public List<Product> listProducts(DataSnapshot snapshot){
        List<Product> productsList = new ArrayList<>();
        String product, description, duration;

        for(DataSnapshot categorySnapshot : snapshot.getChildren()){
            if(categorySnapshot.getChildrenCount()>0){
                for(DataSnapshot productSnapshot : categorySnapshot.getChildren()){
                    if(productSnapshot.getChildrenCount()>0){
                        product = productSnapshot.getKey();
                        description = productSnapshot.child("descr").getValue().toString();
                        duration = productSnapshot.child("duration").getValue().toString();
                        Log.i(TAG , product+"  "+description+"  "+duration);
                        try {
                            productsList.add(new Product(product , description , Integer.parseInt(duration)));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return productsList;
    }

    public List<Product> listPromoteProducts(DataSnapshot snapshot){
        List<Product> promoteProductsList = new ArrayList<>();
        String product, description, duration , promoteDate;

        if(snapshot.hasChildren()){
            for(DataSnapshot categorySnapshot : snapshot.getChildren()){
                if(categorySnapshot.hasChildren()){
                    for(DataSnapshot productSnapshot : categorySnapshot.getChildren()){
                        product = productSnapshot.getKey();
                        description = productSnapshot.child("descr").getValue().toString();
                        duration = productSnapshot.child("duration").getValue().toString();
                        if(productSnapshot.hasChild("entries")){
                            for(DataSnapshot entriesSnapshot : productSnapshot.child("entries").getChildren()){
                                if(entriesSnapshot.hasChildren()){
                                    for(DataSnapshot entrySnapshot : entriesSnapshot.getChildren()){
                                        if(entrySnapshot.getKey().equals("promote_date")){
                                            promoteDate = entrySnapshot.getValue().toString();
                                            if(promoteDate.equals(new DateUtils().getDateStamp())){
                                                try {
                                                    promoteProductsList.add(new Product(product , description , Integer.parseInt(duration)));
                                                } catch (NumberFormatException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return promoteProductsList;
    }

    public List<Product> listExpiredProducts(DataSnapshot snapshot){
        List<Product> expiredProductsList = new ArrayList<>();
        String product, description, duration , expireDate;

        if(snapshot.hasChildren()){
            for(DataSnapshot categorySnapshot : snapshot.getChildren()){
                if(categorySnapshot.hasChildren()){
                    for(DataSnapshot productSnapshot : categorySnapshot.getChildren()){
                        product = productSnapshot.getKey();
                        description = productSnapshot.child("descr").getValue().toString();
                        duration = productSnapshot.child("duration").getValue().toString();
                        if(productSnapshot.hasChild("entries")){
                            for(DataSnapshot entriesSnapshot : productSnapshot.child("entries").getChildren()){
                                if(entriesSnapshot.hasChildren()){
                                    for(DataSnapshot entrySnapshot : entriesSnapshot.getChildren()){
                                        if(entrySnapshot.getKey().equals("expire_date")){
                                            expireDate = entrySnapshot.getValue().toString();
                                            if(expireDate.equals(new DateUtils().getDateStamp())){
                                                try {
                                                    expiredProductsList.add(new Product(product , description , Integer.parseInt(duration)));
                                                } catch (NumberFormatException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return expiredProductsList;
    }

    public void pushEntry(DatabaseReference databaseReference , String category , String product , int quantity , int duration){
        databaseReference = databaseReference.getRoot().child(category).child(product).child("entries").child("entry"+dateUtils.getDateTimeStamp());
        databaseReference.child("quantity").setValue(quantity);
        databaseReference.child("input_date").setValue(dateUtils.getDateStamp());
        databaseReference.child("promote_date").setValue(dateUtils.addDaysToDate(dateUtils.getDateStamp() , duration));
        databaseReference.child("expire_date").setValue(dateUtils.addDaysToDate(dateUtils.getDateStamp() , duration+1));
    }
}
