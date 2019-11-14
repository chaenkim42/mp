package com.example.myapplication.Main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Schedule.ScheduleForm.Trip;

import java.util.ArrayList;
import java.util.List;

public class MyPagePlansAdapter extends RecyclerView.Adapter {
    List<Trip> tripList = new ArrayList<>();
    Context context;
    public TextView plantitle, plandates;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view){
            super(view);
        }
    }

    public MyPagePlansAdapter(List<Trip> tripList, Context context){
        this.tripList = tripList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // userplan_itemcard layout을 화면에 뿌려주고 holder에 연결
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userplans_itemcard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //각 위치에 문자열 세팅
        plantitle = (TextView) holder.itemView.findViewById(R.id.userplansitem_plantitle);
        plandates = (TextView) holder.itemView.findViewById(R.id.userplansitem_plandates);
        plantitle.setText(tripList.get(position).getTitle());
//        plandates.setText((CharSequence) tripList.get(position).getStartDate());
        Log.e("triplist", tripList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        //몇 개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
        return tripList.size();
    }
}
