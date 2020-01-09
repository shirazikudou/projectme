package com.example.shirazikudou.carrentproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{
    private EditText ed1, ed2;
    private TextView txtForgot;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CHECK IF THE USER ALREADY LOGIN OR NOT
        mAuth = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    finish();
                    Intent intent = new Intent(getApplicationContext(),pagemain.class);
                    startActivity(intent);
                }
            }
        };
        firebaseAuth = FirebaseAuth.getInstance();
        ed1 = (EditText) findViewById(R.id.ed_username);
        ed2 = (EditText) findViewById(R.id.ed_password);
        txtForgot = (TextView) findViewById(R.id.txt_forgot);
        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                firebaseAuth.signOut();
            }
        });

    }
    //LOGIN BUTTON
    public void LoginUser(View v){
        String email = ed1.getText().toString().trim();
        String password = ed2.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            ed1.setError("Please Input Email Address.");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ed2.setError("Please put your Password.");
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this,"Please wait ","Verify user....", true);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Succesful Sign In.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    //REGISTER BUTTON
    public void user_Register(View v){
        String email = ed1.getText().toString();
        String password = ed2.getText().toString();

        if (TextUtils.isEmpty(email)){
            ed1.setError("Please Input Email Address");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ed2.setError("Please Input your Password");
            return;
        }
        final  ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this,"Processing....", "Registering User..", true);

        //registering user to firebase
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(getApplicationContext(), signUp.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"Fail to Register", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuth);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuth!=null){
            firebaseAuth.removeAuthStateListener(mAuth);
        }
    }
}
