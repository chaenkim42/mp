package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.User;
import com.example.myapplication.Schedule.ScheduleForm;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class SignUp extends AppCompatActivity {
    public static EditText email, password, password_c, name, age, sex;
    public static Button btn_sign_up;
    public static TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // setting
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        password_c = findViewById(R.id.signup_password_confirm);
        name = findViewById(R.id.signup_name);
        age = findViewById(R.id.signup_age);
        sex = findViewById(R.id.signup_sex);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        textView = findViewById(R.id.textView);


        //회원가입
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = password.getText().toString();
                String pc = password_c.getText().toString();

                if (p.equals(pc)) {
                    // user initialization
                    User user = User.getInstance();
                    user.setData(Integer.parseInt(age.getText().toString()), email.getText().toString(), name.getText().toString(), password.getText().toString(),
                            sex.getText().toString());

//                    DatabaseReference temp_ref = user_ref.push();
//                    DatabaseReference email_ref = temp_ref.child("email");
//                    DatabaseReference password_ref = temp_ref.child("password");
//                    DatabaseReference name_ref = temp_ref.child("name");
//                    DatabaseReference age_ref = temp_ref.child("age");
//                    DatabaseReference sex_ref = temp_ref.child("sex");
//                    DatabaseReference sche_ref = temp_ref.child("schedules");
//
//                    email_ref.setValue(email.getText().toString());
//                    password_ref.setValue(password.getText().toString());
//                    name_ref.setValue((name.getText().toString()));
//                    sex_ref.setValue(sex.getText().toString());
//                    age_ref.setValue(age.getText().toString());

                    startActivity(new Intent(getApplicationContext(), AskPreference.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Check password again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
