package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.User;
import com.example.myapplication.Main.Main;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class AskLocation extends AppCompatActivity implements View.OnClickListener {
    Button nextBtn, prevBtn;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference user_ref = myRef.child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_location);

        nextBtn = findViewById(R.id.askLocation_nextPageBtn);
        nextBtn.setOnClickListener(this);
        prevBtn = findViewById(R.id.askLocation_prevPageBtn);
        prevBtn.setOnClickListener(this);

        ExpandableHeightGridView gridView = (ExpandableHeightGridView) findViewById(R.id.ask_location_gridView);
        gridView.setExpanded(true);
        GridViewImageAdapter adapter = new GridViewImageAdapter(this); //가로크기의 정보를 같이 넘긴다.
        gridView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.askLocation_nextPageBtn:
                // user의 정보를 디비에 넣는다
                User user = User.getInstance();

                    DatabaseReference temp_ref = user_ref.push();
                    user.setU_id(temp_ref.getKey());
                     Log.d("id ", user.getU_id());

                    DatabaseReference email_ref = temp_ref.child("email");
                    DatabaseReference password_ref = temp_ref.child("password");
                    DatabaseReference name_ref = temp_ref.child("name");
                    DatabaseReference age_ref = temp_ref.child("age");
                    DatabaseReference sex_ref = temp_ref.child("sex");
                    DatabaseReference sche_ref = temp_ref.child("schedules");
                    DatabaseReference pref_ref = temp_ref.child("preferences");
                    DatabaseReference loca_ref = temp_ref.child("locations");

                    email_ref.setValue(user.getEmail());
                    password_ref.setValue(user.getPassword());
                    name_ref.setValue(user.getName());
                    sex_ref.setValue(user.getSex());
                    age_ref.setValue(user.getAge());

                    for(int i=0; i<user.getPreferences().size(); i++){
                        pref_ref.push().setValue(user.getPreferences().get(i));
                    }

                for(int i=0; i<user.getLocations().size(); i++){
                    loca_ref.push().setValue(user.getLocations().get(i));
                }

                // 유저 이미지 업로드
//                Bitmap bitmap = user.getUser_image();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] data = baos.toByteArray();

//                StorageReference temp_stref = FirebaseStorage.getInstance().getReference().child(user.getU_id());
//                StorageReference img_stref = temp_stref.child("user_img");
//                UploadTask uploadTask = img_stref.putBytes(data);
//                uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        Log.d("img db result ", "fail.........");
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Log.d("img db result ", "success!!");
//                    }
//                });

                // 메인 화면으로 넘어간다
                startActivity(new Intent(AskLocation.this, Main.class));
                break;
            case R.id.askLocation_prevPageBtn:
                onBackPressed();
                break;
        }
    }
}
