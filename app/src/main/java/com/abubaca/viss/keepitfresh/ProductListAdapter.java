package com.abubaca.viss.keepitfresh;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by viss on 4/4/17.
 */

public class ProductListAdapter extends BaseAdapter {

    List<Product> products;
    Activity activity;

    public ProductListAdapter(Activity activity, List<Product> products){
        this.products = products;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return convertView;
    }
}
