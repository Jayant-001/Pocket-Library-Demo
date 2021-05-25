package com.jayant.pocketlibrary.dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jayant.pocketlibrary.R;

import java.util.HashMap;
import java.util.Map;

public class AddBook extends AppCompatActivity {

    private CardView selectPdf;
    private TextView pdfName;
    private EditText pdfTitle;
    private Button uploadPdf;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        selectPdf = findViewById(R.id.select_pdf);
        pdfName = findViewById(R.id.pdf_name);
        pdfTitle = findViewById(R.id.upload_pdf_title);
        uploadPdf = findViewById(R.id.upload_pdf_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference("books");
        storageReference = FirebaseStorage.getInstance().getReference("books");

        uploadPdf.setEnabled(false);

        selectPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdfFile();
            }
        });

    }


    private void selectPdfFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 12);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null ) {

            uploadPdf.setEnabled(true);
            pdfName.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));
            
            uploadPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (pdfTitle.getText().toString().equals("")){
                        pdfTitle.setError("Title required");
                        pdfTitle.requestFocus();
                        return;
                    }

                    uploadFileToFirebase(data.getData());
                }
            });
        }
    }

    private void uploadFileToFirebase(Uri data) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child(pdfTitle.getText().toString() + "-" + System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri = uriTask.getResult();

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Map<String, Object> data = new HashMap<>();
                data.put("name", pdfTitle.getText().toString());
                data.put("url", uri.toString());

                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("myBooks").push().setValue(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddBook.this, "user updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddBook.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


                PdfData pdfData = new PdfData(pdfTitle.getText().toString(), uri.toString(), 0);
                databaseReference.child(databaseReference.push().getKey()).setValue(pdfData);
                Toast.makeText(AddBook.this, "File is uploaded", Toast.LENGTH_SHORT).show();
                finish();
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploading Progress..." + (int) progress + "%");

            }
        });

    }

}