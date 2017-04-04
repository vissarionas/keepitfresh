package com.abubaca.viss.keepitfresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewEntry extends AppCompatActivity {

    private static final String TAG = "NEW_CATEGORY_ACTIVITY";
    DatabaseReference databaseReference;
    Button addCategoryBtn , addProductBtn , submitBtn;
    Spinner categorySpinner , productSpinner;
    String currentCategory , currentProduct;
    SpinnerAdapter productAdapter , categoryAdapter;
    EditText quantityET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }

    @Override
    protected void onResume() {
        super.onResume();
        categorySpinner = (Spinner)findViewById(R.id.category_spinner);
        productSpinner = (Spinner)findViewById(R.id.product_spinner);
        addCategoryBtn = (Button)findViewById(R.id.add_category_btn);
        addProductBtn = (Button)findViewById(R.id.add_product_btn);
        submitBtn = (Button)findViewById(R.id.submit_btn);
        quantityET = (EditText)findViewById(R.id.quantityET);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                List<String> categories = getChildren(dataSnapshot , "");
                categoryAdapter = new SpinnerAdapter(getApplicationContext() , categories);
                categorySpinner.setAdapter(categoryAdapter);
                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        currentCategory = categoryAdapter.getItem(position).toString();
                        List<String> codes = getChildren(dataSnapshot , currentCategory);
                        productAdapter = new SpinnerAdapter(getApplicationContext() , codes);
                        productSpinner.setAdapter(productAdapter);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        currentProduct = productAdapter.getItem(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PopupDialog(NewEntry.this).addCategory();
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!currentCategory.equals(""))
                    new PopupDialog(NewEntry.this).addProduct(currentCategory);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(quantityET.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if(!currentCategory.equals("") && !currentProduct.equals("") && quantity>0){
                    pushEntry(currentCategory , currentProduct , quantity);
                }
            }
        });
    }

    private void pushEntry(String category, String product , int quantity){
        databaseReference = databaseReference.getRoot().child(category).child(product).child("entry"+getDateStamp());
        databaseReference.child("Quantity").setValue(quantity);
        databaseReference.child("xaxaxa").setValue("e re");
        databaseReference = databaseReference.getRoot();
    }

    //    Outputs a List item with the children under the parent String parameter(used in onDataChange())
    private List<String> getChildren(DataSnapshot dataSnapshot , String parent){
        List<String> children = new ArrayList<>();
        for(DataSnapshot snapshot : dataSnapshot.child(parent).getChildren()){
            children.add(snapshot.getKey());
        }
        return children;
    }

    private String getDateStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date());
    }
}
