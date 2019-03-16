package com.example.hp.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Display extends AppCompatActivity implements View.OnClickListener {
    TextView tv;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private TextView welcome;
    //    private Toolbar welcome;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        tv = (TextView) findViewById(R.id.text);
       // tv.setText(getIntent().getStringExtra("Name"));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            //start profile activity
            finish();
            Intent i = new Intent(Display.this,MainActivity.class);
            startActivity(i);
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        welcome = (TextView) findViewById(R.id.text);
        String document_id = user.getUid();

        DocumentReference documentReference = firebaseFirestore.collection("users").document(document_id);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                welcome.setText("Welcome "+documentSnapshot.getString("username"));
            }
        });

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

    }
}
