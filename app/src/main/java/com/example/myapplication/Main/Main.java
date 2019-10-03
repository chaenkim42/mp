package com.example.myapplication.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.Schedule.MyData;
import com.example.myapplication.Schedule.ScheduleAdapter;
import com.example.myapplication.Schedule.ScheduleForm;
import com.example.myapplication.Search.SearchPage;

import java.security.MessageDigest;
import java.util.ArrayList;


public class Main extends AppCompatActivity implements View.OnClickListener {

    //뒤로가기
    private BackPressCloseHandler backPress;

    //Fragment Manager, Fragment Transaction to handle Fragment
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Button searchBtn;
    private Button mytripBtn;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<MyData> samples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getAppKeyHash();

        //뒤로가기
        backPress = new BackPressCloseHandler(this);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        searchBtn = findViewById(R.id.main_search_btn);
        mytripBtn = findViewById(R.id.main_mytrip_btn);
        recyclerView = findViewById(R.id.recyclerView);
        searchBtn.setOnClickListener(this);
        mytripBtn.setOnClickListener(this);

        //recyclerview
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        samples.add(new MyData("문정", 0));
        samples.add(new MyData("찬주", 0));
        samples.add(new MyData("채은", 0));

        adapter = new ScheduleAdapter(this, samples);
        recyclerView.setAdapter(adapter);


    }

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
            //Log.e("Hash key", info.signingInfo.getApkContentsSigners());
            Signature[] signatures = info.signingInfo.getApkContentsSigners();
            MessageDigest md = MessageDigest.getInstance("SHA");
            for(Signature signature:signatures){
                md.update(signature.toByteArray());
                final String signatureBase64 = new String(Base64.encode(md.digest(),Base64.DEFAULT));
                Log.d("Signature Base 64", signatureBase64);
            }
            /*for (Signature signature : info.signingInfo.getApkContentsSigners()) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
                System.out.println(something);
                System.out.println(something);
            }*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }

    //뒤로가기 버튼
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            Log.i("pop stack", "ok");
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
        }else {
            backPress.onBackPressed();
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_search_btn:
                startActivity(new Intent(Main.this, SearchPage.class));
                break;
            case R.id.main_mytrip_btn:
                startActivity(new Intent(Main.this, ScheduleForm.class));
                break;
            case R.id.main_profile_img_btn:
                startActivity(new Intent(Main.this, MyPage.class));
                break;
        }
    }
}
