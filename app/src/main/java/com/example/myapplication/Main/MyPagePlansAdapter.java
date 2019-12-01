package com.example.myapplication.Main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.ScheduleDb;
import com.example.myapplication.Database.Trip;
import com.example.myapplication.Database.User;
import com.example.myapplication.R;
import com.example.myapplication.Schedule.MyData;


import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MyPagePlansAdapter extends RecyclerView.Adapter {
    int type;
    List<Trip> tripList = new ArrayList<>();
    Context context;
    public ImageView planPhoto;
    TextView no_sche;
    public TextView plantitle, plandates;
    User user = User.getInstance();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view){
            super(view);
        }
    }

    public MyPagePlansAdapter(){
        for(int i=0; i<user.scheduleDbs.size(); i++){
            ScheduleDb temp = user.scheduleDbs.get(i);
            Trip trip;
            try{
                trip = new Trip(temp.title, new SimpleDateFormat("yyyy/MM/dd").parse(temp.start_date), temp.period);
            }catch (ParseException e){
                e.printStackTrace();
                trip = new Trip(temp.title, new Date(), temp.period);
            }
            tripList.add(trip);
        }

        if (tripList.isEmpty()) type = 0;
        else type = 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // userplan_itemcard layout을 화면에 뿌려주고 holder에 연결
        View view;
        switch (type){
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userplans_itemcard, parent, false);
                return new ViewHolder(view);

            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_sche_itemcard, parent, false);
                return new ViewHolder(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //각 위치에 문자열 세팅
        planPhoto = (ImageView)holder.itemView.findViewById(R.id.userplansitem_imageview);
        int[] drawables = new int[8];
        drawables[0] = R.drawable.location0;
        drawables[1] = R.drawable.location1;
        drawables[2] = R.drawable.location2;
        drawables[3] = R.drawable.location3;
        drawables[4] = R.drawable.location4;
        drawables[5] = R.drawable.location5;
        drawables[6] = R.drawable.location6;
        drawables[7] = R.drawable.location7;
//        Random r = new Random();
//        int random = r.nextInt(8);
        switch (type){
            case 0:
                break;
            case 1:
                for(int i=0; i<8; i++){
                    if(i==position){
                        planPhoto.setImageResource(drawables[i]);
                        planPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        break;
                    }
                }
                plantitle = (TextView) holder.itemView.findViewById(R.id.userplansitem_plantitle);
                plandates = (TextView) holder.itemView.findViewById(R.id.userplansitem_plandates);
                plantitle.setText(tripList.get(position).getTitle());
                plandates.setText("2019/12/24");
                //plandates.setText(tripList.get(position).s_date);
                break;
        }

    }

    @Override
    public int getItemCount() {
        //몇 개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
        return tripList.size();
    }
}
