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

import java.util.ArrayList;
import java.util.List;

public class NewEntry extends AppCompatActivity {

    private static final String TAG = "NEW_CATEGORY_ACTIVITY";
    DatabaseReference databaseReference;
    Button addCategoryBtn , addProductBtn;
    Spinner categorySpinner , productSpinner;
    String currentCategory;
    SpinnerAdapter productAdapter , categoryAdapter;

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
                        Log.i(TAG , productAdapter.getItem(position).toString());
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
    }

    //    Outputs a List item with the children under the parent String parameter(used in onDataChange())
    private List<String> getChildren(DataSnapshot dataSnapshot , String parent){
        List<String> children = new ArrayList<>();
        for(DataSnapshot snapshot : dataSnapshot.child(parent).getChildren()){
            children.add(snapshot.getKey());
        }
        return children;
    }
}
