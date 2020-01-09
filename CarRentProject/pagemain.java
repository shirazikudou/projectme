package com.example.shirazikudou.carrentproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class pagemain extends AppCompatActivity{
    private TextView txtV1, txtV2, txtV3, txtV4, txtV5,txtV6;
    private ImageView img1;
    private ListView listView;
    private List<Customer>customerList;
    private customerAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ProgressDialog progressDialog;
    private FirebaseDatabase db;
    private DatabaseReference dR;
    private FirebaseUser user;
    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagemain);

        //DEFINE DATABASE INSTANCES
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        UserID = user.getUid();
        db = FirebaseDatabase.getInstance();
        dR = db.getReference();

        //CHECKING USER ALREADY LOGIN OR NOT
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null){
                    finish();
                    firebaseAuth.signOut();
                    startActivity(new Intent(pagemain.this, MainActivity.class));
                }
            }
        };

        //DEFINE LAYOUT OBJECTS
        txtV1 = (TextView) findViewById(R.id.logutbtn);
        txtV1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                firebaseAuth.signOut();
                startActivity(new Intent(pagemain.this, MainActivity.class));
            }
        });
        txtV2 = (TextView) findViewById(R.id.textView13);
        txtV3 = (TextView) findViewById(R.id.textView15);
        txtV4 = (TextView) findViewById(R.id.textView16);
        txtV5 = (TextView) findViewById(R.id.textView17);
        txtV6 = (TextView) findViewById(R.id.textView18);
        img1 = (ImageView) findViewById(R.id.img2);

        // RETRIEVE DATA FROM FIREBASE DATABASE
        DatabaseReference ref = db.getReference("UserDetails").child("Matric_ID").child(UserID);
        Query query = ref.orderByChild(UserID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    UserDetails uD = ds.getValue(UserDetails.class); //CALL DATA MODEL
                        Picasso.get().load(uD.getImage()).resize(350,350).into(img1);
                        txtV2.setText(uD.getFullName());
                        txtV3.setText(uD.getEmail());
                        txtV4.setText(uD.getMatricNumber());
                        txtV5.setText(uD.getInasis());
                        txtV6.setText(uD.getCourse());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        customerList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.ls1);


        // get database reference from rentmycar.class
        DatabaseReference reff = db.getReference(bookcar.DATABASE_PATH_FB);

        //Displaying data into ListView on apps
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //FETCH DATA FROM FIREBASE DATABASE
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    //CALL DEFAULT CONSTRUCTOR FROM DATA MODEL TO RETRIEVE DATA FROM FIREBASE DATABASE
                    Customer i = snapshot.getValue(Customer.class);
                    customerList.add(i);
                }
                adapter = new customerAdapter(pagemain.this,R.layout.customlistbookacar,customerList);

                //SET ADAPTER FOR LIST VIEW
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //SET LIST VIEW WHEN GET SELECTED ITEM
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
            }
        });

    }


    public void rent_car(View v){
        finish();
        Intent intent = new Intent(this, rentmycar.class);
        startActivity(intent);
    }
    public void booAcar(View v){
        finish();
        Intent intent = new Intent(this, selectCar.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener !=null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}
