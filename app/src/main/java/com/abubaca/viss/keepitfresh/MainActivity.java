package com.abubaca.viss.keepitfresh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MAIN_ACTIVITY";

    Button newEntryBtn;
    DatabaseReference database;
    DataSnapshot snapshot;

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
                removeEntries(dataSnapshot);
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

    private void removeEntries(DataSnapshot snapshot){
        for(DataSnapshot categorySnapshot : snapshot.child("").getChildren()){
            if(categorySnapshot.getChildrenCount()>0){
                for(DataSnapshot productSnapshot : categorySnapshot.getChildren()){
                    if(productSnapshot.getChildrenCount()>0){
                        String product = productSnapshot.getKey();
                        for(DataSnapshot featureSnapshot : productSnapshot.getChildren()){
                            if(featureSnapshot.getChildrenCount()>0){
                                for(DataSnapshot entrySnapshot : featureSnapshot.getChildren()){
                                    if((entrySnapshot.getKey().substring(0 , 5).equals("entry"))){
//                                        entrySnapshot.getRef().removeValue();
                                        if(entrySnapshot.child("input_date").exists()){
                                            Log.i(TAG , product+"\n"+entrySnapshot.child("input_date").getValue().toString());
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