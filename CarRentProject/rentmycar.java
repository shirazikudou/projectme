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
import android.widget.ImageView;
import android.widget.TextView;
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

public class rentmycar extends AppCompatActivity {
    public static final String STORAGE_PATH = "carsowner/";
    public static final String DATABASE_PATH = "CarsOwner";
    public static final int REQUEST_CODE = 12;
    private Uri uri;
    private ImageView img2;
    private EditText ed1, ed2, ed3, ed4;
    private TextView txt1;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference myReference;
    private StorageReference myStorRef;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentmycar);

        myStorRef =FirebaseStorage.getInstance().getReference();
        myReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH );
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //CHECK IF USER ALREADY LOGIN
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null){
                    finish();
                    firebaseAuth.signOut();
                    startActivity(new Intent(rentmycar.this, MainActivity.class));
                }
            }
        };

        img2 = (ImageView) findViewById(R.id.imageView2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);
            }
        });
        txt1 = (TextView) findViewById(R.id.sgnout_txt);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                firebaseAuth.signOut();
            }
        });
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);
        ed4 = (EditText) findViewById(R.id.editText4);
    }

    @SuppressWarnings("Test")
    public void submitRent(View v){
        if (uri != null ) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Saving Data");
            dialog.show();

            //Get the storage reference
            StorageReference ref = myStorRef.child(STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(uri));

            //Add file to reference
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Dimiss dialog when success
                    dialog.dismiss();

                    //Display success toast msg
                    Toast.makeText(getApplicationContext(), "Details uploaded", Toast.LENGTH_LONG).show();

                    //get Input data
                    CarsOwner carsOwner = new CarsOwner(
                            taskSnapshot.getDownloadUrl().toString(),
                            ed1.getText().toString(),
                            ed2.getText().toString(),
                            ed3.getText().toString(),
                            ed4.getText().toString());

                    //Save information in to firebase database
                    String iD = myReference.push().getKey();
                    myReference.child(iD).setValue(carsOwner);

                    //start Activity Page after finish rentmycar activity
                    finish();
                    Intent intent = new Intent(rentmycar.this, pagemain.class);
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
    protected void onActivityResult(int requestCode, int resultcode, Intent data){
        super.onActivityResult(requestCode, resultcode, data);
        if (requestCode == REQUEST_CODE && resultcode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                img2.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void cancelRent(View v){
        finish();
        Intent intent = new Intent(rentmycar.this, pagemain.class);
        startActivity(intent);
    }
    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
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
