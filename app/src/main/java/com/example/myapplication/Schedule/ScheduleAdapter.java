package com.example.myapplication.Schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemCardviewBinding;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MyData> datas;

    public class ViewCard extends RecyclerView.ViewHolder{
    ItemCardviewBinding binding;

        public ViewCard(ItemCardviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position){
            if(position==0){
                binding.date.setText(datas.get(position).getText());
                binding.placePic.setImageResource(datas.get(position).getImgNum());
            }else{
                binding.date.setText(datas.get(position).getText());
            }

//            binding.placePic.setImageResource(R.drawable.doggo);
//            System.out.println(datas.get(position).getImgNum());
//            binding.placePic.setImageDrawable(context.getResources().getDrawable(datas.get(position).getImgNum()));
        }
    }

//    public class ViewImage extends RecyclerView.ViewHolder{
//        public ViewImage(){
//
//        }
//    }

    public ScheduleAdapter(Context context, List<MyData> datas){
       this.context = context;
       this.datas = datas;
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==0){
            return new ViewCard(ItemCardviewBinding.inflate(LayoutInflater.from(context), parent, false));
        }else{
            return new ViewCard(ItemCardviewBinding.inflate(LayoutInflater.from(context), parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(datas.get(position).viewType==0){
            ((ViewCard)holder).bind(position);
        }else{
            ((ViewCard)holder).bind(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
       if (datas.get(position).viewType==0) return 0;
       else return 1;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return datas.size() ;
    }

}
