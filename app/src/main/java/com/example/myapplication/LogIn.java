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


public class LogIn extends AppCompatActivity {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference user_ref = myRef.child("user");
    EditText email_field;
    EditText password_field;
    HashMap<String, String> s;
    public static final ArrayList<User> users= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askaccount);

        Button login = findViewById(R.id.btn_login);
        Button checking = findViewById(R.id.btn_print);
        email_field = findViewById(R.id.field_email);
        password_field = findViewById(R.id.field_password);

       s = new HashMap<>();

        user_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                while(child.hasNext()){
                    DataSnapshot temp = child.next(); //각각의 유저 객체

                    // 파싱
                    StringTokenizer tk = new StringTokenizer(temp.getValue().toString(), ", ");
                    String[] arr = new String[5];
                    int i=0;
                    while(tk.hasMoreTokens()){
                        String[] dummy = tk.nextToken().split("=");
                        arr[i] = dummy[1];
                        i++;
                    }
                    arr[4] = arr[4].substring(0, arr[4].length()-1);

                    //유저 객체 생성
                    User user = new User(Integer.parseInt(arr[0]), arr[1], arr[2], arr[3], arr[4]);
                    users.add(user);

                    // set 이용해서 회원가입 진행
                    s.put(arr[4], arr[0]);
//                    Log.d("e: ", arr[4]);
//                    Log.d("p: ", arr[0]);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                String given_email = email_field.getText().toString();
                String given_password = password_field.getText().toString();

                if(s.keySet().contains(given_email)){
                    if(s.get(given_email).equals(given_password)){
                        startActivity(new Intent(getApplicationContext(), Main.class));
                    }
                }else{
                    Log.d("error: ", "noooo");
                }

                break;

            case R.id.btn_ask_sign_up:
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
        }
    }
}
