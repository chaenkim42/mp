package com.example.myapplication;

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

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference user_ref = myRef.child("user");

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

        // Write a message to the database
//        final DatabaseReference password_ref = user_ref.child("password");

//        ScheduleForm.Trip trip = new ScheduleForm.Trip("new", new Date(2019/11/11),3);

//        myRef.child("1").child("id").setValue("1111");
//        myRef.setValue("안녕하세요");

//        InputStream is = null;
//        try {
//            is = this.getAssets().open("전국지역특화거리표준데이터.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            String myJson = new String(buffer, "UTF-8");
//            JSONObject obj = new JSONObject(myJson);
//            String myFinalString = obj.getString("records");
//            Log.e("jsonjson", myFinalString);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                Gson gson = new Gson();
                String value = dataSnapshot.getKey();
//                JSONObject jsonObject = gson.toJson(value);
                Log.d("FIREBASE EXAMPLE", "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
//        btn_sign_up.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick (View view){
//                DatabaseReference temp_ref = user_ref.push();
//                DatabaseReference email_ref = temp_ref.child("email");
//                DatabaseReference password_ref = temp_ref.child("password");
//                DatabaseReference name_ref = temp_ref.child("name");
//                DatabaseReference age_ref = temp_ref.child("age");
//                DatabaseReference sex_ref = temp_ref.child("sex");
//
//                String p = password.getText().toString();
//                String pc = password_c.getText().toString();
//
//                if (p.equals(pc)) {
//                    email_ref.setValue(email.getText().toString());
//                    password_ref.setValue(password.getText().toString());
//                    name_ref.setValue((name.getText().toString()));
//                    sex_ref.setValue(sex.getText().toString());
//                    age_ref.setValue(age.getText().toString());
//                } else {
//                    Toast.makeText(getApplicationContext(), "Check password again!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//        };
    }
}
