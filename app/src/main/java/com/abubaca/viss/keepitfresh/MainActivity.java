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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        newEntryBtn = (Button)findViewById(R.id.new_entry_btn);
        newEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewEntry.class));
            }
        });
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                logCodes(dataSnapshot , "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void logCodes(DataSnapshot snapshot, String parent){
        List<String> codes = new ArrayList<>();
        for(DataSnapshot productsSnapshot : snapshot.child(parent).getChildren()){
            for(DataSnapshot codesSnapshot : snapshot.child(productsSnapshot.getKey()).getChildren()){
                String code = codesSnapshot.getKey();
                codes.add(code);
                if(code.contains("04")) Log.i(TAG , codesSnapshot.getRef().getParent().getKey().toString());
            }
        }
        Log.i(TAG , codes+"");
    }

    private void logDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        Log.i(TAG , currentDateandTime);
    }
}