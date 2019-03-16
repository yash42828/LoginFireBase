package com.example.hp.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private long backPressedTime;
    private Toast backToast;
    private FirebaseAuth firebaseAuth;
    String des = "Welcome";
    EditText edittext;
//    DatabaseHelper mybd = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
//        if(firebaseAuth.getCurrentUser() != null){
////            //Toast.makeText(MainActivity.this, "Error:Not Login", Toast.LENGTH_SHORT).show();
////            finish();
////            Intent i = new Intent(MainActivity.this,Display.class);
////            startActivity(i);
////
////        }



    }

    public void onClick(View vs) {
        if (vs.getId()==R.id.loginbtn){
            EditText user1 = (EditText) findViewById(R.id.user);
            String user = user1.getText().toString().trim();
            EditText pass1 = (EditText) findViewById(R.id.pass);
            String pass = pass1.getText().toString().trim();

            if(user.isEmpty() && pass.isEmpty()){
                Toast.makeText(MainActivity.this,"Please Enter all Details",Toast.LENGTH_LONG).show();
            }
            else if(user1.getText().toString().isEmpty())
            {
                user1.setError("Name Can't be Empty");
            }
            else if(pass1.getText().toString().isEmpty())
            {
                pass1.setError("Password Can't be Empty");
            }
            else{
                //firebaseAuth = FirebaseAuth.getInstance();
                edittext = (EditText) findViewById(R.id.user);
                  final String name = edittext.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(user,pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    //start profile activity
                                    Toast.makeText(MainActivity.this,"Logged in",Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent i = new Intent(MainActivity.this,Profile.class);
                                    //i.putExtra("Name",name);
                                    startActivity(i);


                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Enter Registered Email & Password",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }




           // Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();

//            String password = mybd.searchPass(user);
//            if(pass.equals(password))
//            {
//                edittext = (EditText) findViewById(R.id.user);
//                String name = edittext.getText().toString();
//                 Intent intent = new Intent(MainActivity.this,Display.class);
//                 intent.putExtra("Name",name);
//                 startActivity(intent);
//                Toast.makeText(this,"Login Success",Toast.LENGTH_LONG).show();
//
//            }
//            else
//            {
//                Toast.makeText(this,"Login Unsuccess",Toast.LENGTH_LONG).show();
//            }
           // Log.d("s1","Login Successful");
         //   Toast.makeText(this,"Login Success",Toast.LENGTH_LONG).show();
        }
        if(vs.getId()==R.id.tvreg){
            Intent i = new Intent(MainActivity.this,Registeration.class);
            startActivityForResult(i,1);
        }
    }



    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        EditText user1 = (EditText) findViewById(R.id.user);
        String user = user1.getText().toString().trim();
        if (requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(this, data.getStringExtra("u"), Toast.LENGTH_SHORT).show();
                user1.setText(data.getStringExtra("u"));
            }
            if (resultCode == RESULT_CANCELED)
            {
                //Write your code if there's no result
            }
        }
    }

}