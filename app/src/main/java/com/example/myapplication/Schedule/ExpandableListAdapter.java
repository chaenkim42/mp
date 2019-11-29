package com.example.myapplication.Schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.DragStartHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.Place;
import com.example.myapplication.R;
import com.example.myapplication.Search.SearchMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class ExpandableListAdapter extends RecyclerView.Adapter implements PlaceItemTouchHelperCallback.OnItemMoveListener {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public static final int EMPTY_CHILD = 2;
    public static boolean edit_flag = false;
    private List<Item> data;
    public static int whichDay=-1;


    public interface OnStartDragListener{
        void onStartDrag(ListChildViewHolder listChildViewHolder);
    }

    private final OnStartDragListener startDragListener;
    private OnAdapterInteractionListener mListener;

    public interface OnAdapterInteractionListener{
        void addBtnClickedInAdapter(boolean isClicked);
    }


    public ExpandableListAdapter(List<Item> data, Context context, OnStartDragListener onStartDragListener){
        this.data = data;
        mListener = (OnAdapterInteractionListener) context;
        startDragListener = onStartDragListener;
    }


    public interface OnItemClickListener{
        //empty child(장소 추가하기) click listener
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mmListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mmListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18*dp);
        int subItemPaddingTopAndBottom = (int) (5*dp);

        switch (viewType) {
            case HEADER: // HEADER 인 경우에 recyclerview_list_item.xml 을 생성
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.scheduleform_recyclerview_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.scheduleform_recyclerview_child, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(view);

                return child;
            case EMPTY_CHILD:
                inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.scheduleform_recyclerview_empty_child, parent, false);
                ListEmptyChildViewHolder emptyChild = new ListEmptyChildViewHolder(view);
                return emptyChild;
        }


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item; // 지금 가르키는거
                itemController.header_title.setText(item.title);
                itemController.header_date.setText(item.subTitle);
                itemController.saveBtn.setVisibility(View.INVISIBLE);

                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.up_arrow);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.down_arrow);
                }
                itemController.toggleBtn_constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) { //다 보여지고 있는 형태일때
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD
                                    || data.size() > pos + 1 && data.get(pos + 1).type == EMPTY_CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.down_arrow);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.up_arrow);
                            item.invisibleChildren = null;
                        }
                    }
                });

                // edit btn 눌렀을때 아이콘 보이기
                itemController.editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // edit btn 안보이게
                        itemController.editBtn.setVisibility(View.INVISIBLE);
                        // save btn 보이게
                        itemController.saveBtn.setVisibility(View.VISIBLE);
                    }
                });

                // save btn 눌렀을 때
                itemController.saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemController.saveBtn.setVisibility(View.INVISIBLE);
                        itemController.editBtn.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case CHILD:
                final ListChildViewHolder controller = (ListChildViewHolder) holder;
                controller.title.setText(item.title);
                controller.circle.setText(String.valueOf(item.orderOfVisit));

                controller.edit_btn.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                       if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){
                           startDragListener.onStartDrag(controller);
                           notifyDataSetChanged();
                       }
                        return false;
                    }
                });

                break;
            case EMPTY_CHILD:
                final ListEmptyChildViewHolder emptychild_controller = (ListEmptyChildViewHolder) holder;
                emptychild_controller.text_constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.addBtnClickedInAdapter(true);
                    }
                });
                emptychild_controller.plusCircle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.addBtnClickedInAdapter(true);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position){
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public TextView header_date;
        public ConstraintLayout toggleBtn_constraintLayout;
        public ImageView btn_expand_toggle;
        public Button budgetBtn;
        public Button editBtn, saveBtn;
        public Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.recyclerviewheader_title);
            header_date = (TextView) itemView.findViewById(R.id.recyclerviewheader_date);
            toggleBtn_constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.recyclerviewheader_toggleBtn_constraintLayout);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.recyclerviewheader_btn_expand_toggle);
            budgetBtn = (Button) itemView.findViewById(R.id.recyclerviewheader_budgetBtn);
            editBtn = (Button) itemView.findViewById(R.id.recyclerviewheader_editBtn);
            saveBtn = itemView.findViewById(R.id.recyclerviewheader_saveBtn);
        }
    }

    public static class ListChildViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView circle;
        public ImageButton edit_btn;
        public DragStartHelper mDragHandler;

        public ListChildViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.recyclerviewchild_location_title);
            circle = (TextView) itemView.findViewById(R.id.recyclerviewchild_circle);
            edit_btn = itemView.findViewById(R.id.edit_btn);

        }
    }

    private class ListEmptyChildViewHolder extends  RecyclerView.ViewHolder  {
        public TextView textView;
        public TextView plusCircle;
        public ConstraintLayout text_constraintLayout;
        public ImageButton edit_btn;

        public ListEmptyChildViewHolder(final View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.recyclerviewEmptychild_textView);
            plusCircle = (TextView) itemView.findViewById(R.id.recyclerviewEmptychild_circle);
            text_constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.recyclerviewEmptychild_text_constraintLayout);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mmListener != null){
                            mmListener.onItemClick(v,pos);
                        }
                    }
                }
            });
        }

    }

    public static class Item {
        public int type;
        public int whichDay; //1부터 시작
        public String title;
        public String subTitle;
        public int orderOfVisit;// for child item.
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String title, String subTitle) {
            if(type == HEADER){
                this.type = type;
                this.title = title; // ex. "Day 1"
                this.subTitle = subTitle; // ex "09/01 일"
            }
        }
        public Item(int type, Place place, int orderOfVisit){
            if(type == CHILD){
                this.type = type;
                this.title = place.getName(); // 여행장소 이름 ex. "여수엑스포"
                this.orderOfVisit= orderOfVisit;
            }
        }
        public Item(int type, int whichDay){
            if(type == EMPTY_CHILD){
                this.type = type;
                this.whichDay = whichDay;
            }
        }

        public int getWhichDay() {
            if(type == EMPTY_CHILD){
                return this.whichDay;
            }else{
                return -1;
            }
        }
    }



}
