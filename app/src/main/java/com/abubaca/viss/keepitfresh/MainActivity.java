package com.abubaca.viss.keepitfresh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MAIN_ACTIVITY";

    Button newCategoryBtn , submitBtn;
    EditText input1 ,input2 ,input3 , input4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        newCategoryBtn = (Button)findViewById(R.id.new_category_btn);
        submitBtn = (Button)findViewById(R.id.submit_btn);
        input1 = (EditText)findViewById(R.id.input1);
        input2 = (EditText)findViewById(R.id.input2);
        input3 = (EditText)findViewById(R.id.input3);
        input4 = (EditText)findViewById(R.id.input4);

        final LinkedList<Integer> queue = new LinkedList<>();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            Integer int1 , int2 , int3 , int4;
            @Override
            public void onClick(View v) {
                if(!input1.getText().toString().equals("")) {
                    try {
                        int1 = Integer.parseInt(input1.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    insertToQueue(queue , int1);
                }
                if(!input1.getText().toString().equals("")) {
                    try {
                        int2 = Integer.parseInt(input2.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    insertToQueue(queue , int2);
                }
                if(!input1.getText().toString().equals("")) {
                    try {
                        int3 = Integer.parseInt(input3.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    insertToQueue(queue , int3);
                }
                if(!input1.getText().toString().equals("")) {
                    try {
                        int4 = Integer.parseInt(input4.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    insertToQueue(queue , int4);
                }

                Log.i(TAG , queue.toString());
            }
        });




        newCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , NewCategoryActivity.class));
            }
        });
    }

    private void insertToQueue(LinkedList<Integer> queue , Integer intNum){
        if(queue.size()>=4) queue.removeLast();
        queue.addFirst(intNum);
    }
}
