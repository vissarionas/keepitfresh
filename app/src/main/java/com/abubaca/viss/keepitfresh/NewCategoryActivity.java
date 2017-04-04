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
import java.util.Iterator;
import java.util.List;

public class NewCategoryActivity extends AppCompatActivity {

    private static final String TAG = "NEW_CATEGORY_ACTIVITY";
    DatabaseReference databaseReference;
    Button addCategoryBtn;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
    }

    @Override
    protected void onResume() {
        super.onResume();
        categorySpinner = (Spinner)findViewById(R.id.category_spinner);
        addCategoryBtn = (Button)findViewById(R.id.add_category_btn);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> categories = getChildren(dataSnapshot , "");
                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext() , categories);
//                ArrayAdapter spinnerAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item , categories);
                categorySpinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PopupDialog(NewCategoryActivity.this).addCategory();
            }
        });
    }

//    Logs a List item with the children under the parent String parameter(used in onDataChange())
    private void logChildren(DataSnapshot dataSnapshot , String parent){
        List<String> children = new ArrayList<>();
        for(DataSnapshot snapshot : dataSnapshot.child(parent).getChildren()){
            children.add(snapshot.getKey());
        }
        Log.i(TAG , children.toString());
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
