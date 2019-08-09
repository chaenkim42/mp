package com.example.myapplication.Schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private ArrayList<String> strings;
//    private ArrayList<Drawable> drawables;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
//        ImageView imageView;

        ViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
//            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public ScheduleAdapter(ArrayList<String> str){
        strings = str;
//        drawables = drb;
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycler_item, parent, false) ;
        ScheduleAdapter.ViewHolder vh = new ScheduleAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int position) {
        String string = strings.get(position);
//        Drawable drawable = drawables.get(position);
        holder.textView.setText(string);
//        holder.imageView.setImageDrawable(drawable);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return strings.size() ;
    }

}
