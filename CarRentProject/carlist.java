package com.example.shirazikudou.carrentproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class carlist extends ArrayAdapter<CarsOwner> {
    private Activity context;
    private int resource;
    private List<CarsOwner> listofcar;


    public carlist(@NonNull Activity context, @LayoutRes int resource, @NonNull List<CarsOwner> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listofcar = objects;
    }

    @Override
    public int getCount() {
        return listofcar.size();
    }

    @Nullable
    @Override
    public CarsOwner getItem(int position) {
        return listofcar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        ImageView img = (ImageView) v.findViewById(R.id.imgView);
        TextView tv1 = (TextView) v.findViewById(R.id.txt1);
        TextView tv2 = (TextView) v.findViewById(R.id.txt2);
        TextView tv3 = (TextView) v.findViewById(R.id.txt3);
        TextView tv4 = (TextView) v.findViewById(R.id.txt4);

        final CarsOwner c = (CarsOwner)this.getItem(position);
        Picasso.get().load(c.getImage()).resize(200,200).into(img);
        tv1.setText(c.getName());
        tv2.setText(c.getMerk());
        tv3.setText(c.getPlatNumber());
        tv4.setText(c.getContact());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBookCarActivity(c.getImage(),
                        c.getName(),
                        c.getMerk(),
                        c.getPlatNumber(),
                        c.getContact());
            }
        });

        return v;

    }
    private void openBookCarActivity(String...book){
        Intent i = new Intent (context,bookcar.class);

        i.putExtra("pict",book[0]);
        i.putExtra("name",book[1]);
        i.putExtra("merk",book[2]);
        i.putExtra("plat",book[3]);
        i.putExtra("contact",book[4]);

        context.startActivity(i);
    }
}
