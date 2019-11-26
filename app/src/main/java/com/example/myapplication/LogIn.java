package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.User;
import com.example.myapplication.Main.Main;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;


public class LogIn extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference user_ref = myRef.child("user");
    EditText email_field;
    EditText password_field;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askaccount);

        Button login = findViewById(R.id.btn_login);
        email_field = findViewById(R.id.field_email);
        password_field = findViewById(R.id.field_password);
        email_field.setOnClickListener(this);
        password_field.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                final String given_email = email_field.getText().toString();
                final String given_password = password_field.getText().toString();

                        user_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                                while(child.hasNext()){
                                    DataSnapshot temp = child.next(); //각각의 유저 객체

                                    if(temp.child("email").getValue().toString().equals(given_email)){
                                        if(temp.child("password").getValue().toString().equals(given_password)){
                                            // 유저 정한다
                                            User user = User.getInstance();
                                            temp.child("age").getValue();

                                            user.setU_id(temp.getKey());

                                            user.setData(Integer.parseInt(temp.child("age").getValue().toString()), given_email,
                                                    temp.child("name").getValue().toString(), given_password, temp.child("sex").getValue().toString());

                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        startActivity(new Intent(getApplicationContext(), Main.class));
                break;

            case R.id.btn_ask_sign_up:
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
                break;
            case R.id.field_email:
                email_field.setText("");
                break;
            case R.id.field_password:
                password_field.setText("");
                break;
        }
    }
}
