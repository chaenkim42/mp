package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

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
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w("FIREBASE EXAMPLE", "Failed to read value.", error.toException());
            }
        });
    }

}
