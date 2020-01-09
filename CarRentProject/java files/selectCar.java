package com.example.shirazikudou.carrentproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class selectCar extends AppCompatActivity {
    private TextView txt1;
    private ListView lst1;
    private ScrollView scrollView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase dB;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private carlist adapter;
    private ProgressDialog progressDialog;
    private List<CarsOwner> img_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        dB = FirebaseDatabase.getInstance();


        //RETRIEVE DATA FROM FIREBASE DATABASE ON CARSOWNER TABLE
        img_car = new ArrayList<>();
        lst1 = (ListView)findViewById(R.id.carListview);
        databaseReference = dB.getReference(rentmycar.DATABASE_PATH);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    CarsOwner cars = ds.getValue(CarsOwner.class);
                    img_car.add(cars);
                }
                adapter = new carlist(selectCar.this, R.layout.customlistcars,img_car);
                lst1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //SET THE LIST VIEW ON ITEM CLICK
        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
            }
        });

        //CHECK IF USER ALREADY LOGIN
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null){
                    Intent intent = new Intent(selectCar.this, MainActivity.class);
                    startActivity(intent);
                }else{}
            }
        };


        //sign Out text view method
        txt1 = (TextView) findViewById(R.id.textView26);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
    public void cancelBtn(View v){
        finish();
        startActivity(new Intent(getApplicationContext(), pagemain.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener !=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
