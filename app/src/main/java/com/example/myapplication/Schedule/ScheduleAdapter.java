package com.example.myapplication.Schedule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.User;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int type;
    Context context;
    List<MyData> datas;
    TextView main_title, drawer_schedule_title;
    ImageView drawer_schedule_img, main_img;
    User user = User.getInstance();

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View view){
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        String sche_id = user.scheduleDbs.get(pos).sche_id;
                        Intent intent = new Intent(context, ScheduleForm.class);
                        intent.putExtra("sche_id", sche_id);
                        intent.putExtra("sche_pos", pos);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public ScheduleAdapter(Context context, int viewType){
        this.context = context;
        datas = new ArrayList<>();

        for(int i=0; i<user.scheduleDbs.size(); i++){
            MyData myData = new MyData(user.scheduleDbs.get(i).title, R.drawable.loca2);
            datas.add(myData);
        }

        this.type = viewType;
        // 어댑터를 생성할때 뷰타입을 받아온다 - 뷰 홀더에 레이아웃 인플레이트 할때 다른 종류로 나눠줌
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        //리사이클러뷰 여러개에 어뎁터를 재활용하기 위해서 뷰타입으로 어떤 아이템 리스트를 만들건지 지정함
        switch (type){
            case 0: // main
                view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
                return new ViewHolder(view);
            case 1: // my_page
                view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
                return new ViewHolder(view);
            case 2: // drawer_recyclerview
                view = LayoutInflater.from(context).inflate(R.layout.item_cardview, parent, false);
                return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 각 레이아웃의 성분들에 데이터로 받아온 각종 자료들을 넣어줌
        switch (type){
            case 0: //
                main_title = holder.itemView.findViewById(R.id.schedule_title);
                main_title.setText(datas.get(position).getText());
                main_img = holder.itemView.findViewById(R.id.schedule_img);
                main_img.setImageResource(datas.get(position).getImgNum());
                break;
            case 1:
                main_title = holder.itemView.findViewById(R.id.schedule_title);
                main_title.setText(datas.get(position).getText());
                main_img = holder.itemView.findViewById(R.id.schedule_img);
                main_img.setImageResource(datas.get(position).getImgNum());
                break;
            case 2:
                drawer_schedule_title = holder.itemView.findViewById(R.id.drawer_schedule_title);
                drawer_schedule_img = holder.itemView.findViewById(R.id.drawer_schedule_img);
                drawer_schedule_title.setText(datas.get(position).getText());
                drawer_schedule_img.setImageResource(datas.get(position).getImgNum());
                break;
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //
//    public class ViewCard extends RecyclerView.ViewHolder{
//    ItemCardviewBinding binding;
//
//        public ViewCard(ItemCardviewBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//        public void bind(int position){
//            if(position==0){
//                binding.date.setText(datas.get(position).getText());
//                binding.placePic.setImageResource(datas.get(position).getImgNum());
//            }else{
//                binding.date.setText(datas.get(position).getText());
//            }
//
//        }
//    }
//
//
//    public ScheduleAdapter(Context context, List<MyData> datas){
//       this.context = context;
//       this.datas = datas;
//    }
//
//    //아이템 뷰를 위한 뷰홀더 객체 생성
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        if(viewType==0){
//            return new ViewCard(ItemCardviewBinding.inflate(LayoutInflater.from(context), parent, false));
//        }else{
//            return new ViewCard(ItemCardviewBinding.inflate(LayoutInflater.from(context), parent, false));
//        }
//
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if(datas.get(position).viewType==0){
//            ((ViewCard)holder).bind(position);
//        }else{
//            ((ViewCard)holder).bind(position);
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//       if (datas.get(position).viewType==0) return 0;
//       else return 1;
//    }
//
//    // getItemCount() - 전체 데이터 갯수 리턴.
//    @Override
//    public int getItemCount() {
//        return datas.size() ;
//    }

}