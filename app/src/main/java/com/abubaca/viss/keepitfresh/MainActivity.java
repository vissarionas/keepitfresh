package com.abubaca.viss.keepitfresh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                populateProductList(dataSnapshot);
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

    private void populateProductList(DataSnapshot dataSnapshot){
        List<Product> products = new ArrayList<>();
        products.addAll(new Queries().listPromoteProducts(dataSnapshot));
        products.addAll(new Queries().listExpiredProducts(dataSnapshot));

        ListView productLV = (ListView)findViewById(R.id.product_LV);
        ProductListAdapter adapter = new ProductListAdapter(this , products);
        productLV.setAdapter(adapter);
    }
}