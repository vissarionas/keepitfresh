package com.abubaca.viss.keepitfresh;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by viss on 4/4/17.
 */

public class ProductListAdapter extends BaseAdapter {

    private List<Product> products;
    private LayoutInflater layoutInflater;

    ProductListAdapter(Context context, List<Product> products){
        this.products = products;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        convertView = layoutInflater.inflate(R.layout.product_list_item , parent , false);
        TextView productListTV = (TextView)convertView.findViewById(R.id.product_list_TV);
        TextView expireDateTV = (TextView)convertView.findViewById(R.id.expire_date_TV);
        productListTV.setText(products.get(position).getName());
        expireDateTV.setText(String.valueOf(products.get(position).getDuration()));
        return convertView;
    }
}
