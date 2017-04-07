package com.abubaca.viss.keepitfresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewEntry extends AppCompatActivity {

    private static final String TAG = "NEW_CATEGORY_ACTIVITY";
    DatabaseReference databaseReference;
    Button addCategoryBtn , addProductBtn , submitBtn;
    Spinner categorySpinner , productSpinner;
    String currentCategory , currentProduct;
    SpinnerAdapter productAdapter , categoryAdapter;
    EditText quantityET;
    Queries queries;

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
                queries = new Queries(dataSnapshot);
                categoryAdapter = new SpinnerAdapter(getApplicationContext() , android.R.layout.simple_spinner_item, queries.getCategories());
                categorySpinner.setAdapter(categoryAdapter);
                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        currentCategory = categoryAdapter.getItem(position).toString();
                        productAdapter = new SpinnerAdapter(getApplicationContext() , android.R.layout.simple_spinner_item, queries.getProducts(currentCategory));
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
                    int duration = queries.getProductDuration(currentProduct);
                    queries.pushFifoEntry(currentCategory , currentProduct , quantity , duration);
                    quantityET.setText("");
                    new CustomToast(getApplicationContext()).showToast("New entry inserted" , "SUCCESS");
                }else{
                    new CustomToast(getApplicationContext()).showToast("Something missing" , "WARNING");
                }
            }
        });
    }




}
