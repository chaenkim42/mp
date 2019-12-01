package com.example.myapplication.Schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.Diary;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyDiary_Adapter extends RecyclerView.Adapter {
    List<Diary> diaryList = new ArrayList<>();
    Context context;
    public TextView title, contents_text;
    public ImageView rep_photo;
    private ItemClick itemClick;
    int type;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view){
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(itemClick!=null){
                            itemClick.onClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    public MyDiary_Adapter(List<Diary> diaryList, Context context){
        this.diaryList = diaryList;
        this.context = context;
        if(diaryList.isEmpty()) type =0;
        else type = 1;
    }

    public interface ItemClick{
        public void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // activity_mydiary_cardview layout을 화면에 뿌려주고 holder에 연결
        View view;
        switch (type){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_diary, parent, false);
                return new ViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mydiary_cardview, parent, false);
                return new ViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (type){
            case 0:
                break;
            case 1:
                title = (TextView) holder.itemView.findViewById(R.id.mydiary_cardview_title);
                contents_text = (TextView) holder.itemView.findViewById(R.id.mydiary_cardview_content);
                rep_photo = (ImageView) holder.itemView.findViewById(R.id.mydiary_cardview_photobox);
                title.setText(diaryList.get(position).title);
                contents_text.setText(diaryList.get(position).contents_text);
                rep_photo.setImageBitmap(MyDiaryEditPage.img);
                rep_photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }
}
