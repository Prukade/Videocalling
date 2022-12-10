package com.example.video;

import  androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    //Firebase Declaration
    FirebaseAuth auth;
    EditText Email,Password,Name;
    Button SignUp,CreateAccount,Login;
    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Progress Bar
//        progressDialog=new ProgressDialog(com.example.VideoCallingApp.SignUp.this);

        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Wwe are Creating your Account");

        auth=FirebaseAuth.getInstance();
        Email=(EditText) findViewById(R.id.Email);
        Password=(EditText) findViewById(R.id.Password);
        Name=(EditText) findViewById(R.id.Name);

        SignUp=(Button) findViewById(R.id.Signup);
        Login=(Button)findViewById(R.id.SignIn);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail,password,name;

                progressDialog.show();
                mail= Email.getText().toString();
                password=Password.getText().toString();
                name=Name.getText().toString();
                User user=new User();
                user.setEmail(mail);
                user.setName(name);
                user.setPassword(password);

                auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            firebaseFirestore.collection("User")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            startActivity(new Intent(SignUp.this,LoginActivity.class));
                                        }
                                    });

                            Toast.makeText(SignUp.this, "Login successfuly" , Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SignUp.this, "Task.getException().getLocalizedMessage()" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUp.this,LoginActivity.class));
//            String email=Email.getText().toString();
//            String password=Password.getText().toString();
//
//                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();


            }
        });

    }
}