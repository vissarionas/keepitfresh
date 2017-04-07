package com.abubaca.viss.keepitfresh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MAIN_ACTIVITY";

    Button newEntryBtn;
    DatabaseReference database;
    Spinner listViewSpinner;
    List<Product> products;
    Queries queries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        products = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                queries = new Queries(dataSnapshot);
                products.clear();
                products.addAll(queries.listProducts());
                populateProductList(products);
                setListViewSpinner();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        newEntryBtn = (Button)findViewById(R.id.new_entry_btn);
        newEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewEntry.class));
            }
        });
    }

    private void populateProductList(List<Product> products){
        ListView productLV = (ListView)findViewById(R.id.product_LV);
        ProductListAdapter adapter = new ProductListAdapter(this , products);
        productLV.setAdapter(adapter);
    }

    private void setListViewSpinner(){
        List<String> views = new ArrayList<>();
        views.add("ALL");
        views.add("PROMOTE");
        views.add("EXPIRED");
        listViewSpinner = (Spinner)findViewById(R.id.list_view_spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item , views);
        listViewSpinner.setAdapter(adapter);
        listViewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case (0):
                        products.clear();
                        products.addAll(queries.listProducts());
                        populateProductList(products);
                        break;
                    case (1):
                        products.clear();
                        products.addAll(queries.listPromoteProducts());
                        populateProductList(products);
                        break;
                    case (2):
                        products.clear();
                        products.addAll(queries.listExpiredProducts());
                        populateProductList(products);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}