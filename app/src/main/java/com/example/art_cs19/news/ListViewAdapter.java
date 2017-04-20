package com.example.art_cs19.news;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by USER on 17/4/2560.
 */

public class ListViewAdapter extends ArrayAdapter<ContactAdapter> {
    Context context;
    public ListViewAdapter(Context context, int resource, List<ContactAdapter> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v = convertView;

        if(null == v){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }
        ContactAdapter product = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.img);
        TextView txtname = (TextView) v.findViewById(R.id.txtname);
        TextView txtphone = (TextView) v.findViewById(R.id.txtphone);

        ImageButton imageButton = (ImageButton) v.findViewById(R.id.btn1);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position == 0) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "028836621"));
                    context.startActivity(intent);
                }else if(position == 1) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "191"));
                    context.startActivity(intent);
                }else if(position == 2){
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "199"));
                    context.startActivity(intent);
                }else if(position == 3) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1155"));
                    context.startActivity(intent);
                }else if(position == 4) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1554"));
                    context.startActivity(intent);
                }else if(position == 5) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1197"));
                    context.startActivity(intent);
                }else if(position == 6) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1199"));
                    context.startActivity(intent);
                }else if(position == 7) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1192"));
                    context.startActivity(intent);
                }else if(position == 8) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1669"));
                    context.startActivity(intent);
                }else if(position == 9) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "192"));
                    context.startActivity(intent);
                }else if(position == 10) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1677"));
                    context.startActivity(intent);
                }else if(position == 11) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1130"));
                    context.startActivity(intent);
                }else{
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1125"));
                    context.startActivity(intent);
                }

            }
        });
        img.setImageResource(product.getImgId());
        txtname.setText(product.getName());
        txtphone.setText(product.getPhone());


        return v;
    }

}
