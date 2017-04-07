package com.abubaca.viss.keepitfresh;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viss on 4/7/17.
 */

public class Queries {

    private DateUtils dateUtils;
    private DatabaseReference databaseReference;
    private DataSnapshot dataSnapshot;

    public Queries(DataSnapshot dataSnapshot){
        this.dataSnapshot = dataSnapshot;
        dateUtils = new DateUtils();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private final static String TAG = "QUERIES";

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        if (dataSnapshot != null) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                categories.add(snapshot.getKey());
            }
        }
        return categories;
    }

    public List<String> getProducts(String category) {
        List<String> products = new ArrayList<>();
        if(dataSnapshot != null) {
            for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                if (categorySnapshot.getKey().equals(category)) {
                    if (categorySnapshot.hasChildren()) {
                        for (DataSnapshot productSnapshot : categorySnapshot.getChildren()) {
                            products.add(productSnapshot.getKey());
                        }
                    }
                    break;
                }
            }
        }
        return products;
    }

    public int getProductDuration(String product){
        Integer productDuration = 0;
        if(dataSnapshot != null) {
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

    public List<Product> listProducts(){
        List<Product> productsList = new ArrayList<>();
        String product, duration;
        if(dataSnapshot != null) {
            for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                if (categorySnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot productSnapshot : categorySnapshot.getChildren()) {
                        if (productSnapshot.getChildrenCount() > 0) {
                            product = productSnapshot.getKey();
                            duration = productSnapshot.child("duration").getValue().toString();
                            try {
                                productsList.add(new Product(product, Integer.parseInt(duration)));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return productsList;
    }

    public List<Product> listPromoteProducts(){
        List<Product> promoteProductsList = new ArrayList<>();
        String product , duration , promoteDate;

        if(dataSnapshot != null) {
            for(DataSnapshot categorySnapshot : dataSnapshot.getChildren()){
                if(categorySnapshot.hasChildren()){
                    for(DataSnapshot productSnapshot : categorySnapshot.getChildren()){
                        product = productSnapshot.getKey();
                        duration = productSnapshot.child("duration").getValue().toString();
                        if(productSnapshot.hasChild("entries")){
                            for(DataSnapshot entriesSnapshot : productSnapshot.child("entries").getChildren()){
                                if(entriesSnapshot.hasChildren()){
                                    for(DataSnapshot entrySnapshot : entriesSnapshot.getChildren()){
                                        if(entrySnapshot.getKey().equals("promote_date")){
                                            promoteDate = entrySnapshot.getValue().toString();
                                            if(promoteDate.equals(new DateUtils().getDateStamp())){
                                                try {
                                                    promoteProductsList.add(new Product(product , Integer.parseInt(duration)));
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

    public List<Product> listExpiredProducts(){
        List<Product> expiredProductsList = new ArrayList<>();
        String product , duration , expireDate;

        if(dataSnapshot != null) {
            for(DataSnapshot categorySnapshot : dataSnapshot.getChildren()){
                if(categorySnapshot.hasChildren()){
                    for(DataSnapshot productSnapshot : categorySnapshot.getChildren()){
                        product = productSnapshot.getKey();
                        duration = productSnapshot.child("duration").getValue().toString();
                        if(productSnapshot.hasChild("entries")){
                            for(DataSnapshot entriesSnapshot : productSnapshot.child("entries").getChildren()){
                                if(entriesSnapshot.hasChildren()){
                                    for(DataSnapshot entrySnapshot : entriesSnapshot.getChildren()){
                                        if(entrySnapshot.getKey().equals("expire_date")){
                                            expireDate = entrySnapshot.getValue().toString();
                                            if(expireDate.equals(new DateUtils().getDateStamp())){
                                                try {
                                                    expiredProductsList.add(new Product(product , Integer.parseInt(duration)));
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

    public void pushFifoEntry(String category , String product , int quantity , int duration){
        databaseReference = databaseReference.getRoot().child(category).child(product).child("entries").child("entry"+dateUtils.getDateTimeStamp());
        databaseReference.child("quantity").setValue(quantity);
        databaseReference.child("input_date").setValue(dateUtils.getDateStamp());
        databaseReference.child("promote_date").setValue(dateUtils.addDaysToDate(dateUtils.getDateStamp() , duration));
        databaseReference.child("expire_date").setValue(dateUtils.addDaysToDate(dateUtils.getDateStamp() , duration+1));
    }
}
