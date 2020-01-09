package com.example.shirazikudou.carrentproject;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class customerAdapter extends ArrayAdapter<Customer> {
    private Activity context;
    private int resource;
    private List<Customer> listuser;

    public customerAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Customer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listuser = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        TextView tv1 = (TextView) v.findViewById(R.id.textView23);
        TextView tv2 = (TextView) v.findViewById(R.id.textView24);
        TextView tv3 = (TextView) v.findViewById(R.id.textView25);
        TextView tv4 = (TextView) v.findViewById(R.id.textView22);
        TextView tv5 = (TextView) v.findViewById(R.id.textView21);
        TextView tv6 = (TextView) v.findViewById(R.id.textView27);
        TextView tv7 = (TextView) v.findViewById(R.id.textView28);
        TextView tv8 = (TextView) v.findViewById(R.id.textView29);
        TextView tv9 = (TextView) v.findViewById(R.id.textView30);
        TextView tv10 = (TextView) v.findViewById(R.id.textView31);

        tv1.setText(listuser.get(position).getcName());
        tv2.setText(listuser.get(position).getcContact());
        tv3.setText(listuser.get(position).getcUsetime());
        tv4.setText(listuser.get(position).getcBookingtime());
        tv5.setText(listuser.get(position).getcLicence());
        tv6.setText("NAME");
        tv7.setText("CONTACT");
        tv8.setText("TIME");
        tv9.setText("DATE");
        tv10.setText("LICENCE");

        return v;

    }
}
