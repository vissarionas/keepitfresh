package com.abubaca.viss.keepitfresh;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
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
        final EditText productET = new EditText(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        productET.setLayoutParams(params);
        productET.setHint("type new product");
        productET.setInputType(InputType.TYPE_CLASS_TEXT);
        productET.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
        productET.setSelection(productET.getText().length());
        dialogBuilder.setView(productET);
        dialogBuilder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String productName = productET.getText().toString();
                        if(!productName.equals("")){
                            database = database.child(categoryName).child(productName);
                            database.setValue("");
                            database = database.getParent();
                        }
                    }
                });
        Dialog dialog = dialogBuilder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.setTitle(R.string.add_product);
        dialog.show();
    }


}
