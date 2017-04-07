package com.abubaca.viss.keepitfresh;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by viss on 4/7/17.
 */

public class CustomToast {

    Toast toast;
    Context context;
    LayoutInflater layoutInflater;

    public CustomToast(Context context){
        this.context = context;
        this.toast = new Toast(context);
    }

    public void showToast(String text, String state){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastView = layoutInflater.inflate(R.layout.toast_view , null);
        LinearLayout toastLL = (LinearLayout)toastView.findViewById(R.id.toastLL);
        TextView toastTV = (TextView)toastView.findViewById(R.id.toastTV);
        toastTV.setText(text);
        switch (state){
            case ("SUCCESS"):
                toastLL.setBackgroundColor(context.getResources().getColor(R.color.toast_success));
                break;
            case ("WARNING"):
                toastLL.setBackgroundColor(context.getResources().getColor(R.color.toast_warning));
                break;
        }
        toast.setView(toastView);
        toast.setGravity(Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

}
