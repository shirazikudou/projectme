package com.example.shirazikudou.carrentproject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class signUp extends AppCompatActivity{

    public static final String FB_STORAGE_PATH = "users/";
    public static final String FB_DATABASE_PATH = "UserDetails";
    public static final int REQUEST_CODE = 1234;
    private Uri imgUrl;
    private ImageButton iB;
    private EditText fn, eAddress, uPass, uMatric, uInasis, uCourse;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference myRef;
    private StorageReference storageReference;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initialize firebase object
        storageReference =FirebaseStorage.getInstance().getReference();
        myRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH ).child("Matric_ID");
        firebaseAuth = FirebaseAuth.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid(); // GENERATE UNIQUE ID FROM FIREBASE DATABASE


        //DEFINE LAYOUT OBJECTS
        iB = (ImageButton) findViewById(R.id.imageButton);
        fn = (EditText) findViewById(R.id.ed_fname);
        eAddress = (EditText) findViewById(R.id.ed_email);
        uPass = (EditText) findViewById(R.id.ed_pass);
        uMatric = (EditText) findViewById(R.id.ed_matric);
        uInasis = (EditText) findViewById(R.id.ed_dpp);
        uCourse = (EditText) findViewById(R.id.ed_course);

    }

    // REGISTER ALL THE USER DETAIL AND UPLOAD DATA TO FIREBASE DATABASE
    public  void userRegister(View v){

        if (imgUrl != null ) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Saving Data");
            dialog.show();

            //Get the storage reference
            StorageReference ref = storageReference.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUrl));

            //Add file to reference
            ref.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Dimiss dialog when success
                    dialog.dismiss();
                    //Display success toast msg
                    Toast.makeText(getApplicationContext(), "Details Successfully uploaded", Toast.LENGTH_LONG).show();


                    // get the user input
                    UserDetails userDetails = new UserDetails(
                            taskSnapshot.getDownloadUrl().toString()
                            ,fn.getText().toString()
                            ,eAddress.getText().toString()
                            ,uPass.getText().toString()
                            ,uMatric.getText().toString()
                            ,uInasis.getText().toString()
                            ,uCourse.getText().toString());

                    //Save information in to firebase database
                    myRef.child(UserId).child(uMatric.getText().toString()).setValue(userDetails);

                    //start Activity Page
                    finish();
                    Intent intent = new Intent(signUp.this, pagemain.class);
                    startActivity(intent);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //Dimiss dialog when error
                            dialog.dismiss();
                            //Display err toast msg
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload progress

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please Check Details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUrl = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUrl);
                iB.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void Cancelbtn(View v){
        finish();
        firebaseAuth.signOut();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void TakeImage(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);
    }
}
