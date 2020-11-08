package com.example.mohsinassaignment3;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context c;
    ArrayList<Device> al;
    MyAdapter(Context c,ArrayList al)
    {
        this.c=c;
        this.al=al;
    }


    @Override
    public int getCount() {
        return al.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Device d=al.get(position);
        View v1=((Activity)c).getLayoutInflater().inflate(R.layout.activity_my_adapter,null);
        TextView t1=v1.findViewById(R.id.t1);
        TextView t2=v1.findViewById(R.id.text2);
        ImageView im=v1.findViewById(R.id.image);
        t1.setText(d.getName());
        t2.setText(d.getDis());
        im.setImageResource(d.getImage());
        return v1;

    }
}