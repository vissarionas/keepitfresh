package com.abubaca.viss.keepitfresh;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by viss on 4/4/17.
 */

public class SpinnerAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    TextView itemTV;
    List<String> objects;
    Context context;

    public SpinnerAdapter(Context context, List<String> objects) {
        this.objects = objects;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.spinner_item , parent , false);
        itemTV = (TextView)convertView.findViewById(R.id.item_tv);
        itemTV.setText(getItem(position).toString());
        return convertView;
    }
}
