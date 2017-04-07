package com.abubaca.viss.keepitfresh;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by viss on 4/4/17.
 */

public class PopupDialog {

    Activity activity;
    DatabaseReference database;

    public PopupDialog(Activity activity){
        this.activity = activity;
        database = FirebaseDatabase.getInstance().getReference();
    }


    void addCategory(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        final EditText categoryET = new EditText(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        categoryET.setLayoutParams(params);
        categoryET.setHint("type new category");
        categoryET.setInputType(InputType.TYPE_CLASS_TEXT);
        categoryET.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
        categoryET.setSelection(categoryET.getText().length());
        dialogBuilder.setView(categoryET);
        dialogBuilder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String categoryName =categoryET.getText().toString();
                        if(!categoryName.equals("")){
                            database = database.child(categoryName);
                            database.setValue("");
                            database = database.getParent();
                        }
                    }
                });
        Dialog dialog = dialogBuilder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.setTitle(R.string.add_category);
        dialog.show();
    }

    void addProduct(final String categoryName){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.new_product_dialog, null);
        final EditText productET = (EditText)view.findViewById(R.id.productNameET);
        final EditText durationET = (EditText)view.findViewById(R.id.productDurationET);
        productET.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});

        dialogBuilder.setView(view);
        dialogBuilder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String productName = productET.getText().toString();
                        String productDuration = durationET.getText().toString();
                        if(!productName.equals("") && !productDuration.equals("")){
                            database = database.child(categoryName).child(productName).child("duration");
                            database.setValue(productDuration);
                            database = database.getParent();
                            new CustomToast(activity.getApplicationContext()).showToast(productName+" inserted" , "SUCCESS");
                        }
                    }
                });
        Dialog dialog = dialogBuilder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.setTitle(R.string.add_product);
        dialog.show();
    }
}
