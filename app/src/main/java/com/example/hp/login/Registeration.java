package com.example.hp.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;


import java.util.regex.Pattern;

public class Registeration extends AppCompatActivity{

 //   EditText name,email,password,address,phone;
    Button regbtn;

    private EditText email;
    private EditText name;
    private EditText phone;
    private EditText password;
    private EditText address;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
//    DatabaseHelper mybd;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z0-9])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    //DatabaseHelper helper=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        db = FirebaseFirestore.getInstance();

//        mybd = new DatabaseHelper(this);

        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        address = (EditText)findViewById(R.id.address);
        phone = (EditText)findViewById(R.id.phone);
        regbtn = findViewById(R.id.regbtn);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Name = name.getText().toString().trim();
                final String Email = email.getText().toString().trim();
                final String Password = password.getText().toString().trim();
                final String Address = address.getText().toString().trim();


                if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty()
                || address.getText().toString().isEmpty() || phone.getText().toString().isEmpty())
                {
                    Toast.makeText(Registeration.this, "Please Enter all Details", Toast.LENGTH_SHORT).show();
                }
               else if (name.getText().toString().isEmpty())
                {
                   // Toast.makeText(Registeration.this, "Please Enter all Details", Toast.LENGTH_SHORT).show();
                    name.setError("Name Can't be Empty");
                }
               else if (email.getText().toString().isEmpty())
                {
                    email.setError("Email Can't be Empty");
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                {
                    email.setError("Please Enter valid Email!");
                }
                else if (!PASSWORD_PATTERN.matcher(password.getText().toString()).matches())
                {
                    //password.setError("Password too weak");
                    password.setError("It must contain 1 special character and length should be minimum 4");
                }

                else {



                    AlertDialog.Builder builder=new AlertDialog.Builder(Registeration.this);

                    builder.setTitle("Register?");
                    builder.setMessage("Are you confirm");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                   // Toast.makeText(Registeration.this, "You pressed Yes!", Toast.LENGTH_SHORT).show();


                                    firebaseAuth = FirebaseAuth.getInstance();

                                    firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                                            .addOnCompleteListener(Registeration.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Registeration.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                                                        //Successflly registered
                                                        DB(Name, Email, Password, Address,firebaseAuth.getUid());
                                                    } else {
                                                        Toast.makeText(Registeration.this, "Error:Not Registered", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });




                                   // startActivity(new Intent(Registeration.this,MainActivity.class));


                                }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Registeration.this, "You pressed No!", Toast.LENGTH_SHORT).show();
                           //Toast.makeText(getApplicationContext(), name.toString(), Toast.LENGTH_SHORT).show();


                        }
                    });
                    builder.create().show();

                }

            }
        });
    }
    private void DB(String Name,String Email,String Password,String Address,String document_id){
        CollectionReference dbUsers = db.collection("users");
       // Toast.makeText(Registeration.this, "Data Arrived at DB", Toast.LENGTH_SHORT).show();

        Users user = new Users(Name,Email,Password,Address);
        dbUsers.document(document_id).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String user = email.getText().toString();
                            Toast.makeText(Registeration.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("u",user);
                            setResult(RESULT_OK,returnIntent);
                            finish();
//                        startActivity(new Intent(Registeration.this,MainActivity.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registeration.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

}