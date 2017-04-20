package com.example.art_cs19.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by USER on 17/4/2560.
 */

public class GridViewAdpater extends ArrayAdapter<ContactAdapter> {
    public GridViewAdpater(Context context, int resource, List<ContactAdapter> objects) {
        super(context, resource, objects);
    }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if(null == v){
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.grid_item, null);
            }
            ContactAdapter product = getItem(position);
            ImageView img= (ImageView) v.findViewById(R.id.img);
            TextView txtname = (TextView) v.findViewById(R.id.txtname);
            TextView txtphone = (TextView) v.findViewById(R.id.txtphone);

            img.setImageResource(product.getImgId());
            txtname.setText(product.getName());
            txtphone.setText(product.getPhone());

            return v;
        }


    }

